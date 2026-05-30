<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><title>Registro completado</title>
<style>
body{font-family:monospace;max-width:560px;margin:4em auto;background:#1e1e2e;
     color:#cdd6f4;text-align:center}
h1{color:#a6e3a1;font-size:2em}p{color:#a6adc8;line-height:1.6}a{color:#89dceb}
.icono{font-size:3.5em;display:block;margin-bottom:.3em}
.nota{background:#313244;padding:.7em 1em;border-radius:4px;font-size:.82em;
      color:#585b70;margin-top:1.5em;text-align:left}
code{color:#a6e3a1}
</style>
</head>
<body>

<%
    // Lee el nombre guardado en sesión por el servlet tras el POST exitoso
    String nombre = "";
    javax.servlet.http.HttpSession s = request.getSession(false);
    if (s != null) {
        Object v = s.getAttribute("usuarioRegistrado");
        if (v != null) {
            nombre = (String) v;
            s.removeAttribute("usuarioRegistrado"); // se consume una sola vez
        }
    }
    request.setAttribute("nombre", nombre);
%>

<span class="icono">&#10003;</span>
<h1>¡Registro completado!</h1>

<c:if test="${not empty nombre}">
  <p>Bienvenido, <strong>${nombre}</strong>.</p>
</c:if>
<c:if test="${empty nombre}">
  <p>Tu cuenta ha sido creada correctamente.</p>
</c:if>

<p><a href="/registro">Registrar otro usuario</a></p>

<div class="nota" style="text-align:left">
  <strong>¿Por qué esta página no se reenvía al recargar?</strong><br>
  El servlet hizo <code>sendRedirect("/registro?ok=1")</code> después del POST exitoso.
  El navegador lanzó un GET nuevo; recargar repite solo ese GET, no el POST original.
  Eso es el patrón <strong>PRG (Post-Redirect-Get)</strong>.
</div>

</body>
</html>
