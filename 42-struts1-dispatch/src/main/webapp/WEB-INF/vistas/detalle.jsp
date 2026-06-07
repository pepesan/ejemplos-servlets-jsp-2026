<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle — <c:out value="${contacto.nombre}"/></title>
    <style>
        body   { font-family: sans-serif; max-width: 520px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #2ecc71; padding-bottom: 8px; }
        dl     { margin: 24px 0; }
        dt     { font-weight: bold; color: #888; font-size: 0.85em;
                 text-transform: uppercase; letter-spacing: 0.05em; margin-top: 16px; }
        dd     { margin: 4px 0 0 0; font-size: 1.05em; }
        .acciones { margin-top: 28px; display: flex; gap: 12px; align-items: center; }
        .btn-editar { display: inline-block; padding: 9px 20px; background: #3498db;
                      color: #fff; text-decoration: none; border-radius: 4px; font-size: 0.95em; }
        .btn-editar:hover { background: #2980b9; }
        .btn-elim { padding: 9px 20px; background: #e74c3c; color: #fff;
                    border: none; border-radius: 4px; cursor: pointer; font-size: 0.95em; }
        .btn-elim:hover { background: #c0392b; }
        a.volver { color: #666; font-size: 0.95em; }
    </style>
</head>
<body>
    <h1>Contacto #<c:out value="${contacto.id}"/></h1>

    <dl>
        <dt>Nombre</dt>
        <dd><c:out value="${contacto.nombre}"/></dd>

        <dt>Email</dt>
        <dd><c:out value="${contacto.email}"/></dd>

        <dt>Teléfono</dt>
        <dd>
            <c:choose>
                <c:when test="${not empty contacto.telefono}">
                    <c:out value="${contacto.telefono}"/>
                </c:when>
                <c:otherwise><span style="color:#aaa">—</span></c:otherwise>
            </c:choose>
        </dd>
    </dl>

    <div class="acciones">
        <a class="btn-editar"
           href="${pageContext.request.contextPath}/contactos.do?method=editar&amp;id=${contacto.id}">Editar</a>

        <form method="post"
              action="${pageContext.request.contextPath}/contactos.do"
              onsubmit="return confirm('¿Eliminar a «${contacto.nombre}»?')">
            <input type="hidden" name="method" value="eliminar"/>
            <input type="hidden" name="id"     value="${contacto.id}"/>
            <button type="submit" class="btn-elim">Eliminar</button>
        </form>

        <a class="volver"
           href="${pageContext.request.contextPath}/contactos.do?method=listar">← Volver a la lista</a>
    </div>
</body>
</html>
