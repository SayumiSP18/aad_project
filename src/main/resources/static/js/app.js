let currentKey = null;
let optionsCache = {}; // entityKey -> array of {value,label}

$(function () {
    requireRole(["ADMIN", "STAFF"]);
    $("#topUsername").text(localStorage.getItem("username") || "");
    $("#topRole").text(localStorage.getItem("role") || "");
    buildSidebar();

    const firstKey = Object.keys(ENTITIES)[0];
    selectEntity(firstKey);

    $("#logoutBtn").on("click", function () {
        localStorage.clear();
        location.href = "login.html";
    });

    $("#modalCancel").on("click", closeModal);
    $("#modalForm").on("submit", function (e) {
        e.preventDefault();
        submitForm();
    });
});

function buildSidebar() {
    let html = "";
    NAV_GROUPS.forEach(function (group) {
        html += `<div class="group-label">${group.label}</div>`;
        group.items.forEach(function (key) {
            const cfg = ENTITIES[key];
            if (!cfg) return;
            html += `<a data-key="${key}" onclick="selectEntity('${key}')">${cfg.title}</a>`;
        });
    });
    $("#navLinks").html(html);
}

function selectEntity(key) {
    currentKey = key;
    $("#navLinks a").removeClass("active");
    $(`#navLinks a[data-key="${key}"]`).addClass("active");

    const cfg = ENTITIES[key];
    $("#pageTitle").text(cfg.title);

    let toolbarHtml = `<input type="text" id="searchBox" placeholder="Search ${cfg.title.toLowerCase()}...">
                        <button class="btn secondary small" onclick="runSearch()">Search</button>
                        <button class="btn secondary small" onclick="loadList()">Clear</button>`;
    if (!cfg.noCreate) {
        toolbarHtml += `<button class="btn small" onclick="openForm(null)">+ Add ${cfg.title.slice(0, -1) || cfg.title}</button>`;
    }
    $("#toolbar").html(toolbarHtml);

    loadList();
}

function loadList() {
    const cfg = ENTITIES[currentKey];
    apiCall("GET", cfg.listUrl).then(function (res) {
        renderTable(res.body || []);
    }).catch(function (err) {
        renderTable([]);
        if (err && err.message) alert(err.message);
    });
}

function runSearch() {
    const cfg = ENTITIES[currentKey];
    const value = $("#searchBox").val();
    if (!cfg.filterUrl) { loadList(); return; }
    const query = {};
    query[cfg.filterParam] = value;
    apiCall("GET", cfg.filterUrl, undefined, query).then(function (res) {
        renderTable(res.body || []);
    }).catch(function (err) {
        renderTable([]);
        if (err && err.message) alert(err.message);
    });
}

function renderTable(rows) {
    const cfg = ENTITIES[currentKey];
    let thead = "<tr>";
    cfg.columns.forEach(function (c) { thead += `<th>${c.label}</th>`; });
    thead += "<th>Actions</th></tr>";
    $("#tableHead").html(thead);

    if (!rows.length) {
        $("#tableBody").html(`<tr><td colspan="${cfg.columns.length + 1}"><div class="empty-state">No records found</div></td></tr>`);
        return;
    }

    let body = "";
    rows.forEach(function (row) {
        body += "<tr>";
        cfg.columns.forEach(function (c) {
            let val = row[c.key];
            if (val === null || val === undefined) val = "";
            if (typeof val === "boolean") val = val ? "Yes" : "No";
            body += `<td>${val}</td>`;
        });
        const id = row[cfg.idField];
        body += `<td>
            <button class="btn secondary small" onclick='openForm(${JSON.stringify(row)})'>Edit</button>
            <button class="btn danger small" onclick="deleteRecord(${id})">Delete</button>
        </td>`;
        body += "</tr>";
    });
    $("#tableBody").html(body);
}

function deleteRecord(id) {
    const cfg = ENTITIES[currentKey];
    if (!confirm("Delete this record?")) return;
    apiCall("DELETE", cfg.deleteUrlPrefix + id).then(function (res) {
        alert(res.message || "Deleted");
        loadList();
    }).catch(function (err) {
        alert((err && err.message) || "Delete failed");
    });
}

function openForm(existing) {
    const cfg = ENTITIES[currentKey];
    const isEdit = !!existing;
    const fields = (!isEdit && cfg.createFields) ? cfg.createFields : cfg.formFields;

    $("#modalTitle").text((isEdit ? "Edit " : "Add ") + cfg.title.replace(/s$/, ""));

    const optionPromises = fields
        .filter(function (f) { return f.type === "select" && f.optionsFrom; })
        .map(function (f) { return loadOptions(f.optionsFrom); });

    Promise.all(optionPromises).then(function () {
        let html = "";
        if (isEdit) {
            html += `<input type="hidden" id="f_${cfg.idField}" value="${existing[cfg.idField]}">`;
        }
        fields.forEach(function (f) {
            if (f.editOnly && !isEdit) return;
            const val = existing && existing[f.key] !== undefined ? existing[f.key] : "";
            html += `<label>${f.label}</label>`;

            if (f.type === "select") {
                let opts = f.staticOptions
                    ? f.staticOptions.map(function (o) { return { value: o, label: o }; })
                    : (optionsCache[f.optionsFrom] || []);
                html += `<select id="f_${f.key}" ${f.required ? "required" : ""}><option value="">-- select --</option>`;
                opts.forEach(function (o) {
                    const selected = String(o.value) === String(val) ? "selected" : "";
                    html += `<option value="${o.value}" ${selected}>${o.label}</option>`;
                });
                html += `</select>`;
            } else if (f.type === "checkbox") {
                html += `<input type="checkbox" id="f_${f.key}" ${val ? "checked" : ""}>`;
            } else {
                html += `<input type="${f.type || 'text'}" id="f_${f.key}" value="${val}" ${f.step ? 'step="' + f.step + '"' : ''} ${f.required ? "required" : ""}>`;
            }
        });
        $("#modalFields").html(html);
        $("#modalOverlay").addClass("open");
    });
}

function loadOptions(entityKey) {
    const cfg = ENTITIES[entityKey];
    if (!cfg) return Promise.resolve();
    return apiCall("GET", cfg.listUrl).then(function (res) {
        const rows = res.body || [];
        const f = Object.values(ENTITIES).flatMap(function (c) { return (c.formFields || []).concat(c.createFields || []); })
            .find(function (ff) { return ff.optionsFrom === entityKey; });
        optionsCache[entityKey] = rows.map(function (r) {
            return { value: r[f ? f.optionsValue : cfg.idField], label: r[f ? f.optionsLabel : cfg.idField] };
        });
    }).catch(function () { optionsCache[entityKey] = []; });
}

function closeModal() {
    $("#modalOverlay").removeClass("open");
}

function submitForm() {
    const cfg = ENTITIES[currentKey];
    const idVal = $(`#f_${cfg.idField}`).val();
    const isEdit = !!idVal;
    const fields = (!isEdit && cfg.createFields) ? cfg.createFields : cfg.formFields;

    const payload = {};
    if (isEdit) payload[cfg.idField] = Number(idVal);

    fields.forEach(function (f) {
        if (f.editOnly && !isEdit) return;
        const el = $(`#f_${f.key}`);
        if (!el.length) return;
        if (f.type === "checkbox") {
            payload[f.key] = el.is(":checked");
        } else if (f.type === "number") {
            payload[f.key] = parseFloat(el.val());
        } else if (f.type === "select" && f.optionsFrom && ENTITIES[f.optionsFrom] && f.optionsValue !== "roleName") {
            payload[f.key] = el.val() ? Number(el.val()) || el.val() : null;
        } else {
            payload[f.key] = el.val();
        }
    });

    const url = isEdit ? cfg.updateUrl : (cfg.registerUrl || cfg.saveUrl);
    const method = isEdit ? "PUT" : "POST";

    apiCall(method, url, payload).then(function (res) {
        alert(res.message || "Saved");
        closeModal();
        loadList();
    }).catch(function (err) {
        alert((err && err.message) || "Save failed");
    });
}
