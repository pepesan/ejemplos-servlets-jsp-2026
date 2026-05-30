<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>500 — Error interno</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#f38ba8;text-align:center}
.numero{font-size:5em;display:block;text-align:center;margin:.1em 0}
h2{color:#cba6f7;margin-top:1.4em}
p{line-height:1.6;color:#a6adc8}a{color:#89dceb}code{color:#a6e3a1}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;font-size:.88em;vertical-align:top}
th{background:#313244;color:#cba6f7;white-space:nowrap}
td:first-child{color:#89b4fa;white-space:nowrap}
pre{background:#181825;border-left:3px solid #f38ba8;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.82em;line-height:1.5;
    color:#f38ba8;max-height:300px;overflow-y:auto}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;color:#a6adc8;margin:.5em 0}
.origen{display:inline-block;padding:.1em .5em;border-radius:3px;font-size:.78em;
        font-weight:bold;margin-left:.4em}
.via-exc{background:#cba6f760;color:#cba6f7}
.via-http{background:#fab38760;color:#fab387}
</style>
</head>
<body>

<%--
    Esta página se activa de DOS formas distintas:
    1. Error HTTP 500: el servlet llama a resp.sendError(500, "...")
    2. Excepción no capturada: <exception-type>java.lang.Exception</exception-type> en web.xml

    En ambos casos el contenedor pone información en javax.servlet.error.* del request.
    En el caso 2 (excepción), 'exception' también está disponible (isErrorPage=true).
--%>
<%
    String  uri     = (String)  request.getAttribute("javax.servlet.error.request_uri");
    Integer codigo  = (Integer) request.getAttribute("javax.servlet.error.status_code");
    String  mensaje = (String)  request.getAttribute("javax.servlet.error.message");
    Class   tipo    = (Class)   request.getAttribute("javax.servlet.error.exception_type");
    String  sv      = (String)  request.getAttribute("javax.servlet.error.servlet_name");
    Throwable ex    = (Throwable) request.getAttribute("javax.servlet.error.exception");
    if (ex == null) ex = exception;   // fallback al implícito

    boolean viaExcepcion = (ex != null);
%>

<span class="numero">500</span>
<h1>Error interno del servidor</h1>

<p style="text-align:center">
  Origen:
  <% if (viaExcepcion) { %>
    <span class="origen via-exc">excepción no capturada</span>
    &nbsp;→ entrada <code>&lt;exception-type&gt;</code> en web.xml
  <% } else { %>
    <span class="origen via-http">sendError(500)</span>
    &nbsp;→ entrada <code>&lt;error-code&gt;500&lt;/error-code&gt;</code> en web.xml
  <% } %>
</p>

<h2>Atributos del request (<code>javax.servlet.error.*</code>)</h2>
<table>
<tr><th>Atributo</th><th>Valor</th></tr>
<tr><td><code>status_code</code></td>    <td><%= codigo  != null ? codigo  : "—" %></td></tr>
<tr><td><code>request_uri</code></td>    <td><%= uri     != null ? uri     : "—" %></td></tr>
<tr><td><code>servlet_name</code></td>   <td><%= sv      != null ? sv      : "—" %></td></tr>
<tr><td><code>exception_type</code></td> <td><%= tipo    != null ? tipo.getName() : "—" %></td></tr>
<tr><td><code>message</code></td>        <td><%= mensaje != null ? mensaje : "—" %></td></tr>
</table>

<% if (viaExcepcion) { %>
<h2>Excepción</h2>
<table>
<tr><th>Campo</th><th>Valor</th></tr>
<tr><td><code>getClass().getName()</code></td> <td><%= ex.getClass().getName() %></td></tr>
<tr><td><code>getMessage()</code></td>         <td><%= ex.getMessage() %></td></tr>
</table>

<h2>Stack trace</h2>
<%
    java.io.StringWriter sw = new java.io.StringWriter();
    ex.printStackTrace(new java.io.PrintWriter(sw));
    out.println("<pre>" + sw.toString().replace("<", "&lt;").replace(">", "&gt;") + "</pre>");
%>
<% } %>

<div class="nota">
<strong>¿Cuándo se usa esta página?</strong><br>
· <code>resp.sendError(500, "mensaje")</code> — el servlet indica explícitamente el error.<br>
· Excepción no capturada en un servlet sin <code>errorPage</code> configurado.<br>
· Excepción cuyo tipo coincide con <code>&lt;exception-type&gt;java.lang.Exception&lt;/exception-type&gt;</code>.
</div>

<p><a href="/">← Inicio</a></p>
</body>
</html>
