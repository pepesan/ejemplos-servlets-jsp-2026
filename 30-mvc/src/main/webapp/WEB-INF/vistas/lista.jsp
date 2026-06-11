<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Catálogo de productos</title>
    <style>
        body  { font-family: sans-serif; max-width: 800px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #4a90d9; padding-bottom: 8px; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td { padding: 10px 14px; border: 1px solid #ddd; text-align: left; }
        th    { background: #4a90d9; color: #fff; }
        tr:nth-child(even) { background: #f5f7fa; }
        a     { color: #4a90d9; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .btn  { display: inline-block; padding: 8px 16px; background: #4a90d9;
                color: #fff; border-radius: 4px; margin-bottom: 16px; }
        .btn:hover { background: #357ab8; text-decoration: none; }
        .vacio { color: #888; font-style: italic; margin: 20px 0; }
    </style>
</head>
<body>
    <h1>Catálogo de productos</h1>

    <a class="btn" href="${pageContext.request.contextPath}/app/productos/nuevo">+ Nuevo producto</a>

    <c:choose>
        <c:when test="${empty productos}">
            <p class="vacio">No hay productos registrados.</p>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Acción</th>
                </tr>
                <c:forEach var="p" items="${productos}">
                    <tr>
                        <td><c:out value="${p.id}"/></td>
                        <td><a href="${pageContext.request.contextPath}/app/productos/ver?id=${p.id}"><c:out value="${p.nombre}"/></a></td>
                        <td><fmt:formatNumber value="${p.precio}" type="currency" currencySymbol="€"
                                              maxFractionDigits="2" minFractionDigits="2"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/app/productos/ver?id=${p.id}">Ver</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <p><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
