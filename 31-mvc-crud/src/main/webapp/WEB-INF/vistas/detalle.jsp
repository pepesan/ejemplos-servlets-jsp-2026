<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Contacto: <c:out value="${contacto.nombre}"/></title>
    <style>
        body  { font-family: sans-serif; max-width: 560px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #2ecc71; padding-bottom: 8px; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td { padding: 10px 14px; border: 1px solid #ddd; }
        th    { background: #f5f7fa; width: 35%; text-align: left; }
        .badge { display: inline-block; padding: 3px 10px; border-radius: 10px; font-weight: bold; }
        .badge-AMIGO   { background: #d5f5e3; color: #1e8449; }
        .badge-TRABAJO { background: #d6eaf8; color: #1a5276; }
        .badge-FAMILIA { background: #fdebd0; color: #784212; }
        .acciones { display: flex; gap: 10px; margin-top: 24px; flex-wrap: wrap; align-items: center; }
        a.btn { display: inline-block; padding: 9px 18px; background: #2ecc71;
                color: #fff; text-decoration: none; border-radius: 4px; }
        a.btn:hover { background: #27ae60; }
        button.btn-danger { padding: 9px 18px; background: #e74c3c; color: #fff;
                            border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        button.btn-danger:hover { background: #c0392b; }
        a { color: #2ecc71; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Detalle del contacto</h1>

    <table>
        <tr><th>ID</th>         <td>${contacto.id}</td></tr>
        <tr><th>Nombre</th>     <td><c:out value="${contacto.nombre}"/></td></tr>
        <tr><th>Email</th>      <td><c:out value="${contacto.email}"/></td></tr>
        <tr><th>Teléfono</th>   <td><c:out value="${contacto.telefono}"/></td></tr>
        <tr>
            <th>Categoría</th>
            <td><span class="badge badge-${contacto.categoria}">${contacto.categoria}</span></td>
        </tr>
    </table>

    <div class="acciones">
        <a class="btn" href="${pageContext.request.contextPath}/app/contactos/editar?id=${contacto.id}">
            Editar
        </a>
        <form method="post"
              action="${pageContext.request.contextPath}/app/contactos/eliminar"
              onsubmit="return confirm('¿Eliminar a ${contacto.nombre}?')">
            <input type="hidden" name="id" value="${contacto.id}">
            <button type="submit" class="btn-danger">Eliminar</button>
        </form>
        <a href="${pageContext.request.contextPath}/app/contactos">← Volver a la agenda</a>
    </div>
</body>
</html>
