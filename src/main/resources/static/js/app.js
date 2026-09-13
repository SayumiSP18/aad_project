let currentKey = null;
let optionsCache = {}; // entityKey -> array of {value,label}

/** Fill {placeholders} in a translated string, e.g. tpl('toolbar.addBtn', {title: 'Zone'}) */
function tpl(key, vars) {
    let s = t(key);
    Object.keys(vars || {}).forEach(function (k) {
        s = s.split('{' + k + '}').join(vars[k]);
    });
    return s;
}

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
        html += `<div class="group-label">${t(group.labelKey)}</div>`;
        group.items.forEach(function (key) {
            const cfg = ENTITIES[key];
            if (!cfg) return;
            html += `<a data-key="${key}" onclick="selectEntity('${key}')">${t(cfg.titleKey)}</a>`;
        });
    });
    $("#navLinks").html(html);
}

function selectEntity(key) {
    currentKey = key;
    $("#navLinks a").removeClass("active");
    $(`#navLinks a[data-key="${key}"]`).addClass("active");

    const cfg = ENTITIES[key];
    const titleText = t(cfg.titleKey);
    const singularText = t(cfg.singularKey);
    $("#pageTitle").text(titleText);

    let toolbarHtml = `<input type="text" id="searchBox" placeholder="${tpl('toolbar.searchPlaceholder', { title: titleText.toLowerCase() })}">
                        <button class="btn secondary small" onclick="runSearch()">${t('common.search')}</button>
                        <button class="btn secondary small" onclick="loadList()">${t('common.clear')}</button>`;
    if (!cfg.noCreate) {
        toolbarHtml += `<button class="btn small" onclick="openForm(null)">${tpl('toolbar.addBtn', { title: singularText })}</button>`;
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
    cfg.columns.forEach(function (c) { thead += `<th>${t(c.label)}</th>`; });
    thead += `<th>${t('common.actions')}</th></tr>`;
    $("#tableHead").html(thead);

    if (!rows.length) {
        $("#tableBody").html(`<tr><td colspan="${cfg.columns.length + 1}"><div class="empty-state">${t('common.noData')}</div></td></tr>`);
        return;
    }

    let body = "";
    rows.forEach(function (row) {
        body += "<tr>";
        cfg.columns.forEach(function (c) {
            let val = row[c.key];
            if (val === null || val === undefined) val = "";
            // Booleans are UI-generated text ("Yes"/"No"), safe to translate.
            // Everything else here is real data from the database — never translated.
            if (typeof val === "boolean") val = val ? t('common.yes') : t('common.no');
            body += `<td>${val}</td>`;
        });
        const id = row[cfg.idField];
        body += `<td>
            <button class="btn secondary small" onclick='openForm(${JSON.stringify(row)})'>${t('common.edit')}</button>
            <button class="btn danger small" onclick="deleteRecord(${id})">${t('common.delete')}</button>
        </td>`;
        body += "</tr>";
    });
    $("#tableBody").html(body);
}

function deleteRecord(id) {
    const cfg = ENTITIES[currentKey];
    if (!confirm(t('common.confirmDelete'))) return;
    apiCall("DELETE", cfg.deleteUrlPrefix + id).then(function (res) {
        alert(res.message || t('alert.deleted'));
        loadList();
    }).catch(function (err) {
        alert((err && err.message) || t('alert.deleteFailed'));
    });
}

function openForm(existing) {
    const cfg = ENTITIES[currentKey];
    const isEdit = !!existing;
    const fields = (!isEdit && cfg.createFields) ? cfg.createFields : cfg.formFields;

    $("#modalTitle").text(`${isEdit ? t('modal.editPrefix') : t('modal.addPrefix')} ${t(cfg.singularKey)}`);

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
            html += `<label>${t(f.label)}</label>`;

            if (f.type === "select") {
                let opts;
                if (f.staticOptions) {
                    // Enum values like BOOKED/CARD/PENDING get translated via their namespace
                    opts = f.staticOptions.map(function (o) {
                        return { value: o, label: t(`${f.optionsNamespace}.${o}`) };
                    });
                } else {
                    // Options pulled from another entity (branch names, customer names, etc.)
                    // are real data — displayed exactly as stored, never translated.
                    opts = optionsCache[f.optionsFrom] || [];
                }
                html += `<select id="f_${f.key}" ${f.required ? "required" : ""}><option value="">${t('common.selectPlaceholder')}</option>`;
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
        alert(res.message || t('alert.saved'));
        closeModal();
        loadList();
    }).catch(function (err) {
        alert((err && err.message) || t('alert.saveFailed'));
    });
}

// The whole admin screen is rebuilt from JS on every render, so the simplest
// correct fix for language switching is: rebuild the sidebar and re-select
// the current module whenever the language changes. This does re-fetch data
// from the server each time you switch language — acceptable for this scale
// of app, and avoids caching complexity you don't need right now.
document.addEventListener('languageChanged', function () {
    buildSidebar();
    if (currentKey) selectEntity(currentKey);
});