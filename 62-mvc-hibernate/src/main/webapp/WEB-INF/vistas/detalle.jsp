<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Vista:       detalle.jsp
  Controller:  EmpleadoController.ver(req, resp)

  Atributos recibidos via request.setAttribute() → leídos en JSP con EL:

    setAttribute("empleado", Empleado) → ${empleado}

  Propiedades accesibles con EL (EL llama al getter):
    ${empleado.id}           →  getId()
    ${empleado.nombre}       →  getNombre()
    ${empleado.departamento} →  getDepartamento()
    ${empleado.salario}      →  getSalario()    → <fmt:formatNumber value="${empleado.salario}">
    ${empleado.activo}       →  isActivo()      → <c:if test="${empleado.activo}">
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ficha de empleado</title>
    <style>
        body  { font-family: sans-serif; max-width: 600px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #27ae60; padding-bottom: 8px; }
        dl    { display: grid; grid-template-columns: 160px 1fr; gap: 10px 16px; margin: 20px 0; }
        dt    { font-weight: bold; color: #555; }
        dd    { margin: 0; }
        .badge { display: inline-block; padding: 3px 10px; border-radius: 10px;
                 font-size: 0.85em; font-weight: bold; }
        .badge-activo   { background: #d5f5e3; color: #1a5c35; }
        .badge-inactivo { background: #f0f4f8; color: #888; }
        .btn  { display: inline-block; padding: 8px 16px; background: #27ae60;
                color: #fff; border-radius: 4px; text-decoration: none; }
        .btn:hover { background: #219a52; }
        a     { color: #27ae60; }
    </style>
</head>
<body>

<h1>Ficha del empleado</h1>

<dl>
    <dt>ID:</dt>
    <dd>${empleado.id}</dd>

    <dt>Nombre:</dt>
    <dd><c:out value="${empleado.nombre}"/></dd>

    <dt>Departamento:</dt>
    <dd><c:out value="${empleado.departamento}"/></dd>

    <dt>Salario:</dt>
    <%-- fmt:formatNumber con patrón moneda --%>
    <dd><fmt:formatNumber value="${empleado.salario}" pattern="#,##0.00 €"/></dd>

    <dt>Estado:</dt>
    <dd>
        <c:if test="${empleado.activo}">
            <span class="badge badge-activo">Activo</span>
        </c:if>
        <c:if test="${not empleado.activo}">
            <span class="badge badge-inactivo">Inactivo</span>
        </c:if>
    </dd>
</dl>

<p>
    <a href="${pageContext.request.contextPath}/app/empleados/editar?id=${empleado.id}" class="btn">Editar</a>
    &nbsp;
    <a href="${pageContext.request.contextPath}/app/empleados">← Volver a la lista</a>
</p>

</body>
</html>
