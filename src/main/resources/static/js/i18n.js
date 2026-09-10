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
 */

const SUPPORTED_LANGUAGES = ['en', 'si'];
const DEFAULT_LANGUAGE = 'en';
const STORAGE_KEY = 'preferredLang';

let currentLang = DEFAULT_LANGUAGE;
let currentTranslations = {};
let fallbackTranslations = {};

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