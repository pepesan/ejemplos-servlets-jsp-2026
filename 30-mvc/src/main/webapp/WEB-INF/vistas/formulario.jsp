<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo producto</title>
    <style>
        body   { font-family: sans-serif; max-width: 500px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #4a90d9; padding-bottom: 8px; }
        label  { display: block; margin-top: 14px; font-weight: bold; }
        input  { width: 100%; padding: 8px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; }
        input.error { border-color: #e74c3c; }
        .msg-error  { color: #e74c3c; font-size: 0.875em; margin-top: 3px; }
        button { margin-top: 20px; padding: 10px 24px; background: #4a90d9;
                 color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        button:hover { background: #357ab8; }
        a      { color: #4a90d9; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Nuevo producto</h1>

    <form method="post" action="${pageContext.request.contextPath}/app/productos/nuevo">

        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre"
               value="<c:out value='${producto.getNombre()}'/>"
               class="${not empty errores.nombre ? 'error' : ''}">
        <c:if test="${not empty errores.nombre}">
            <div class="msg-error"><c:out value="${errores.nombre}"/></div>
        </c:if>

        <label for="precio">Precio (€)</label>
        <input type="text" id="precio" name="precio"
               value="<c:out value='${producto.getPrecio()}'/>"
               class="${not empty errores.precio ? 'error' : ''}">
        <c:if test="${not empty errores.precio}">
            <div class="msg-error"><c:out value="${errores.precio}"/></div>
        </c:if>

        <button type="submit">Guardar</button>
    </form>

    <p style="margin-top:20px">
        <a href="${pageContext.request.contextPath}/app/productos">← Volver al catálogo</a>
    </p>
</body>
</html>
