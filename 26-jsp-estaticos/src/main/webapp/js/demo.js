// demo.js — prueba visual de que el JS cargó correctamente

document.addEventListener('DOMContentLoaded', function () {

    // ── Indicador de carga ─────────────────────────────────────────────
    var indicador = document.getElementById('js-status');
    if (indicador) {
        var ahora = new Date();
        indicador.textContent = '✓ JS cargado — ' + ahora.toLocaleTimeString('es-ES', { hour12: false });
        indicador.style.background = '#1e3a2b';
        indicador.style.borderLeft = '4px solid #a6e3a1';
        indicador.style.color = '#a6e3a1';
        indicador.style.padding = '.5em 1em';
        indicador.style.borderRadius = '0 4px 4px 0';
        indicador.style.marginBottom = '.8em';
    }

    // ── URL que ve el navegador ────────────────────────────────────────
    var urlDiv = document.getElementById('browser-url');
    if (urlDiv) {
        urlDiv.textContent = window.location.pathname;
    }

    // ── Rutas resueltas por el navegador ──────────────────────────────
    resolverRuta('ruta-relativa',  'css/estilos.css');
    resolverRuta('ruta-subida',    '../css/estilos.css');
    resolverRuta('ruta-absoluta',  '/css/estilos.css');
});

function resolverRuta(id, ruta) {
    var el = document.getElementById(id);
    if (!el) return;
    // El navegador resuelve la ruta relativa al href de un <a> temporal
    var a = document.createElement('a');
    a.href = ruta;
    el.textContent = a.pathname;
}
