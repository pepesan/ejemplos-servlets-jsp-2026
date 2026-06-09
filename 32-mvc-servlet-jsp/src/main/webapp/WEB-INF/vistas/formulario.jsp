<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Atributos recibidos del controlador (AlumnoController.formularioNuevo / .formularioEditar / .guardar):
    alumno   → Alumno              — objeto a editar (vacío si es nuevo; con datos si hay error de validación)
    esNuevo  → boolean             — true = alta nueva, false = edición
    errores  → Map<String,String>  — mapa campo → mensaje (solo presente si hubo errores de validación)
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>
        <c:if test="${esNuevo}">Nuevo alumno</c:if>
        <c:if test="${not esNuevo}">Editar alumno</c:if>
    </title>
    <style>
        body    { font-family: sans-serif; max-width: 500px; margin: 40px auto; color: #333; }
        h1      { border-bottom: 2px solid #3498db; padding-bottom: 8px; }
        .campo  { margin-bottom: 16px; }
        label   { display: block; font-weight: bold; margin-bottom: 4px; }
        input[type=text], input[type=email], input[type=number] {
            width: 100%; padding: 8px; border: 1px solid #ccc;
            border-radius: 4px; font-size: 1em; box-sizing: border-box;
        }
        input.error { border-color: #e74c3c; }
        .msg-error  { color: #e74c3c; font-size: 0.85em; margin-top: 4px; }
        .btn        { display: inline-block; padding: 10px 20px; background: #3498db;
                      color: #fff; border-radius: 4px; border: none; cursor: pointer; font-size: 1em; }
        .btn:hover  { background: #2980b9; }
        a           { color: #3498db; }
    </style>
</head>
<body>

<h1>
    <c:if test="${esNuevo}">Nuevo alumno</c:if>
    <c:if test="${not esNuevo}">Editar alumno — <c:out value="${alumno.nombre}"/></c:if>
</h1>

<%-- ── Si volvemos del guardado con errores, mostrar bloque resumen ─────── --%>
<c:if test="${not empty errores}">
    <div style="background:#fdedec;border:1px solid #e74c3c;border-radius:4px;padding:12px;margin-bottom:16px">
        <strong>Corrige los siguientes errores:</strong>
        <ul style="margin:6px 0 0 0">
            <c:forEach var="error" items="${errores}">
                <li><c:out value="${error.value}"/></li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/app/alumnos/guardar">

    <%-- Campo oculto: presente solo en edición (alumno.id > 0) --%>
    <c:if test="${not esNuevo}">
        <input type="hidden" name="id" value="${alumno.id}">
    </c:if>

    <div class="campo">
        <label for="nombre">Nombre *</label>
        <%-- EL pre-rellena el campo; ${errores.nombre} lee errores['nombre'] del mapa --%>
        <input type="text" id="nombre" name="nombre"
               value="<c:out value='${alumno.nombre}'/>"
               class="${not empty errores.nombre ? 'error' : ''}">
        <c:if test="${not empty errores.nombre}">
            <div class="msg-error"><c:out value="${errores.nombre}"/></div>
        </c:if>
    </div>

    <div class="campo">
        <label for="email">Email</label>
        <input type="email" id="email" name="email"
               value="<c:out value='${alumno.email}'/>">
    </div>

    <div class="campo">
        <label for="nota">Nota (0 – 10) *</label>
        <input type="number" id="nota" name="nota"
               min="0" max="10" step="0.1"
               value="${alumno.nota > 0 || not esNuevo ? alumno.nota : ''}"
               class="${not empty errores.nota ? 'error' : ''}">
        <c:if test="${not empty errores.nota}">
            <div class="msg-error"><c:out value="${errores.nota}"/></div>
        </c:if>
    </div>

    <div class="campo">
        <label>
            <input type="checkbox" name="activo"
                   <c:if test="${alumno.activo}">checked</c:if>>
            Alumno activo
        </label>
    </div>

    <p>
        <button type="submit" class="btn">
            <c:if test="${esNuevo}">Crear alumno</c:if>
            <c:if test="${not esNuevo}">Guardar cambios</c:if>
        </button>
        &nbsp;
        <a href="${pageContext.request.contextPath}/app/alumnos">Cancelar</a>
    </p>

</form>

</body>
</html>
