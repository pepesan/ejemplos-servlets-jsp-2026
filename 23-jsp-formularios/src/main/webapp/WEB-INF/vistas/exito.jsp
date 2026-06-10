<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><title>Registro completado</title>
<style>
body{font-family:monospace;max-width:560px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#a6e3a1}p{color:#a6adc8;line-height:1.6}a{color:#89dceb}code{color:#a6e3a1}
.icono{font-size:3em;display:block;margin-bottom:.2em;text-align:center}
h1{text-align:center}
table{border-collapse:collapse;width:100%;margin:1.2em 0}
td,th{border:1px solid #45475a;padding:.45em .9em;font-size:.9em;text-align:left}
th{background:#313244;color:#cba6f7;width:40%}
td{color:#cdd6f4}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.82em;
      color:#585b70;margin-top:1.5em}
.acciones{margin-top:1.2em;display:flex;gap:.8em}
.acciones a{background:#45475a;padding:.4em 1em;border-radius:4px;text-decoration:none;
            color:#cdd6f4;font-size:.9em}
.acciones a:hover{background:#585b70}
</style>
</head>
<body>

<%
    com.cursosdedesarrollo.RegistroBean bean = null;
    javax.servlet.http.HttpSession s = request.getSession(false);
    if (s != null) {
        Object v = s.getAttribute("usuarioRegistrado");
        if (v instanceof com.cursosdedesarrollo.RegistroBean) {
            bean = (com.cursosdedesarrollo.RegistroBean) v;
            s.removeAttribute("usuarioRegistrado"); // se consume una sola vez
        }
    }
    request.setAttribute("bean", bean);
%>

<span class="icono">&#10003;</span>
<h1>¡Registro completado!</h1>

<c:choose>
  <c:when test="${bean != null}">
    <p>Los siguientes datos han sido validados y registrados correctamente:</p>
    <table>
      <tr><th>Nombre</th>              <td><c:out value="${bean.nombre}" /></td></tr>
      <tr><th>Correo electrónico</th>  <td><c:out value="${bean.email}" /></td></tr>
      <tr><th>Edad</th>                <td><c:out value="${bean.edad}" /> años</td></tr>
      <tr><th>Contraseña</th>          <td><em style="color:#585b70">— no se muestra —</em></td></tr>
    </table>
  </c:when>
  <c:otherwise>
    <p>Tu cuenta ha sido creada correctamente.</p>
  </c:otherwise>
</c:choose>

<div class="acciones">
  <a href="${pageContext.request.contextPath}/registro">Registrar otro usuario</a>
  <a href="${pageContext.request.contextPath}/">← Inicio</a>
</div>

<div class="nota">
  <strong>¿Por qué esta página no se reenvía al recargar?</strong><br>
  El servlet hizo <code>sendRedirect("/registro?ok=1")</code> después del POST exitoso.
  El navegador lanzó un GET nuevo; recargar repite solo ese GET, no el POST original.
  Eso es el patrón <strong>PRG (Post-Redirect-Get)</strong>.<br><br>
  <strong>¿Por qué el bean está en sesión y no en el request?</strong><br>
  El redirect hace que el navegador lance una segunda petición GET. El <code>request</code>
  del POST ya no existe en ese momento, así que los datos deben viajar en
  <code>session</code>. Se consumen con <code>removeAttribute</code> para que
  una recarga posterior no los vuelva a mostrar.
</div>

</body>
</html>
