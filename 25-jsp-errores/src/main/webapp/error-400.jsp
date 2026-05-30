<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>400 — Petición incorrecta</title>
<style>
body{font-family:monospace;max-width:760px;margin:4em auto;background:#1e1e2e;color:#cdd6f4;text-align:center}
h1{color:#f9e2af;font-size:5em;margin:.2em 0}
h2{color:#cba6f7;font-size:1.3em}
p{color:#a6adc8;line-height:1.6}a{color:#89dceb}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.85em;color:#a6adc8;
      margin:1em auto;max-width:560px;text-align:left}
</style>
</head>
<body>

<%
    String uri     = (String)  request.getAttribute("javax.servlet.error.request_uri");
    String mensaje = (String)  request.getAttribute("javax.servlet.error.message");
%>

<h1>400</h1>
<h2>Petición incorrecta</h2>
<p>La petición no pudo procesarse correctamente.</p>
<% if (mensaje != null && !mensaje.isEmpty()) { %>
  <p><em><%= mensaje %></em></p>
<% } %>
<p><a href="/">← Volver al inicio</a> &nbsp;|&nbsp; <a href="generar?tipo=404">Ver demo 404</a></p>
</body>
</html>
