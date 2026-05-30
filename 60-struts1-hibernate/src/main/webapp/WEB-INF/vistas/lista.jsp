<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"   prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Empleados — Struts + Hibernate</title>
    <style>
        body  { font-family: sans-serif; max-width: 860px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #2980b9; padding-bottom: 8px; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th    { background: #2980b9; color: #fff; padding: 10px 12px; text-align: left; }
        td    { padding: 10px 12px; border-bottom: 1px solid #ddd; }
        tr:hover td { background: #f0f6fc; }
        .btn          { display: inline-block; padding: 8px 16px; border-radius: 4px;
                        text-decoration: none; font-size: .9em; }
        .btn-primary  { background: #2980b9; color: #fff; }
        .btn-danger   { background: #e74c3c; color: #fff; }
        .btn:hover    { opacity: .85; }
        .vacio        { color: #999; font-style: italic; }
    </style>
</head>
<body>
    <h1>Lista de empleados</h1>

    <a class="btn btn-primary" href="${pageContext.request.contextPath}/crear.do">+ Nuevo empleado</a>

    <table>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Departamento</th>
            <th>Salario</th>
            <th></th>
        </tr>
        <c:choose>
            <c:when test="${empty empleados}">
                <tr><td colspan="5" class="vacio">No hay empleados registrados.</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="e" items="${empleados}">
                    <tr>
                        <td>${e.id}</td>
                        <td><c:out value="${e.nombre}"/></td>
                        <td><c:out value="${e.departamento}"/></td>
                        <td><fmt:formatNumber value="${e.salario}" minFractionDigits="2" maxFractionDigits="2"/> €</td>
                        <td>
                            <a class="btn btn-danger"
                               href="${pageContext.request.contextPath}/eliminar.do?id=${e.id}"
                               onclick="return confirm('¿Eliminar a ${e.nombre}?')">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>

    <p style="margin-top: 24px">
        <a href="${pageContext.request.contextPath}/">← Inicio</a>
    </p>
</body>
</html>
