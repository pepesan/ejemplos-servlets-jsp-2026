<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Contactos — Struts DispatchAction</title>
    <style>
        body   { font-family: sans-serif; max-width: 800px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #2ecc71; padding-bottom: 8px; }
        table  { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px 12px; border: 1px solid #ddd; text-align: left; }
        th     { background: #2ecc71; color: #fff; }
        tr:nth-child(even) { background: #f0fdf4; }
        .btn        { display: inline-block; padding: 8px 16px; border-radius: 4px;
                      text-decoration: none; font-size: 0.9em; cursor: pointer; }
        .btn-nuevo  { background: #2ecc71; color: #fff; border: none; margin-bottom: 16px; }
        .btn-nuevo:hover  { background: #27ae60; }
        .btn-editar { background: #3498db; color: #fff; border: none; }
        .btn-editar:hover { background: #2980b9; }
        .btn-elim   { background: #e74c3c; color: #fff; border: none; }
        .btn-elim:hover   { background: #c0392b; }
        .vacio { color: #888; font-style: italic; padding: 20px 0; }
        td a   { color: #27ae60; text-decoration: none; }
        td a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Contactos</h1>

    <a class="btn btn-nuevo"
       href="${pageContext.request.contextPath}/contactos.do?method=nuevo">+ Nuevo contacto</a>

    <c:choose>
        <c:when test="${empty contactos}">
            <p class="vacio">No hay contactos registrados.</p>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Email</th>
                        <th>Teléfono</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${contactos}">
                        <tr>
                            <td><c:out value="${c.id}"/></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/contactos.do?method=ver&amp;id=${c.id}">
                                    <c:out value="${c.nombre}"/>
                                </a>
                            </td>
                            <td><c:out value="${c.email}"/></td>
                            <td><c:out value="${c.telefono}"/></td>
                            <td>
                                <a class="btn btn-editar"
                                   href="${pageContext.request.contextPath}/contactos.do?method=editar&amp;id=${c.id}">Editar</a>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/contactos.do"
                                      style="display:inline"
                                      onsubmit="return confirm('¿Eliminar a «${c.nombre}»?')">
                                    <input type="hidden" name="method" value="eliminar"/>
                                    <input type="hidden" name="id"     value="${c.id}"/>
                                    <button type="submit" class="btn btn-elim">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <p style="margin-top:24px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
