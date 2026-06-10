<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Vista:       formulario.jsp
  Controllers: EmpleadoController.formularioNuevo()  → esNuevo=true,  empleado vacío
               EmpleadoController.formularioEditar() → esNuevo=false, empleado con datos de BD
               EmpleadoController.guardar()          → en caso de error de validación

  Atributos recibidos via setAttribute:

    setAttribute("empleado",  Empleado)             → ${empleado}
      Pre-rellena los campos: value="<c:out value='${empleado.nombre}'/>".
      Para nuevo, el objeto está vacío. Para editar o tras error, tiene los datos introducidos.

    setAttribute("esNuevo",   boolean)              → ${esNuevo}
      Controla título, botón y la presencia del campo oculto con el id.

    setAttribute("errores",   Map<String,String>)   → ${errores}   (solo si hay errores)
      Mapa campo → mensaje. Acceso por clave: ${errores.nombre} → errores.get("nombre").
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>
        <c:if test="${esNuevo}">Nuevo empleado</c:if>
        <c:if test="${not esNuevo}">Editar empleado</c:if>
    </title>
    <style>
        body    { font-family: sans-serif; max-width: 500px; margin: 40px auto; color: #333; }
        h1      { border-bottom: 2px solid #27ae60; padding-bottom: 8px; }
        .campo  { margin-bottom: 16px; }
        label   { display: block; font-weight: bold; margin-bottom: 4px; }
        input[type=text], input[type=number] {
            width: 100%; padding: 8px; border: 1px solid #ccc;
            border-radius: 4px; font-size: 1em; box-sizing: border-box;
        }
        input.error { border-color: #e74c3c; }
        .msg-error  { color: #e74c3c; font-size: 0.85em; margin-top: 4px; }
        .btn        { padding: 10px 20px; background: #27ae60; color: #fff;
                      border-radius: 4px; border: none; cursor: pointer; font-size: 1em; }
        .btn:hover  { background: #219a52; }
        a           { color: #27ae60; }
    </style>
</head>
<body>

<h1>
    <c:if test="${esNuevo}">Nuevo empleado</c:if>
    <c:if test="${not esNuevo}">Editar empleado — <c:out value="${empleado.nombre}"/></c:if>
</h1>

<%-- Bloque de errores: ${errores} es un Map<String,String> con campo→mensaje --%>
<c:if test="${not empty errores}">
    <div style="background:#fdedec;border:1px solid #e74c3c;border-radius:4px;padding:12px;margin-bottom:16px">
        <strong>Corrige los siguientes errores:</strong>
        <ul style="margin:6px 0 0 0">
            <%-- c:forEach itera el mapa: var="error" accede a entry.key y entry.value --%>
            <c:forEach var="error" items="${errores}">
                <li><c:out value="${error.value}"/></li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/app/empleados/guardar">

    <%-- Campo oculto solo en modo edición: ${empleado.id} viene de Hibernate (Long) --%>
    <c:if test="${not esNuevo}">
        <input type="hidden" name="id" value="${empleado.id}">
    </c:if>

    <div class="campo">
        <label for="nombre">Nombre *</label>
        <%-- c:out escapa HTML; ${errores.nombre} lee errores.get("nombre") del mapa --%>
        <input type="text" id="nombre" name="nombre"
               value="<c:out value='${empleado.nombre}'/>"
               class="${not empty errores.nombre ? 'error' : ''}">
        <c:if test="${not empty errores.nombre}">
            <div class="msg-error"><c:out value="${errores.nombre}"/></div>
        </c:if>
    </div>

    <div class="campo">
        <label for="departamento">Departamento</label>
        <input type="text" id="departamento" name="departamento"
               value="<c:out value='${empleado.departamento}'/>">
    </div>

    <div class="campo">
        <label for="salario">Salario (€) *</label>
        <input type="number" id="salario" name="salario"
               min="0.01" step="0.01"
               value="${empleado.salario > 0 ? empleado.salario : ''}"
               class="${not empty errores.salario ? 'error' : ''}">
        <c:if test="${not empty errores.salario}">
            <div class="msg-error"><c:out value="${errores.salario}"/></div>
        </c:if>
    </div>

    <div class="campo">
        <label>
            <input type="checkbox" name="activo"
                   <c:if test="${empleado.activo}">checked</c:if>>
            Empleado activo
        </label>
    </div>

    <p>
        <button type="submit" class="btn">
            <c:if test="${esNuevo}">Crear empleado</c:if>
            <c:if test="${not esNuevo}">Guardar cambios</c:if>
        </button>
        &nbsp;
        <a href="${pageContext.request.contextPath}/app/empleados">Cancelar</a>
    </p>

</form>
</body>
</html>
