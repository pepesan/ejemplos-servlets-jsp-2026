<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agenda de contactos</title>
    <style>
        body  { font-family: sans-serif; max-width: 900px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #2ecc71; padding-bottom: 8px; }
        .toolbar { display: flex; gap: 12px; align-items: center; margin: 16px 0; flex-wrap: wrap; }
        .btn  { display: inline-block; padding: 8px 16px; background: #2ecc71;
                color: #fff; border-radius: 4px; text-decoration: none; border: none; cursor: pointer; font-size: 1em; }
        .btn:hover { background: #27ae60; }
        .btn-danger { background: #e74c3c; }
        .btn-danger:hover { background: #c0392b; }
        .btn-outline { background: #fff; color: #2ecc71; border: 1px solid #2ecc71; }
        .btn-outline:hover { background: #2ecc71; color: #fff; }
        .btn-outline.activo { background: #2ecc71; color: #fff; }
        select { padding: 8px; border: 1px solid #ccc; border-radius: 4px; font-size: 1em; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td { padding: 10px 14px; border: 1px solid #ddd; text-align: left; }
        th    { background: #2ecc71; color: #fff; }
        tr:nth-child(even) { background: #f5f7fa; }
        .badge { display: inline-block; padding: 2px 8px; border-radius: 10px; font-size: 0.8em; font-weight: bold; }
        .badge-AMIGO   { background: #d5f5e3; color: #1e8449; }
        .badge-TRABAJO { background: #d6eaf8; color: #1a5276; }
        .badge-FAMILIA { background: #fdebd0; color: #784212; }
        .acciones { display: flex; gap: 6px; }
        a     { color: #2ecc71; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .vacio { color: #888; font-style: italic; margin: 20px 0; }
    </style>
</head>
<body>
    <h1>Agenda de contactos</h1>

    <div class="toolbar">
        <a class="btn" href="${pageContext.request.contextPath}/app/contactos/nuevo">+ Nuevo contacto</a>

        <%-- Filtro por categoría --%>
        <form method="get" action="${pageContext.request.contextPath}/app/contactos" style="display:flex;gap:8px;align-items:center">
            <select name="categoria">
                <option value="">Todas las categorías</option>
                <c:forEach var="cat" items="${categorias}">
                    <option value="${cat}" ${categoriaActiva == cat ? 'selected' : ''}>${cat}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-outline">Filtrar</button>
            <c:if test="${not empty categoriaActiva}">
                <a href="${pageContext.request.contextPath}/app/contactos" class="btn btn-outline">✕ Limpiar</a>
            </c:if>
        </form>
    </div>

    <c:choose>
        <c:when test="${empty contactos}">
            <p class="vacio">No hay contactos
                <c:if test="${not empty categoriaActiva}"> en la categoría <strong>${categoriaActiva}</strong></c:if>.
            </p>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Teléfono</th>
                    <th>Categoría</th>
                    <th>Acciones</th>
                </tr>
                <c:forEach var="c" items="${contactos}">
                    <tr>
                        <td><c:out value="${c.nombre}"/></td>
                        <td><c:out value="${c.email}"/></td>
                        <td><c:out value="${c.telefono}"/></td>
                        <td><span class="badge badge-${c.categoria}">${c.categoria}</span></td>
                        <td class="acciones">
                            <a href="${pageContext.request.contextPath}/app/contactos/ver?id=${c.id}">Ver</a>
                            <a href="${pageContext.request.contextPath}/app/contactos/editar?id=${c.id}">Editar</a>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/app/contactos/eliminar"
                                  onsubmit="return confirm('¿Eliminar a ${c.nombre}?')"
                                  style="display:inline">
                                <input type="hidden" name="id" value="${c.id}">
                                <button type="submit" class="btn btn-danger" style="padding:4px 10px;font-size:0.85em">Eliminar</button>
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
