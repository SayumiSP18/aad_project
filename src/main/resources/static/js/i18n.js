/**
 * i18n.js — lightweight client-side localization engine
 * No backend / Spring changes required. Dictionaries are served
 * as static JSON from /i18n/{lang}.json (src/main/resources/static/i18n/).
 *
 * Usage in HTML:
 *   <h1 data-i18n="nav.dashboard">Dashboard</h1>
 *   <input data-i18n-placeholder="login.username" placeholder="Username">
 *   <button data-i18n-title="common.delete" title="Delete">...</button>
 *
 * Usage in JS (for dynamically built content, e.g. after a fetch()):
 *   row.innerHTML = `<td>${t('deliveries.status.pending')}</td>`;
 *
 * Language changes fire a 'languageChanged' event on document, so any
 * screen that renders tables from API data can re-render on that event.
 *
 * IMPORTANT: loadLanguage() is async (it fetches JSON over the network),
 * so any other script that calls t() on page load — e.g. app.js building
 * a sidebar from ENTITIES — must wait for the dictionaries to actually be
 * loaded first, or every t() call will fail with "missing key in all
 * dictionaries" (currentTranslations/fallbackTranslations are still {}).
 * `window.i18nReady` is a promise that resolves once the first language
 * load completes; consumers should do `window.i18nReady.then(() => {...})`
 * before calling t() on initial page load.
 */

const SUPPORTED_LANGUAGES = ['en', 'si'];
const DEFAULT_LANGUAGE = 'en';
const STORAGE_KEY = 'preferredLang';

let currentLang = DEFAULT_LANGUAGE;
let currentTranslations = {};
let fallbackTranslations = {};

// Resolved once the first loadLanguage() call completes successfully.
// Other scripts can do: window.i18nReady.then(() => { ...calls to t()... })
let _resolveI18nReady;
window.i18nReady = new Promise((resolve) => { _resolveI18nReady = resolve; });

/**
 * Load a language dictionary and apply it to the page.
 * Falls back to English for any missing keys, and to DEFAULT_LANGUAGE
 * entirely if the requested language file fails to load.
 */
async function loadLanguage(lang) {
    if (!SUPPORTED_LANGUAGES.includes(lang)) {
        console.warn(`i18n: unsupported language "${lang}", falling back to ${DEFAULT_LANGUAGE}`);
        lang = DEFAULT_LANGUAGE;
    }

    try {
        // Always keep an English fallback loaded so missing keys degrade gracefully
        if (Object.keys(fallbackTranslations).length === 0) {
            const fallbackRes = await fetch(`/i18n/${DEFAULT_LANGUAGE}.json`);
            fallbackTranslations = await fallbackRes.json();
        }

        if (lang === DEFAULT_LANGUAGE) {
            currentTranslations = fallbackTranslations;
        } else {
            const res = await fetch(`/i18n/${lang}.json`);
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            currentTranslations = await res.json();
        }

        currentLang = lang;
        localStorage.setItem(STORAGE_KEY, lang);

        applyTranslations();
        syncSwitcherUI();
        document.dispatchEvent(new CustomEvent('languageChanged', { detail: { lang } }));
    } catch (err) {
        console.error(`i18n: failed to load "${lang}" — staying on ${currentLang}`, err);
    } finally {
        // Resolve exactly once, even on failure, so waiting code doesn't hang forever.
        if (_resolveI18nReady) {
            _resolveI18nReady();
            _resolveI18nReady = null;
        }
    }
}

/** Look up a single key, with English fallback and a visible marker for missing keys. */
function t(key) {
    if (currentTranslations[key] !== undefined) return currentTranslations[key];
    if (fallbackTranslations[key] !== undefined) {
        console.warn(`i18n: missing key "${key}" in "${currentLang}", using English fallback`);
        return fallbackTranslations[key];
    }
    console.warn(`i18n: missing key "${key}" in all dictionaries`);
    return key;
}

/** Walk the DOM and apply translations to every tagged element. */
function applyTranslations() {
    document.querySelectorAll('[data-i18n]').forEach(el => {
        const key = el.getAttribute('data-i18n');
        el.textContent = t(key);
    });

    document.querySelectorAll('[data-i18n-placeholder]').forEach(el => {
        const key = el.getAttribute('data-i18n-placeholder');
        el.setAttribute('placeholder', t(key));
    });

    document.querySelectorAll('[data-i18n-title]').forEach(el => {
        const key = el.getAttribute('data-i18n-title');
        el.setAttribute('title', t(key));
    });

    document.documentElement.setAttribute('lang', currentLang);
}

/** Keep a <select id="langSwitcher"> (if present on the page) in sync with the active language. */
function syncSwitcherUI() {
    const switcher = document.getElementById('langSwitcher');
    if (switcher) switcher.value = currentLang;
}

document.addEventListener('DOMContentLoaded', () => {
    const saved = localStorage.getItem(STORAGE_KEY) || DEFAULT_LANGUAGE;
    loadLanguage(saved);

    const switcher = document.getElementById('langSwitcher');
    if (switcher) {
        switcher.addEventListener('change', (e) => loadLanguage(e.target.value));
    }
});