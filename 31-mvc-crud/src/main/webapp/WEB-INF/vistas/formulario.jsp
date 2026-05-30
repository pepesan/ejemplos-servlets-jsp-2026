<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Formulario reutilizable para CREAR y EDITAR un contacto.
  Si hay un atributo "contacto" en el request → modo editar (pre-rellena campos).
  Si no hay "contacto"                        → modo crear (campos vacíos).
  En ambos casos, si hay errores de validación los campos individuales
  vienen en request con el valor enviado (nombre, email, telefono, categoria).
--%>
<c:set var="esEdicion" value="${not empty contacto}"/>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><c:choose><c:when test="${esEdicion}">Editar contacto</c:when><c:otherwise>Nuevo contacto</c:otherwise></c:choose></title>
    <style>
        body   { font-family: sans-serif; max-width: 520px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #2ecc71; padding-bottom: 8px; }
        label  { display: block; margin-top: 14px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; box-sizing: border-box;
                        border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; font-size: 1em; }
        input.error, select.error { border-color: #e74c3c; }
        .msg-error  { color: #e74c3c; font-size: 0.875em; margin-top: 3px; }
        button { margin-top: 20px; padding: 10px 24px; background: #2ecc71;
                 color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        button:hover { background: #27ae60; }
        a      { color: #2ecc71; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .hint  { font-size: 0.8em; color: #888; margin-top: 2px; }
    </style>
</head>
<body>
    <h1>
        <c:choose>
            <c:when test="${esEdicion}">Editar contacto</c:when>
            <c:otherwise>Nuevo contacto</c:otherwise>
        </c:choose>
    </h1>

    <%-- La acción del form depende del modo --%>
    <c:choose>
        <c:when test="${esEdicion}">
            <c:set var="accion" value="${pageContext.request.contextPath}/app/contactos/editar?id=${contacto.id}"/>
        </c:when>
        <c:otherwise>
            <c:set var="accion" value="${pageContext.request.contextPath}/app/contactos/nuevo"/>
        </c:otherwise>
    </c:choose>

    <form method="post" action="${accion}">

        <%-- Nombre --%>
        <label for="nombre">Nombre *</label>
        <input type="text" id="nombre" name="nombre"
               value="<c:out value='${not empty nombre ? nombre : contacto.nombre}'/>"
               class="${not empty errores.nombre ? 'error' : ''}">
        <c:if test="${not empty errores.nombre}">
            <div class="msg-error"><c:out value="${errores.nombre}"/></div>
        </c:if>

        <%-- Email --%>
        <label for="email">Email *</label>
        <input type="text" id="email" name="email"
               value="<c:out value='${not empty email ? email : contacto.email}'/>"
               class="${not empty errores.email ? 'error' : ''}">
        <c:if test="${not empty errores.email}">
            <div class="msg-error"><c:out value="${errores.email}"/></div>
        </c:if>

        <%-- Teléfono --%>
        <label for="telefono">Teléfono <span class="hint">(opcional)</span></label>
        <input type="text" id="telefono" name="telefono"
               value="<c:out value='${not empty telefono ? telefono : contacto.telefono}'/>"
               class="${not empty errores.telefono ? 'error' : ''}">
        <c:if test="${not empty errores.telefono}">
            <div class="msg-error"><c:out value="${errores.telefono}"/></div>
        </c:if>

        <%-- Categoría --%>
        <label for="categoria">Categoría *</label>
        <c:set var="catActual" value="${not empty categoria ? categoria : contacto.categoria}"/>
        <select id="categoria" name="categoria"
                class="${not empty errores.categoria ? 'error' : ''}">
            <option value="">-- Selecciona --</option>
            <c:forEach var="cat" items="${categorias}">
                <option value="${cat}" ${catActual == cat ? 'selected' : ''}>${cat}</option>
            </c:forEach>
        </select>
        <c:if test="${not empty errores.categoria}">
            <div class="msg-error"><c:out value="${errores.categoria}"/></div>
        </c:if>

        <button type="submit">
            <c:choose>
                <c:when test="${esEdicion}">Guardar cambios</c:when>
                <c:otherwise>Crear contacto</c:otherwise>
            </c:choose>
        </button>
    </form>

    <p style="margin-top:20px">
        <a href="${pageContext.request.contextPath}/app/contactos">← Volver a la agenda</a>
    </p>
</body>
</html>
