<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Candidatura registrada</title>
    <style>
        *, *::before, *::after { box-sizing: border-box; }
        body   { font-family: sans-serif; max-width: 660px; margin: 40px auto;
                 color: #222; padding: 0 16px; background: #fafafa; }
        h1     { color: #27ae60; border-bottom: 3px solid #27ae60; padding-bottom: 10px; }
        .ok    { background: #eafaf1; border-left: 4px solid #27ae60;
                 padding: 12px 16px; border-radius: 4px; margin: 16px 0; font-size: 1.05em; }
        table  { border-collapse: collapse; width: 100%; margin-top: 16px; }
        th, td { padding: 10px 14px; border: 1px solid #d5d8dc; text-align: left;
                 vertical-align: top; }
        th     { background: #f2f3f4; width: 38%; }
        .tags  { display: flex; flex-wrap: wrap; gap: 6px; }
        .tag   { background: #2980b9; color: #fff; padding: 2px 10px;
                 border-radius: 20px; font-size: .85em; }
        a      { color: #2980b9; }
    </style>
</head>
<body>

<h1>&#10003; Candidatura registrada</h1>
<div class="ok">Todos los datos del formulario han sido validados y procesados correctamente.</div>

<table>
    <tr><th>Nombre</th>
        <td><c:out value="${registro.nombre}"/> <c:out value="${registro.apellidos}"/></td></tr>

    <tr><th>Email</th>
        <td><c:out value="${registro.email}"/></td></tr>

    <tr><th>Fecha de nacimiento</th>
        <td><c:out value="${registro.fechaNacimiento}"/></td></tr>

    <tr><th>Género</th>
        <td>
            <c:choose>
                <c:when test="${registro.genero eq 'm'}">Hombre</c:when>
                <c:when test="${registro.genero eq 'f'}">Mujer</c:when>
                <c:when test="${registro.genero eq 'o'}">Otro</c:when>
                <c:otherwise>Prefiero no decir</c:otherwise>
            </c:choose>
        </td></tr>

    <tr><th>País</th>
        <td><c:out value="${registro.pais}"/></td></tr>

    <tr><th>Nivel</th>
        <td>
            <c:choose>
                <c:when test="${registro.nivel eq 'junior'}">Junior (0–2 años)</c:when>
                <c:when test="${registro.nivel eq 'mid'}">Mid (2–5 años)</c:when>
                <c:when test="${registro.nivel eq 'senior'}">Senior (+5 años)</c:when>
            </c:choose>
        </td></tr>

    <tr><th>Tecnologías</th>
        <td>
            <div class="tags">
                <c:forEach var="t" items="${registro.tecnologias}">
                    <span class="tag"><c:out value="${t}"/></span>
                </c:forEach>
            </div>
        </td></tr>

    <tr><th>Modalidad</th>
        <td>
            <c:choose>
                <c:when test="${registro.modalidad eq 'presencial'}">Presencial</c:when>
                <c:when test="${registro.modalidad eq 'remoto'}">Remoto</c:when>
                <c:otherwise>Híbrido</c:otherwise>
            </c:choose>
        </td></tr>

    <tr><th>Comentarios</th>
        <td>
            <c:choose>
                <c:when test="${not empty registro.comentarios}">
                    <c:out value="${registro.comentarios}"/>
                </c:when>
                <c:otherwise><em style="color:#999">—</em></c:otherwise>
            </c:choose>
        </td></tr>

    <tr><th>Disponibilidad</th>
        <td>
            <c:choose>
                <c:when test="${not empty registro.fechaDisponible}">
                    <c:out value="${registro.fechaDisponible}"/>
                </c:when>
                <c:otherwise><em style="color:#999">No indicada</em></c:otherwise>
            </c:choose>
        </td></tr>
</table>

<p style="margin-top:24px">
    <a href="${pageContext.request.contextPath}/formulario.do">← Volver al formulario</a>
    &nbsp;·&nbsp;
    <a href="${pageContext.request.contextPath}/">← Inicio</a>
</p>
</body>
</html>
