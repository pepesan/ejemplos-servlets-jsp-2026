<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Demo correcto – contextPath</title>

    <%-- ══════════════════════════════════════════════════════════════
         FORMA CORRECTA: rutas absolutas con ${pageContext.request.contextPath}
         ─────────────────────────────────────────────────────────────
         ${pageContext.request.contextPath} devuelve la raíz del contexto:
           - "" (cadena vacía)  cuando la app está desplegada en /
           - "/miapp"           cuando está en /miapp
         Así la ruta final siempre empieza en la raíz del servidor.
         ══════════════════════════════════════════════════════════════ --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<nav>
    <a href="${pageContext.request.contextPath}/">Inicio</a>
    <a href="${pageContext.request.contextPath}/demo-correcto.jsp">✓ Correcto</a>
    <a href="${pageContext.request.contextPath}/demo-incorrecto.jsp">✗ Incorrecto</a>
    <a href="${pageContext.request.contextPath}/api/demo">Forward servlet</a>
</nav>

<h1>✓ Rutas correctas con <code>contextPath</code></h1>
<p>
    Esta página usa <code>${pageContext.request.contextPath}</code> para construir
    todas las rutas. El CSS y el JS cargan independientemente de cómo se llegue aquí.
</p>

<div class="ok">CSS cargado — este bloque tiene fondo verde gracias a la hoja de estilos.</div>

<h2>URL actual del navegador</h2>
<p>La URL que ve el navegador: <code id="browser-url"></code></p>
<p>
    El <code>contextPath</code> evaluado por el servidor:
    <code>${pageContext.request.contextPath}</code>
    <% if (request.getContextPath().isEmpty()) { %>
        <em>(cadena vacía → app en raíz /)</em>
    <% } %>
</p>

<h2>Cómo se resuelven las rutas desde aquí</h2>
<table>
    <tr><th>Expresión en JSP</th><th>Ruta que envía el navegador</th><th>Resultado</th></tr>
    <tr>
        <td><code>${pageContext.request.contextPath}/css/estilos.css</code></td>
        <td><code>${pageContext.request.contextPath}/css/estilos.css</code></td>
        <td>✓ Siempre correcto</td>
    </tr>
    <tr>
        <td><code id="ruta-relativa-label">css/estilos.css</code> (relativa)</td>
        <td><code id="ruta-relativa"></code> ← calculado por el JS</td>
        <td>Depende de la URL actual</td>
    </tr>
</table>

<h2>Código JSP</h2>
<pre>
&lt;!-- En el &lt;head&gt; de cada JSP --&gt;
&lt;link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/estilos.css"&gt;
&lt;script src="${pageContext.request.contextPath}/js/demo.js"&gt;&lt;/script&gt;

&lt;!-- En los enlaces internos --&gt;
&lt;a href="${pageContext.request.contextPath}/otra-pagina.jsp"&gt;Ir&lt;/a&gt;
&lt;form action="${pageContext.request.contextPath}/api/guardar" method="post"&gt;
</pre>

<h2>Por qué no usar <code>request.getContextPath()</code> en scriptlets</h2>
<pre>
&lt;!-- Scriptlet (evitar) --&gt;
&lt;link href="&lt;%= request.getContextPath() %&gt;/css/estilos.css"&gt;

&lt;!-- EL (preferible) --&gt;
&lt;link href="${pageContext.request.contextPath}/css/estilos.css"&gt;
</pre>
<p>Ambas son equivalentes, pero la EL es más legible y no rompe el editor HTML.</p>

<div id="js-status">Cargando JS...</div>
<script src="${pageContext.request.contextPath}/js/demo.js"></script>

</body>
</html>
