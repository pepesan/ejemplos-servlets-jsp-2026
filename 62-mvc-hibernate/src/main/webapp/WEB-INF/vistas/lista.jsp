<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Vista:       lista.jsp
  Controller:  EmpleadoController.listar(req, resp)

  Atributos recibidos via request.setAttribute() → leídos en JSP con EL:

    setAttribute("empleados", List<Empleado>) → ${empleados}  → <c:forEach items="${empleados}">
    setAttribute("total",     int)            → ${total}      → texto directo
    setAttribute("buscar",    String)         → ${buscar}     → value del campo de búsqueda
                                                                 (solo presente si se buscó algo)

  Propiedades de Empleado (${e.propiedad} → e.getPropiedad() / e.isPropiedad()):
    ${e.id}  ${e.nombre}  ${e.departamento}  ${e.salario}  ${e.activo}
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Empleados</title>
    <style>
        body  { font-family: sans-serif; max-width: 1000px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #27ae60; padding-bottom: 8px; }
        .toolbar { display: flex; gap: 12px; align-items: center; margin: 16px 0; flex-wrap: wrap; }
        .btn  { display: inline-block; padding: 8px 16px; background: #27ae60;
                color: #fff; border-radius: 4px; text-decoration: none; border: none;
                cursor: pointer; font-size: 1em; }
        .btn:hover      { background: #219a52; }
        .btn-danger     { background: #e74c3c; }
        .btn-danger:hover { background: #c0392b; }
        .btn-outline    { background: #fff; color: #27ae60; border: 1px solid #27ae60; }
        .btn-outline:hover { background: #27ae60; color: #fff; }
        input[type=text]{ padding: 7px 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 1em; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td{ padding: 10px 14px; border: 1px solid #ddd; text-align: left; }
        th    { background: #27ae60; color: #fff; }
        tr:nth-child(even) { background: #f5f7fa; }
        .badge { display: inline-block; padding: 2px 8px; border-radius: 10px;
                 font-size: 0.8em; font-weight: bold; }
        .badge-activo   { background: #d5f5e3; color: #1a5c35; }
        .badge-inactivo { background: #f0f4f8; color: #888; }
        .acciones { display: flex; gap: 6px; align-items: center; }
        a   { color: #27ae60; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .vacio { color: #888; font-style: italic; }
        .info  { color: #555; font-size: 0.9em; }
    </style>
</head>
<body>

<h1>Lista de empleados</h1>

<div class="toolbar">
    <a href="${pageContext.request.contextPath}/app/empleados/nuevo" class="btn">+ Nuevo empleado</a>

    <%-- Búsqueda por nombre o departamento --%>
    <form method="get" action="${pageContext.request.contextPath}/app/empleados"
          style="display:flex;gap:8px;align-items:center">
        <%-- value="${buscar}" pre-rellena con el último texto buscado --%>
        <input type="text" name="buscar" value="<c:out value='${buscar}'/>"
               placeholder="Buscar nombre o departamento…">
        <button type="submit" class="btn btn-outline">Buscar</button>
        <c:if test="${not empty buscar}">
            <a href="${pageContext.request.contextPath}/app/empleados" class="btn btn-outline">✕ Limpiar</a>
        </c:if>
    </form>
</div>

<%-- Estadísticas --%>
<p class="info">
    <c:if test="${not empty buscar}">
        Resultados para "<strong><c:out value="${buscar}"/></strong>":
    </c:if>
    <strong>${total}</strong> empleado<c:if test="${total != 1}">s</c:if>
</p>

<%-- Lista con c:choose para el caso vacío --%>
<c:choose>
    <c:when test="${empty empleados}">
        <p class="vacio">No se encontraron empleados.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Departamento</th>
                <th>Salario</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>

            <%-- c:forEach itera List<Empleado>; varStatus da el índice --%>
            <c:forEach var="e" items="${empleados}" varStatus="estado">
                <tr>
                    <td>${estado.count}</td>
                    <td><c:out value="${e.nombre}"/></td>
                    <td><c:out value="${e.departamento}"/></td>

                    <%-- fmt:formatNumber formatea el salario con separador de miles y 2 decimales --%>
                    <td><fmt:formatNumber value="${e.salario}" pattern="#,##0.00 €"/></td>

                    <%-- c:if para el campo booleano activo --%>
                    <td>
                        <c:if test="${e.activo}">
                            <span class="badge badge-activo">Activo</span>
                        </c:if>
                        <c:if test="${not e.activo}">
                            <span class="badge badge-inactivo">Inactivo</span>
                        </c:if>
                    </td>

                    <td class="acciones">
                        <a href="${pageContext.request.contextPath}/app/empleados/ver?id=${e.id}">Ver</a>
                        <a href="${pageContext.request.contextPath}/app/empleados/editar?id=${e.id}">Editar</a>
                        <form method="post"
                              action="${pageContext.request.contextPath}/app/empleados/eliminar"
                              onsubmit="return confirm('¿Eliminar a ${e.nombre}?')"
                              style="display:inline">
                            <input type="hidden" name="id" value="${e.id}">
                            <button type="submit" class="btn btn-danger"
                                    style="padding:4px 10px;font-size:0.85em">Eliminar</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<p style="margin-top:20px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
