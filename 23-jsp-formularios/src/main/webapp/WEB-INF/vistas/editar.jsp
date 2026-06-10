<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><title>Editar perfil — usuario ${bean.id}</title>
<style>
body{font-family:monospace;max-width:600px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}p{color:#a6adc8;line-height:1.6}a{color:#89dceb}code{color:#a6e3a1}
.campo{margin:.9em 0}
label{display:block;font-size:.9em;color:#cba6f7;margin-bottom:.3em}
input[type=text],input[type=email],select{
    width:100%;box-sizing:border-box;background:#313244;color:#cdd6f4;
    border:1px solid #45475a;padding:.45em .8em;border-radius:4px;
    font-family:monospace;font-size:.95em}
input.error-field,select.error-field{border-color:#f38ba8}
.error{color:#f38ba8;font-size:.82em;margin-top:.25em;display:block}
.campo-check{display:flex;align-items:center;gap:.6em;margin:.9em 0}
.campo-check input{width:auto}
.campo-check label{margin:0;color:#cba6f7}
button{margin-top:1.2em;background:#89b4fa;color:#1e1e2e;border:none;
       padding:.55em 1.6em;border-radius:4px;cursor:pointer;font-weight:bold;
       font-size:1em;font-family:monospace}
button:hover{background:#b4d0f7}
.resumen-errores{background:#f38ba820;border:1px solid #f38ba8;border-radius:4px;
                 padding:.7em 1em;margin-bottom:1em;font-size:.88em;color:#f38ba8}
.ok-banner{background:#a6e3a120;border:1px solid #a6e3a1;border-radius:4px;
           padding:.7em 1em;margin-bottom:1em;font-size:.88em;color:#a6e3a1}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.82em;
      color:#585b70;margin-top:1.5em}
.nav-usuarios{margin-bottom:1.2em;font-size:.85em}
.nav-usuarios a{margin-right:.8em}
</style>
</head>
<body>

<p><a href="${pageContext.request.contextPath}/">← Inicio</a>
   &nbsp;|&nbsp;
   <a href="${pageContext.request.contextPath}/registro">← Formulario de registro</a></p>
<h1>Editar perfil</h1>

<%-- Navegación entre los 3 usuarios de demo --%>
<div class="nav-usuarios">
  Usuarios de demo:
  <a href="${pageContext.request.contextPath}/editar?id=1">Ana (admin)</a>
  <a href="${pageContext.request.contextPath}/editar?id=2">Luis (editor)</a>
  <a href="${pageContext.request.contextPath}/editar?id=3">Carmen (usuario)</a>
</div>

<%-- Banner de éxito (PRG: llegamos aquí vía GET tras el redirect) --%>
<c:if test="${guardado}">
  <div class="ok-banner">&#10003; Cambios guardados correctamente.</div>
</c:if>

<%-- Resumen de errores --%>
<c:if test="${not empty errores}">
  <div class="resumen-errores">
    &#9888; Hay <strong>${errores.size()}</strong>
    error<c:if test="${errores.size() gt 1}">es</c:if> que corregir.
  </div>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/editar?id=${bean.id}"
      novalidate>

  <%-- ── Nombre: value pre-rellena con el dato existente ── --%>
  <div class="campo">
    <label for="nombre">Nombre *</label>
    <input type="text" id="nombre" name="nombre"
           value="<c:out value='${bean.nombre}'/>"
           class="${not empty errores.nombre ? 'error-field' : ''}"
           autofocus>
    <c:if test="${not empty errores.nombre}">
      <span class="error">&#9888; ${errores.nombre}</span>
    </c:if>
  </div>

  <%-- ── Email: value pre-rellena con el dato existente ── --%>
  <div class="campo">
    <label for="email">Correo electrónico *</label>
    <input type="email" id="email" name="email"
           value="<c:out value='${bean.email}'/>"
           class="${not empty errores.email ? 'error-field' : ''}">
    <c:if test="${not empty errores.email}">
      <span class="error">&#9888; ${errores.email}</span>
    </c:if>
  </div>

  <%-- ── Teléfono: opcional ── --%>
  <div class="campo">
    <label for="telefono">Teléfono (opcional)</label>
    <input type="text" id="telefono" name="telefono"
           value="<c:out value='${bean.telefono}'/>"
           class="${not empty errores.telefono ? 'error-field' : ''}"
           placeholder="9 dígitos" style="width:180px">
    <c:if test="${not empty errores.telefono}">
      <span class="error">&#9888; ${errores.telefono}</span>
    </c:if>
  </div>

  <%-- ── Ciudad ── --%>
  <div class="campo">
    <label for="ciudad">Ciudad *</label>
    <input type="text" id="ciudad" name="ciudad"
           value="<c:out value='${bean.ciudad}'/>"
           class="${not empty errores.ciudad ? 'error-field' : ''}">
    <c:if test="${not empty errores.ciudad}">
      <span class="error">&#9888; ${errores.ciudad}</span>
    </c:if>
  </div>

  <%-- ── Rol: <select> con la opción pre-seleccionada mediante EL ── --%>
  <div class="campo">
    <label for="rol">Rol</label>
    <select id="rol" name="rol" style="width:200px">
      <option value="usuario" ${bean.rol eq 'usuario' ? 'selected' : ''}>Usuario</option>
      <option value="editor"  ${bean.rol eq 'editor'  ? 'selected' : ''}>Editor</option>
      <option value="admin"   ${bean.rol eq 'admin'   ? 'selected' : ''}>Administrador</option>
    </select>
  </div>

  <%-- ── Activo: checkbox pre-marcado según bean.activo ── --%>
  <div class="campo-check">
    <input type="checkbox" id="activo" name="activo" value="true"
           ${bean.activo ? 'checked' : ''}>
    <label for="activo">Cuenta activa</label>
  </div>

  <button type="submit">Guardar cambios</button>

</form>

<div class="nota">
  <strong>Claves del formulario de edición:</strong><br>
  &bull; <code>value="&lt;c:out value='\${bean.campo}'/&gt;"</code> — pre-rellena inputs de texto con el dato existente.<br>
  &bull; <code>\${bean.rol eq 'admin' ? 'selected' : ''}</code> — selecciona la opción correcta en <code>&lt;select&gt;</code>.<br>
  &bull; <code>\${bean.activo ? 'checked' : ''}</code> — marca el checkbox si el campo boolean es true.<br>
  &bull; El checkbox no envía parámetro cuando está desmarcado: el servlet lee
  <code>"true".equals(req.getParameter("activo"))</code>.<br>
  &bull; El <code>action</code> incluye <code>?id=\${bean.id}</code> para que el POST sepa qué registro editar.
</div>

</body>
</html>
