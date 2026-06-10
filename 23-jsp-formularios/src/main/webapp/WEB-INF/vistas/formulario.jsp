<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><title>Registro</title>
<style>
body{font-family:monospace;max-width:560px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}p{color:#a6adc8;line-height:1.6}a{color:#89dceb}code{color:#a6e3a1}
.campo{margin:.9em 0}
label{display:block;font-size:.9em;color:#cba6f7;margin-bottom:.3em}
input{width:100%;box-sizing:border-box;background:#313244;color:#cdd6f4;
      border:1px solid #45475a;padding:.45em .8em;border-radius:4px;
      font-family:monospace;font-size:.95em}
input.error-field{border-color:#f38ba8}
.error{color:#f38ba8;font-size:.82em;margin-top:.25em;display:block}
button{margin-top:1.2em;background:#89b4fa;color:#1e1e2e;border:none;
       padding:.55em 1.6em;border-radius:4px;cursor:pointer;font-weight:bold;
       font-size:1em;font-family:monospace}
button:hover{background:#b4d0f7}
.resumen-errores{background:#f38ba820;border:1px solid #f38ba8;border-radius:4px;
                 padding:.7em 1em;margin-bottom:1em;font-size:.88em;color:#f38ba8}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.82em;
      color:#585b70;margin-top:1.5em}
</style>
</head>
<body>

<p><a href="${pageContext.request.contextPath}/">← Inicio</a>
   &nbsp;|&nbsp;
   <a href="${pageContext.request.contextPath}/editar">Editar perfil →</a></p>
<h1>Formulario de registro</h1>
<p>Ejemplo del ciclo GET→POST→forward/redirect con validación y patrón PRG.</p>

<%-- Resumen de errores — solo visible si hay errores --%>
<c:if test="${not empty errores}">
  <div class="resumen-errores">
    &#9888; Hay <strong>${errores.size()}</strong> error<c:if test="${errores.size() gt 1}">es</c:if>
    que corregir antes de continuar.
  </div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/registro" novalidate>

  <div class="campo">
    <label for="nombre">Nombre *</label>
    <input type="text" id="nombre" name="nombre"
           value="<c:out value='${bean.nombre}'/>"
           class="${not empty errores.nombre ? 'error-field' : ''}"
           placeholder="Mínimo 3 caracteres" autofocus>
    <c:if test="${not empty errores.nombre}">
      <span class="error">&#9888; ${errores.nombre}</span>
    </c:if>
  </div>

  <div class="campo">
    <label for="email">Correo electrónico *</label>
    <input type="email" id="email" name="email"
           value="<c:out value='${bean.email}'/>"
           class="${not empty errores.email ? 'error-field' : ''}"
           placeholder="usuario@ejemplo.com">
    <c:if test="${not empty errores.email}">
      <span class="error">&#9888; ${errores.email}</span>
    </c:if>
  </div>

  <div class="campo">
    <label for="password">Contraseña *</label>
    <input type="password" id="password" name="password"
           class="${not empty errores.password ? 'error-field' : ''}"
           placeholder="Mínimo 6 caracteres">
    <c:if test="${not empty errores.password}">
      <span class="error">&#9888; ${errores.password}</span>
    </c:if>
  </div>

  <div class="campo">
    <label for="edad">Edad *</label>
    <input type="text" id="edad" name="edad"
           value="<c:out value='${bean.edad}'/>"
           class="${not empty errores.edad ? 'error-field' : ''}"
           placeholder="Número entre 18 y 120" style="width:120px">
    <c:if test="${not empty errores.edad}">
      <span class="error">&#9888; ${errores.edad}</span>
    </c:if>
  </div>

  <button type="submit">Registrarse</button>

</form>

<div class="nota">
  <strong>Flujo cuando hay errores (sin PRG):</strong><br>
  POST /registro → validación falla → <code>req.setAttribute("errores", mapa)</code>
  → <code>forward</code> a esta JSP → el formulario se re-renderiza con los errores
  y los valores previos. La URL sigue siendo <code>/registro</code>.<br><br>
  <strong>Flujo cuando hay éxito (PRG):</strong><br>
  POST /registro → validación ok → <code>session.setAttribute</code>
  → <code>sendRedirect("/registro?ok=1")</code> → GET /registro?ok=1 → exito.jsp.
  Recargar la página no reenvía el formulario.
</div>

</body>
</html>
