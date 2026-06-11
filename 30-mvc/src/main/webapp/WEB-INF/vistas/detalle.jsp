<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle: <c:out value="${producto.nombre}"/></title>
    <style>
        body  { font-family: sans-serif; max-width: 600px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #4a90d9; padding-bottom: 8px; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td { padding: 10px 14px; border: 1px solid #ddd; }
        th    { background: #f5f7fa; width: 35%; text-align: left; }
        a     { color: #4a90d9; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Detalle del producto</h1>

    <table>
        <tr><th>ID</th>     <td><c:out value="${producto.id}"/></td></tr>
        <tr><th>Nombre</th> <td><c:out value="${producto.nombre}"/></td></tr>
        <tr>
            <th>Precio</th>
            <td>
                <fmt:formatNumber value="${producto.precio}" type="currency"
                                  currencySymbol="€" maxFractionDigits="2" minFractionDigits="2"/>
            </td>
        </tr>
    </table>

    <p style="margin-top:20px">
        <a href="${pageContext.request.contextPath}/app/productos">← Volver al catálogo</a>
    </p>
</body>
</html>
