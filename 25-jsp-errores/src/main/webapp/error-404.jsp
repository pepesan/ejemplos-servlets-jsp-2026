<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>404 — Página no encontrada</title>
<style>
body{font-family:monospace;max-width:760px;margin:4em auto;background:#1e1e2e;color:#cdd6f4;text-align:center}
h1{color:#fab387;font-size:5em;margin:.2em 0}
h2{color:#cba6f7;font-size:1.3em}
p{color:#a6adc8;line-height:1.6}a{color:#89dceb}
pre{background:#181825;border-left:3px solid #fab387;padding:.7em 1em;
    border-radius:0 4px 4px 0;font-size:.85em;text-align:left;max-width:560px;margin:1em auto}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.85em;color:#a6adc8;margin:1em auto;max-width:560px;text-align:left}
</style>
</head>
<body>

<%
    String uri     = (String)  request.getAttribute("javax.servlet.error.request_uri");
    String mensaje = (String)  request.getAttribute("javax.servlet.error.message");
    Integer codigo = (Integer) request.getAttribute("javax.servlet.error.status_code");
%>

<h1>404</h1>
<h2>Página no encontrada</h2>

<p>
  No existe ningún recurso en<br>
  <strong><%= uri != null ? uri : "(URI desconocida)" %></strong>
</p>
<% if (mensaje != null && !mensaje.isEmpty()) { %>
  <p><em><%= mensaje %></em></p>
<% } %>

<p><a href="/">← Volver al inicio</a></p>

<div class="nota">
<strong>¿Por qué ves esta página y no el 404 genérico de Tomcat?</strong><br>
Porque <code>web.xml</code> tiene esta entrada:<br><br>
<pre style="margin:0;border:none;padding:0;background:transparent">
&lt;error-page&gt;
    &lt;error-code&gt;404&lt;/error-code&gt;
    &lt;location&gt;/error-404.jsp&lt;/location&gt;
&lt;/error-page&gt;
</pre>
<br>
El contenedor interceptó el error HTTP <%= codigo %> y redirigió aquí,
poniendo la información en los atributos <code>javax.servlet.error.*</code>
del request.
</div>

</body>
</html>
