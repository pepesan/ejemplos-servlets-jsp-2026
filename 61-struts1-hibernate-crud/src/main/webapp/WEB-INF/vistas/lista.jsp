<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Empleados — Struts + Hibernate CRUD</title>
    <style>
        body   { font-family: sans-serif; max-width: 900px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #2980b9; padding-bottom: 8px; }
        .toolbar { display: flex; gap: 12px; align-items: center; margin-bottom: 16px; flex-wrap: wrap; }
        .search  { display: flex; gap: 8px; flex: 1; min-width: 240px; }
        .search input[type=text] { flex: 1; padding: 8px 10px; border: 1px solid #ccc;
                                   border-radius: 4px; font-size: 0.95em; }
        .search button { padding: 8px 16px; background: #2980b9; color: #fff;
                         border: none; border-radius: 4px; cursor: pointer; }
        .search button:hover { background: #2471a3; }
        table  { width: 100%; border-collapse: collapse; }
        th     { background: #2980b9; color: #fff; padding: 10px 12px; text-align: left; }
        td     { padding: 10px 12px; border-bottom: 1px solid #ddd; }
        tr:hover td { background: #f0f6fc; }
        .btn   { display: inline-block; padding: 6px 12px; border-radius: 4px;
                 text-decoration: none; font-size: 0.85em; cursor: pointer; border: none; }
        .btn-nuevo  { background: #27ae60; color: #fff; padding: 9px 18px; font-size: 0.95em; }
        .btn-nuevo:hover  { background: #219a52; }
        .btn-ver    { background: #8e44ad; color: #fff; }
        .btn-ver:hover    { background: #7d3c98; }
        .btn-editar { background: #2980b9; color: #fff; }
        .btn-editar:hover { background: #2471a3; }
        .btn-elim   { background: #e74c3c; color: #fff; }
        .btn-elim:hover   { background: #c0392b; }
        .vacio { color: #999; font-style: italic; padding: 20px 0; }
        .badge { display: inline-block; background: #eaf2fb; color: #2980b9;
                 border-radius: 12px; padding: 2px 10px; font-size: 0.85em; margin-left: 8px; }
        a.limpiar { font-size: 0.85em; color: #888; }
    </style>
</head>
<body>
    <h1>
        Empleados
        <c:if test="${not empty busqueda}">
            <span class="badge">Búsqueda: "<c:out value="${busqueda}"/>"</span>
        </c:if>
    </h1>

    <div class="toolbar">
        <a class="btn btn-nuevo"
           href="${pageContext.request.contextPath}/empleados.do?method=nuevo">+ Nuevo empleado</a>

        <form class="search" method="get"
              action="${pageContext.request.contextPath}/empleados.do">
            <input type="hidden" name="method" value="listar"/>
            <input type="text" name="busqueda" value="<c:out value='${busqueda}'/>"
                   placeholder="Buscar por nombre o departamento…"/>
            <button type="submit">Buscar</button>
        </form>

        <c:if test="${not empty busqueda}">
            <a class="limpiar"
               href="${pageContext.request.contextPath}/empleados.do?method=listar">✕ Ver todos</a>
        </c:if>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Departamento</th>
                <th>Salario</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty empleados}">
                    <tr><td colspan="5" class="vacio">No se encontraron empleados.</td></tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="e" items="${empleados}">
                        <tr>
                            <td>${e.id}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/empleados.do?method=mostrar&amp;id=${e.id}">
                                    <c:out value="${e.nombre}"/>
                                </a>
                            </td>
                            <td><c:out value="${e.departamento}"/></td>
                            <td><fmt:formatNumber value="${e.salario}" minFractionDigits="2" maxFractionDigits="2"/> €</td>
                            <td style="white-space:nowrap">
                                <a class="btn btn-ver"
                                   href="${pageContext.request.contextPath}/empleados.do?method=mostrar&amp;id=${e.id}">Ver</a>
                                <a class="btn btn-editar"
                                   href="${pageContext.request.contextPath}/empleados.do?method=editar&amp;id=${e.id}">Editar</a>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/empleados.do"
                                      style="display:inline"
                                      onsubmit="return confirm('¿Eliminar a «${e.nombre}»?')">
                                    <input type="hidden" name="method" value="eliminar"/>
                                    <input type="hidden" name="id"     value="${e.id}"/>
                                    <button type="submit" class="btn btn-elim">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <p style="margin-top:24px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
