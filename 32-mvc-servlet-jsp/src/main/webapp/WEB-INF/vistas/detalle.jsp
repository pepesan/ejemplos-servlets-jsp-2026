<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Vista:       detalle.jsp
  Controller:  AlumnoController.ver(req, resp)

  El controller hace req.setAttribute("alumno", alumno) y esta JSP lo lee con EL:

    setAttribute("alumno", Alumno) → ${alumno}

  Propiedades del objeto Alumno (EL llama al getter; isX() para booleanos):

    ${alumno.id}        →  getId()
    ${alumno.nombre}    →  getNombre()
    ${alumno.email}     →  getEmail()
    ${alumno.nota}      →  getNota()        usada en <fmt:formatNumber value="${alumno.nota}">
    ${alumno.activo}    →  isActivo()       usada en <c:if test="${alumno.activo}">
    ${alumno.aprobado}  →  isAprobado()     propiedad calculada (nota >= 5), sin campo propio
    ${alumno.fechaAlta} →  getFechaAlta()   usada en <fmt:formatDate value="${alumno.fechaAlta}">
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de alumno</title>
    <style>
        body   { font-family: sans-serif; max-width: 600px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #3498db; padding-bottom: 8px; }
        dl     { display: grid; grid-template-columns: 140px 1fr; gap: 10px 16px; margin: 20px 0; }
        dt     { font-weight: bold; color: #555; }
        dd     { margin: 0; }
        .badge { display: inline-block; padding: 3px 10px; border-radius: 10px;
                 font-size: 0.85em; font-weight: bold; }
        .badge-sobresaliente { background: #d5f5e3; color: #1a5c35; }
        .badge-notable       { background: #d6eaf8; color: #1a4a7a; }
        .badge-aprobado      { background: #fef9e7; color: #7d6608; }
        .badge-suspenso      { background: #fdedec; color: #922b21; }
        .badge-activo        { background: #d5f5e3; color: #1a5c35; }
        .badge-inactivo      { background: #f0f4f8; color: #888; }
        .btn   { display: inline-block; padding: 8px 16px; background: #3498db;
                 color: #fff; border-radius: 4px; text-decoration: none; }
        .btn:hover { background: #2980b9; }
        a      { color: #3498db; }
    </style>
</head>
<body>

<h1>Ficha del alumno</h1>

<dl>
    <dt>ID:</dt>
    <dd>${alumno.id}</dd>

    <dt>Nombre:</dt>
    <dd><c:out value="${alumno.nombre}"/></dd>

    <dt>Email:</dt>
    <dd><c:out value="${alumno.email}"/></dd>

    <dt>Nota:</dt>
    <dd>
        <fmt:formatNumber value="${alumno.nota}" pattern="0.00"/>
        <c:choose>
            <c:when test="${alumno.nota >= 9}">
                <span class="badge badge-sobresaliente">Sobresaliente</span>
            </c:when>
            <c:when test="${alumno.nota >= 7}">
                <span class="badge badge-notable">Notable</span>
            </c:when>
            <c:when test="${alumno.nota >= 5}">
                <span class="badge badge-aprobado">Aprobado</span>
            </c:when>
            <c:otherwise>
                <span class="badge badge-suspenso">Suspenso</span>
            </c:otherwise>
        </c:choose>
    </dd>

    <dt>Fecha de alta:</dt>
    <dd><fmt:formatDate value="${alumno.fechaAlta}" pattern="dd/MM/yyyy HH:mm"/></dd>

    <dt>Estado:</dt>
    <dd>
        <c:if test="${alumno.activo}">
            <span class="badge badge-activo">Activo</span>
        </c:if>
        <c:if test="${not alumno.activo}">
            <span class="badge badge-inactivo">Inactivo</span>
        </c:if>
    </dd>
</dl>

<p>
    <a href="${pageContext.request.contextPath}/app/alumnos/editar?id=${alumno.id}" class="btn">Editar</a>
    &nbsp;
    <a href="${pageContext.request.contextPath}/app/alumnos">← Volver a la lista</a>
</p>

</body>
</html>
