<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.webjars.WebJarVersionLocator" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Referencia WebJars</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css">
</head>
<body class="bg-dark text-light p-4">
<div class="container">

<nav class="mb-4 small">
    <a href="${pageContext.request.contextPath}/" class="text-secondary me-3">← Inicio</a>
    <a href="${pageContext.request.contextPath}/demo-jquery.jsp" class="text-secondary me-3">jQuery →</a>
    <a href="${pageContext.request.contextPath}/demo-bootstrap.jsp" class="text-secondary me-3">Bootstrap →</a>
    <a href="${pageContext.request.contextPath}/demo-versionless.jsp" class="text-secondary me-3">Sin versión →</a>
</nav>

<h1 class="text-danger mb-4">Referencia WebJars</h1>

<%-- Versiones resueltas en tiempo de ejecución --%>
<%
    WebJarVersionLocator loc = new WebJarVersionLocator();
    String jqVersion  = loc.version("jquery");
    String bsVersion  = loc.version("bootstrap");
%>

<h5 class="text-danger">WebJars presentes en este WAR (detectados en classpath)</h5>
<table class="table table-dark table-bordered table-sm mb-4">
    <thead><tr><th>WebJar</th><th>locate() devuelve</th><th>URL exacta</th></tr></thead>
    <tbody>
        <tr>
            <td>jquery</td>
            <td><code><%= jqVersion != null ? jqVersion : "no encontrado" %></code></td>
            <td><code>/webjars/jquery/<%= jqVersion != null ? jqVersion : "—" %></code></td>
        </tr>
        <tr>
            <td>bootstrap</td>
            <td><code><%= bsVersion != null ? bsVersion : "no encontrado" %></code></td>
            <td><code>/webjars/bootstrap/<%= bsVersion != null ? bsVersion : "—" %></code></td>
        </tr>
    </tbody>
</table>

<h5 class="text-danger">Cómo funciona Tomcat con WebJars</h5>
<pre class="bg-black text-success p-3 rounded small mb-4">
Estructura dentro del JAR (ej. jquery-3.7.1.jar):
  META-INF/
  └── resources/
      └── webjars/
          └── jquery/
              └── 3.7.1/
                  ├── jquery.js
                  ├── jquery.min.js
                  └── jquery.min.map

Tomcat 7+ sirve automáticamente META-INF/resources/ de cualquier JAR
del classpath, exactamente igual que sirve webapp/.

Por eso GET /webjars/jquery/3.7.1/jquery.min.js funciona sin configuración.
</pre>

<h5 class="text-danger">Dependencias Maven (pom.xml de este módulo)</h5>
<pre class="bg-black text-success p-3 rounded small mb-4">
&lt;!-- jQuery 3.7.1 --&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.webjars&lt;/groupId&gt;
    &lt;artifactId&gt;jquery&lt;/artifactId&gt;
    &lt;version&gt;3.7.1&lt;/version&gt;
&lt;/dependency&gt;

&lt;!-- Bootstrap 5.3.3 (incluye Popper via bootstrap.bundle) --&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.webjars&lt;/groupId&gt;
    &lt;artifactId&gt;bootstrap&lt;/artifactId&gt;
    &lt;version&gt;5.3.3&lt;/version&gt;
&lt;/dependency&gt;

&lt;!-- webjars-locator-lite: resuelve versiones sin hardcodearlas --&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.webjars&lt;/groupId&gt;
    &lt;artifactId&gt;webjars-locator-lite&lt;/artifactId&gt;
    &lt;version&gt;1.0.1&lt;/version&gt;
&lt;/dependency&gt;
</pre>

<h5 class="text-danger">Otras bibliotecas disponibles como WebJar</h5>
<table class="table table-dark table-bordered table-sm mb-4">
    <thead><tr><th>Biblioteca</th><th>artifactId</th><th>URL de referencia</th></tr></thead>
    <tbody>
        <tr><td>Chart.js</td><td><code>chartjs</code></td><td><code>/webjars/chartjs/{v}/chart.umd.min.js</code></td></tr>
        <tr><td>Font Awesome</td><td><code>font-awesome</code></td><td><code>/webjars/font-awesome/{v}/css/all.min.css</code></td></tr>
        <tr><td>Moment.js</td><td><code>momentjs</code></td><td><code>/webjars/momentjs/{v}/moment.min.js</code></td></tr>
        <tr><td>Vue.js</td><td><code>vue</code></td><td><code>/webjars/vue/{v}/dist/vue.global.prod.js</code></td></tr>
        <tr><td>Lodash</td><td><code>lodash</code></td><td><code>/webjars/lodash/{v}/lodash.min.js</code></td></tr>
    </tbody>
</table>
<p class="small text-secondary">
    Catálogo completo: <a href="https://www.webjars.org" class="text-info">webjars.org</a>
</p>

<h5 class="text-danger">Resumen: cuándo usar cada approach</h5>
<table class="table table-dark table-bordered table-sm">
    <thead><tr><th>Approach</th><th>URL ejemplo</th><th>Cuándo usarlo</th></tr></thead>
    <tbody>
        <tr>
            <td>Versión exacta</td>
            <td><code>/webjars/jquery/3.7.1/jquery.min.js</code></td>
            <td>Producción: caché perfecta, sin overhead de resolución</td>
        </tr>
        <tr>
            <td>WebjarsServlet (<code>/wj/</code>)</td>
            <td><code>/wj/jquery/jquery.min.js</code></td>
            <td>Desarrollo: 302 redirect transparente; cambias versión en pom.xml sin tocar las JSPs</td>
        </tr>
        <tr>
            <td>CDN externo</td>
            <td><code>https://cdn.jsdelivr.net/…</code></td>
            <td>No recomendado en producción: dependencia externa, CSP, GDPR</td>
        </tr>
    </tbody>
</table>

</div>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
