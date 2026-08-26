const API_BASE = "http://localhost:8080";

function authHeaders() {
    const token = localStorage.getItem("JWT");
    return token ? { "Authorization": "Bearer " + token } : {};
}

function apiCall(method, path, data, query) {
    let url = API_BASE + path;
    if (query) {
        const qs = $.param(query);
        if (qs) url += "?" + qs;
    }

    return $.ajax({
        url: url,
        type: method,
        contentType: "application/json",
        headers: authHeaders(),
        data: data !== undefined ? JSON.stringify(data) : undefined
    }).then(function (response) {
        if (response && response.status !== 0) {
            // status 0 = success per ResponseCode.OPERATION_SUCCESS
            return $.Deferred().reject(response).promise();
        }
        return response;
    }, function (jqXHR) {
        if (jqXHR.status === 401 || jqXHR.status === 403) {
            handleUnauthorized();
        }
        return $.Deferred().reject(
            (jqXHR.responseJSON && jqXHR.responseJSON) || { message: "Request failed" }
        ).promise();
    });
}

function handleUnauthorized() {
    localStorage.removeItem("JWT");
    localStorage.removeItem("userId");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
    if (!location.pathname.endsWith("login.html")) {
        alert("Session expired or access denied. Please log in again.");
        location.href = "login.html";
    }
}

function requireAuth() {
    if (!localStorage.getItem("JWT")) {
        location.href = "login.html";
    }
}

function requireRole(allowedRoles) {
    requireAuth();
    const role = (localStorage.getItem("role") || "").toUpperCase();
    if (!allowedRoles.map(r => r.toUpperCase()).includes(role)) {
        alert("You don't have access to this page.");
        location.href = "login.html";
    }
}
