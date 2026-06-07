<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Objetos implícitos JSP</title>
<style>
body{font-family:monospace;max-width:900px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;vertical-align:top;font-size:.88em}
th{background:#313244;color:#cba6f7;white-space:nowrap}
td:first-child{color:#89b4fa;white-space:nowrap}
td:nth-child(2){color:#fab387}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;color:#a6adc8;margin:.5em 0}
</style>
</head>
<body>

<%-- Preparamos datos de sesión para mostrarlos --%>
<%
    if (session.getAttribute("usuario") == null) {
        session.setAttribute("usuario", "Invitado");
    }
    session.setAttribute("ultimaVisita", new java.util.Date().toString());
    application.setAttribute("appNombre", "20-jsp-jstl");
%>

<%@ include file="WEB-INF/_menu.jspf" %>

<h1>Objetos implícitos JSP</h1>
<p>El contenedor JSP inyecta automáticamente estos 9 objetos en cada página.
No hace falta declararlos: están disponibles directamente en scriptlets y expresiones.</p>

<table>
<tr>
  <th>Objeto</th>
  <th>Tipo Java</th>
  <th>Valor / ejemplo en esta página</th>
</tr>
<tr>
  <td><code>request</code></td>
  <td>HttpServletRequest</td>
  <td>
    Método: <strong><%= request.getMethod() %></strong><br>
    URI: <strong><%= request.getRequestURI() %></strong><br>
    IP cliente: <strong><%= request.getRemoteAddr() %></strong>
  </td>
</tr>
<tr>
  <td><code>response</code></td>
  <td>HttpServletResponse</td>
  <td>
    Content-Type: <strong><%= response.getContentType() %></strong><br>
    <em>(se puede usar para añadir cabeceras o cookies)</em>
  </td>
</tr>
<tr>
  <td><code>session</code></td>
  <td>HttpSession</td>
  <td>
    ID: <strong><%= session.getId() %></strong><br>
    usuario: <strong><%= session.getAttribute("usuario") %></strong><br>
    Nueva: <strong><%= session.isNew() %></strong>
  </td>
</tr>
<tr>
  <td><code>application</code></td>
  <td>ServletContext</td>
  <td>
    Servidor: <strong><%= application.getServerInfo() %></strong><br>
    appNombre: <strong><%= application.getAttribute("appNombre") %></strong>
  </td>
</tr>
<tr>
  <td><code>out</code></td>
  <td>JspWriter</td>
  <td>
    Buffer restante: <strong><%= out.getRemaining() %></strong> bytes<br>
    <em>Equivale a PrintWriter pero con buffer JSP</em>
  </td>
</tr>
<tr>
  <td><code>config</code></td>
  <td>ServletConfig</td>
  <td>
    Nombre del servlet: <strong><%= config.getServletName() %></strong>
  </td>
</tr>
<tr>
  <td><code>page</code></td>
  <td>Object (this)</td>
  <td>
    Clase: <strong><%= page.getClass().getName() %></strong>
  </td>
</tr>
<tr>
  <td><code>pageContext</code></td>
  <td>PageContext</td>
  <td>
    Accede a todos los scopes: page, request, session, application.<br>
    <code>pageContext.getAttribute("x", PageContext.SESSION_SCOPE)</code>
  </td>
</tr>
<tr>
  <td><code>exception</code></td>
  <td>Throwable</td>
  <td>
    Solo disponible en páginas con <code>isErrorPage="true"</code>.<br>
    En esta página: <strong>no accesible</strong> (necesita la directiva
    <code>&#37;@ page isErrorPage="true" &#37;</code>).
  </td>
</tr>
</table>

<h2>Scopes (ámbitos de atributos)</h2>
<div class="nota">
Los objetos implícitos se agrupan por ámbito (de menor a mayor vida):<br><br>
<strong>page</strong> → solo esta petición a esta JSP<br>
<strong>request</strong> → toda la petición HTTP (incluye forwards y includes)<br>
<strong>session</strong> → toda la sesión del usuario (varias peticiones)<br>
<strong>application</strong> → toda la aplicación (todos los usuarios)
</div>

<p><a href="/">← Inicio</a> &nbsp;|&nbsp;
   <a href="expresiones.jsp">← Expresiones</a> &nbsp;|&nbsp;
   <a href="el.jsp?nombre=Ana&edad=28">Siguiente: EL →</a></p>
</body>
</html>
