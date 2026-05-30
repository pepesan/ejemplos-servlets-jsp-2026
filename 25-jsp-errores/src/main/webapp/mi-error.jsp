<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Error — mi-error.jsp</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#f38ba8}h2{color:#cba6f7;margin-top:1.4em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;font-size:.88em;vertical-align:top}
th{background:#313244;color:#cba6f7;white-space:nowrap}
td:first-child{color:#89b4fa;white-space:nowrap}
pre{background:#181825;border-left:3px solid #f38ba8;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.82em;line-height:1.5;
    color:#f38ba8;max-height:260px;overflow-y:auto}
.badge{display:inline-block;background:#f38ba8;color:#1e1e2e;
       padding:.2em .6em;border-radius:3px;font-weight:bold;font-size:.85em}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;color:#a6adc8}
</style>
</head>
<body>

<%-- ══════════════════════════════════════════════════════════════
     isErrorPage="true" activa:
       · El objeto implícito 'exception' (Throwable original)
       · Los atributos javax.servlet.error.* en el request
     ══════════════════════════════════════════════════════════════ --%>

<%
    // Atributos estándar que el contenedor pone en el request de error
    String uri       = (String)  request.getAttribute("javax.servlet.error.request_uri");
    Integer codigo   = (Integer) request.getAttribute("javax.servlet.error.status_code");
    String mensaje   = (String)  request.getAttribute("javax.servlet.error.message");
    Class  tipo      = (Class)   request.getAttribute("javax.servlet.error.exception_type");
    String servlet   = (String)  request.getAttribute("javax.servlet.error.servlet_name");

    // 'exception' es el objeto implícito (solo disponible con isErrorPage="true")
    // Si venimos de una JSP con errorPage, es la excepción lanzada allí
    Throwable ex = exception;
%>

<h1>&#128680; Error capturado por <code>mi-error.jsp</code></h1>
<p>Esta página se activa mediante la directiva
<code>&#37;@ page errorPage="/mi-error.jsp" &#37;</code>
en la JSP que lanzó la excepción.</p>

<div class="badge">isErrorPage="true"</div>

<h2>Información del error</h2>
<table>
<tr><th>Atributo / Objeto</th><th>Valor</th><th>Fuente</th></tr>
<tr>
  <td><code>exception</code></td>
  <td><%= ex != null ? ex.getClass().getName() : "(null)" %></td>
  <td>Objeto implícito JSP</td>
</tr>
<tr>
  <td><code>exception.getMessage()</code></td>
  <td><%= ex != null ? ex.getMessage() : "(null)" %></td>
  <td>Objeto implícito JSP</td>
</tr>
<tr>
  <td><code>javax.servlet.error.request_uri</code></td>
  <td><%= uri != null ? uri : "(no establecido)" %></td>
  <td>request.getAttribute()</td>
</tr>
<tr>
  <td><code>javax.servlet.error.status_code</code></td>
  <td><%= codigo != null ? codigo : "(no establecido)" %></td>
  <td>request.getAttribute()</td>
</tr>
<tr>
  <td><code>javax.servlet.error.exception_type</code></td>
  <td><%= tipo != null ? tipo.getName() : "(no establecido)" %></td>
  <td>request.getAttribute()</td>
</tr>
<tr>
  <td><code>javax.servlet.error.servlet_name</code></td>
  <td><%= servlet != null ? servlet : "(no establecido)" %></td>
  <td>request.getAttribute()</td>
</tr>
</table>

<h2>Stack trace</h2>
<%
    if (ex != null) {
        java.io.StringWriter sw = new java.io.StringWriter();
        ex.printStackTrace(new java.io.PrintWriter(sw));
        out.println("<pre>" + sw.toString().replace("<", "&lt;").replace(">", "&gt;") + "</pre>");
    } else {
        out.println("<p class='nota'>No hay stack trace disponible.</p>");
    }
%>

<div class="nota">
<strong>Cómo funciona el forward a esta página:</strong><br>
1. La JSP origen tiene <code>errorPage="/mi-error.jsp"</code>.<br>
2. Lanza una excepción no capturada en un scriptlet o etiqueta.<br>
3. El contenedor intercepta la excepción y hace <code>forward</code> aquí.<br>
4. La excepción queda disponible en <code>exception</code> y en los atributos
   <code>javax.servlet.error.*</code> del request.
</div>

<p><a href="/">← Inicio</a> &nbsp;|&nbsp; <a href="demos.jsp">Volver a demos.jsp</a></p>
</body>
</html>
