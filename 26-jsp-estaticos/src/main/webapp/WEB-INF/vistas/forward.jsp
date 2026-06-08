<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Forward demo – contextPath en JSP interna</title>

    <%--
        Esta JSP vive en WEB-INF/vistas/ → no es accesible directamente.
        El ForwardServlet la sirve desde /api/demo.
        La URL que ve el navegador es /api/demo.

        Con contextPath:   ${pageContext.request.contextPath}/css/estilos.css
                           → /css/estilos.css  ✓ siempre correcto

        Sin contextPath:   css/estilos.css
                           → /api/css/estilos.css  ✗ 404 (no existe ese path)
    --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<nav>
    <a href="${pageContext.request.contextPath}/">Inicio</a>
    <a href="${pageContext.request.contextPath}/demo-correcto.jsp">✓ Correcto</a>
    <a href="${pageContext.request.contextPath}/demo-incorrecto.jsp">✗ Incorrecto</a>
    <a href="${pageContext.request.contextPath}/api/demo">Forward servlet</a>
</nav>

<h1>Forward desde servlet → JSP en WEB-INF</h1>
<p>
    Esta JSP está en <code>WEB-INF/vistas/forward.jsp</code> y es invisible
    para el navegador. El <code>ForwardServlet</code> la sirve desde
    <code>/api/demo</code>.
</p>

<div class="ok">
    CSS cargado correctamente — la ruta usa
    <code>${pageContext.request.contextPath}/css/estilos.css</code>.
</div>

<h2>URL que ve el navegador vs ubicación real de la JSP</h2>
<table>
    <tr><th>Dato</th><th>Valor</th></tr>
    <tr><td>URL del navegador</td><td><code id="browser-url"></code> ← calculada por JS</td></tr>
    <tr><td><code>request.getRequestURI()</code></td><td><code>/api/demo</code></td></tr>
    <tr><td><code>request.getServletPath()</code></td><td><code><%= request.getServletPath() %></code></td></tr>
    <tr><td>Ubicación física de esta JSP</td><td><code>WEB-INF/vistas/forward.jsp</code></td></tr>
    <tr><td><code>pageContext.request.contextPath</code></td><td><code>${pageContext.request.contextPath}</code></td></tr>
</table>

<h2>¿Qué habría pasado con una ruta relativa?</h2>
<pre>
&lt;!-- En esta JSP (WEB-INF/vistas/forward.jsp) --%>

&lt;!-- INCORRECTO: el navegador ve /api/demo, resuelve como /api/css/estilos.css --%>
&lt;link href="css/estilos.css"&gt;         → GET /api/css/estilos.css  → 404 ✗

&lt;!-- INCORRECTO: sube dos niveles desde /api → llega a /css/estilos.css... --%>
&lt;!-- pero el navegador ve /api/demo, no /WEB-INF/vistas/forward.jsp     --%>
&lt;link href="../../css/estilos.css"&gt;   → GET /css/estilos.css      → ✓ por suerte

&lt;!-- CORRECTO: ruta absoluta desde la raíz del contexto --%>
&lt;link href="${'$'}{pageContext.request.contextPath}/css/estilos.css"&gt;
                                        → GET /css/estilos.css      → ✓ siempre
</pre>

<h2>Código del ForwardServlet</h2>
<pre>
@WebServlet("/api/demo")  // ← URL que ve el navegador
public class ForwardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // La JSP vive en WEB-INF → no accesible directamente
        req.getRequestDispatcher("/WEB-INF/vistas/forward.jsp")
           .forward(req, resp);
    }
}
</pre>

<div id="js-status">Cargando JS...</div>
<script src="${pageContext.request.contextPath}/js/demo.js"></script>

</body>
</html>
