<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Demo incorrecto – rutas relativas</title>

    <%-- ══════════════════════════════════════════════════════════════
         FORMA INCORRECTA: ruta relativa sin contextPath
         ─────────────────────────────────────────────────────────────
         "css/estilos.css" es relativa a la URL actual del navegador.
         Cuando el navegador ve /demo-incorrecto.jsp, resuelve como
         /css/estilos.css → funciona.
         Cuando el navegador ve /api/demo (forward a una JSP en subdir),
         resuelve como /api/css/estilos.css → 404.
         ══════════════════════════════════════════════════════════════ --%>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>

<%-- Sin CSS externo (para mostrar visualmente que esta demo es diferente) --%>
<style>
    body { font-family: monospace; max-width: 860px; margin: 2em auto;
           background: #1e1e2e; color: #cdd6f4; padding: 0 1em; }
    h1   { color: #f38ba8; }
    h2   { color: #cba6f7; margin-top: 1.4em; }
    p    { color: #a6adc8; }
    a    { color: #89dceb; }
    pre  { background: #181825; border-left: 3px solid #f38ba8;
           padding: .8em 1em; border-radius: 0 4px 4px 0; font-size: .88em; }
    code { color: #fab387; }
    table { border-collapse: collapse; width: 100%; margin: .8em 0; }
    td, th { border: 1px solid #45475a; padding: .4em .8em; font-size: .9em; text-align: left; }
    th { background: #313244; color: #cba6f7; }
    .ko { background: #3a1e2b; border-left: 4px solid #f38ba8; padding: .6em 1em; border-radius: 0 4px 4px 0; }
    nav { background: #313244; padding: .5em 1em; border-radius: 4px; margin-bottom: 1.5em; }
    nav a { color: #a6adc8; text-decoration: none; margin-right: 1em; }
</style>

<nav>
    <a href="/">Inicio</a>
    <a href="demo-correcto.jsp">✓ Correcto</a>
    <a href="demo-incorrecto.jsp">✗ Incorrecto</a>
    <a href="api/demo">Forward servlet</a>
</nav>

<h1>✗ Rutas relativas — comportamiento peligroso</h1>

<div class="ko">
    <strong>¿Se cargó el CSS externo?</strong> Si ves el bloque verde de "CSS cargado"
    al principio de la página, cargó. Si no, falló silenciosamente.
    Accedido directamente como <code>/demo-incorrecto.jsp</code>
    probablemente sí funciona; accedido como forward desde otro path, no.
</div>

<h2>URL actual y resolución de rutas relativas</h2>
<p>URL que ve el navegador: <code id="browser-url"></code></p>
<p>
    El navegador resuelve las rutas relativas desde esa URL, <strong>no</strong>
    desde la ubicación física del fichero JSP en el servidor.
</p>

<h2>Cómo resuelve el navegador cada tipo de ruta</h2>
<table>
    <tr><th>Tipo de ruta</th><th>Expresión</th><th>Resuelto desde <code>/demo-incorrecto.jsp</code></th><th>Resuelto desde <code>/api/demo</code></th></tr>
    <tr>
        <td>Relativa simple</td>
        <td><code>css/estilos.css</code></td>
        <td><code id="ruta-relativa">/css/estilos.css</code> ✓</td>
        <td><code>/api/css/estilos.css</code> ✗ 404</td>
    </tr>
    <tr>
        <td>Relativa con subida</td>
        <td><code>../css/estilos.css</code></td>
        <td><code id="ruta-subida">/../css → /css/estilos.css</code> ✓</td>
        <td><code>/../css/estilos.css</code> ✓ (solo por suerte)</td>
    </tr>
    <tr>
        <td>Absoluta sin contextPath</td>
        <td><code>/css/estilos.css</code></td>
        <td><code>/css/estilos.css</code> ✓</td>
        <td><code>/css/estilos.css</code> ✓ pero roto si context ≠ /</td>
    </tr>
    <tr>
        <td><strong>Correcta con contextPath</strong></td>
        <td><code>${'$'}{pageContext.request.contextPath}/css/estilos.css</code></td>
        <td><code>/css/estilos.css</code> ✓</td>
        <td><code>/css/estilos.css</code> ✓ siempre</td>
    </tr>
</table>

<p>
    Prueba ahora la misma JSP vía forward:
    <a href="api/demo">GET /api/demo</a> — la URL del navegador cambia a <code>/api/demo</code>
    y las rutas relativas se calculan desde ahí.
</p>

<div id="js-status">Cargando JS...</div>
<script src="js/demo.js"></script>

</body>
</html>
