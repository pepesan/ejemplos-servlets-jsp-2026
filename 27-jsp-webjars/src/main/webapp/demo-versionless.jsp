<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Demo sin versión – WebjarsServlet</title>
    <%-- Bootstrap SIN versión en la URL → WebjarsServlet resuelve la versión --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/wj/bootstrap/css/bootstrap.min.css">
</head>
<body class="bg-dark text-light p-4">
<div class="container">

<nav class="mb-4 small">
    <a href="${pageContext.request.contextPath}/" class="text-secondary me-3">← Inicio</a>
    <a href="${pageContext.request.contextPath}/demo-jquery.jsp" class="text-secondary me-3">jQuery →</a>
    <a href="${pageContext.request.contextPath}/demo-bootstrap.jsp" class="text-secondary me-3">Bootstrap →</a>
    <a href="${pageContext.request.contextPath}/referencia.jsp" class="text-secondary">Referencia →</a>
</nav>

<h1 class="text-success mb-1">Demo sin versión — <code>/wj/*</code></h1>
<p class="text-secondary mb-4">
    El <code>WebjarsServlet</code> (mapeado en <code>/wj/*</code>) usa
    <code>WebJarVersionLocator</code> para resolver la versión desde el classpath.
    Las JSPs no necesitan conocer la versión exacta.
</p>

<div class="alert alert-success">
    Si ves este bloque verde, Bootstrap cargó desde
    <code>/wj/bootstrap/css/bootstrap.min.css</code>
    (sin versión en la URL).
</div>

<h5 class="text-success">Comparación de paths</h5>
<table class="table table-dark table-bordered table-sm">
    <thead><tr><th>Estilo</th><th>URL en JSP</th><th>URL real del recurso</th></tr></thead>
    <tbody>
    <tr>
        <td>Versión exacta</td>
        <td><code>/webjars/bootstrap/5.3.3/css/bootstrap.min.css</code></td>
        <td>Directamente al DefaultServlet de Tomcat</td>
    </tr>
    <tr class="table-success">
        <td><strong>Sin versión (WebjarsServlet)</strong></td>
        <td><code>/wj/bootstrap/css/bootstrap.min.css</code></td>
        <td>WebjarsServlet → resuelve → 302 redirect a <code>/webjars/bootstrap/5.3.3/…</code></td>
    </tr>
    </tbody>
</table>

<h5 class="text-success mt-4">Cómo funciona WebjarsServlet</h5>
<pre class="bg-black text-success p-3 rounded small">
// URL recibida: GET /wj/bootstrap/css/bootstrap.min.css
// getPathInfo() = /bootstrap/css/bootstrap.min.css

WebJarVersionLocator locator = new WebJarVersionLocator();
// path("bootstrap","css/bootstrap.min.css") → "bootstrap/5.3.3/css/bootstrap.min.css"
String rutaFinal = req.getContextPath() + "/webjars/" + rutaVersionada;
// 302 redirect → el navegador pide /webjars/bootstrap/5.3.3/css/bootstrap.min.css
// (Tomcat 7 no sirve META-INF/resources de JARs via forward, sí via petición directa)
resp.sendRedirect(rutaFinal);
</pre>

<h5 class="text-success mt-4">Ventaja: actualizar versión en un solo sitio</h5>
<pre class="bg-black text-success p-3 rounded small">
&lt;!-- pom.xml: solo aquí cambia la versión --&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.webjars&lt;/groupId&gt;
    &lt;artifactId&gt;bootstrap&lt;/artifactId&gt;
    &lt;version&gt;5.3.4&lt;/version&gt;  &lt;!-- actualizar aquí --&gt;
&lt;/dependency&gt;

&lt;!-- JSP: no cambia nada --&gt;
&lt;link href="${'$'}{pageContext.request.contextPath}/wj/bootstrap/css/bootstrap.min.css"&gt;
</pre>

<div class="alert alert-warning mt-3">
    <strong>Nota de rendimiento:</strong> con versión exacta (<code>/webjars/…</code>),
    el navegador puede cachear agresivamente (la URL incluye la versión, así que al
    cambiar la versión la URL cambia y no hay caché obsoleta).
    Con <code>/wj/</code>, la URL no cambia al actualizar la versión, lo que puede
    causar caché obsoleta en producción. En producción, incluye la versión en la URL.
</div>

</div>

<script src="${pageContext.request.contextPath}/wj/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/wj/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
