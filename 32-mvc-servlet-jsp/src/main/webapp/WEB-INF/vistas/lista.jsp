<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Vista:       lista.jsp
  Controller:  AlumnoController.listar(req, resp)

  El controller hace req.setAttribute(clave, valor) y esta JSP lo lee con EL ${clave}.
  La tabla muestra cada setAttribute, el tipo Java y cómo se usa en la vista:

    setAttribute("alumnos",   List<Alumno>) → ${alumnos}    → <c:forEach items="${alumnos}" var="a">
    setAttribute("total",     int)          → ${total}      → texto directo
    setAttribute("aprobados", long)         → ${aprobados}  → texto directo
    setAttribute("suspensos", long)         → ${suspensos}  → texto directo
    setAttribute("notaMedia", double)       → ${notaMedia}  → <fmt:formatNumber value="${notaMedia}">

  Propiedades del objeto Alumno accesibles con EL dentro del forEach
  (EL llama al getter correspondiente: ${a.nombre} → a.getNombre()):

    ${a.id}  ${a.nombre}  ${a.email}  ${a.nota}  ${a.activo}  ${a.aprobado}  ${a.fechaAlta}
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de alumnos</title>
    <style>
        body  { font-family: sans-serif; max-width: 1000px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #3498db; padding-bottom: 8px; }
        .stats{ display: flex; gap: 20px; margin: 16px 0; flex-wrap: wrap; }
        .stat { background: #f0f4f8; border-radius: 6px; padding: 10px 18px; text-align: center; }
        .stat strong { display: block; font-size: 1.6em; color: #3498db; }
        .btn  { display: inline-block; padding: 8px 16px; background: #3498db;
                color: #fff; border-radius: 4px; text-decoration: none; border: none;
                cursor: pointer; font-size: 1em; }
        .btn:hover      { background: #2980b9; }
        .btn-danger     { background: #e74c3c; }
        .btn-danger:hover { background: #c0392b; }
        table { width: 100%; border-collapse: collapse; margin-top: 16px; }
        th, td{ padding: 10px 14px; border: 1px solid #ddd; text-align: left; }
        th    { background: #3498db; color: #fff; }
        tr:nth-child(even) { background: #f5f7fa; }
        .badge { display: inline-block; padding: 2px 8px; border-radius: 10px;
                 font-size: 0.8em; font-weight: bold; }
        .badge-sobresaliente { background: #d5f5e3; color: #1a5c35; }
        .badge-notable       { background: #d6eaf8; color: #1a4a7a; }
        .badge-aprobado      { background: #fef9e7; color: #7d6608; }
        .badge-suspenso      { background: #fdedec; color: #922b21; }
        .badge-activo        { background: #d5f5e3; color: #1a5c35; }
        .badge-inactivo      { background: #f0f4f8; color: #888; }
        .acciones            { display: flex; gap: 6px; align-items: center; }
        a     { color: #3498db; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .vacio{ color: #888; font-style: italic; }
    </style>
</head>
<body>

<h1>Lista de alumnos</h1>

<%-- ── Estadísticas leídas directamente con EL desde el request ─────────── --%>
<div class="stats">
    <div class="stat"><strong>${total}</strong> Total</div>
    <div class="stat"><strong>${aprobados}</strong> Aprobados</div>
    <div class="stat"><strong>${suspensos}</strong> Suspensos</div>
    <div class="stat">
        <strong><fmt:formatNumber value="${notaMedia}" pattern="0.00"/></strong>
        Nota media
    </div>
</div>

<p><a href="${pageContext.request.contextPath}/app/alumnos/nuevo" class="btn">+ Nuevo alumno</a></p>

<%-- ── Lista con c:choose para el caso vacío ────────────────────────────── --%>
<c:choose>
    <c:when test="${empty alumnos}">
        <p class="vacio">No hay alumnos registrados.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Email</th>
                <th>Nota</th>
                <th>Alta</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>

            <%-- ── Iteración con c:forEach ─────────────────────────────── --%>
            <c:forEach var="a" items="${alumnos}" varStatus="estado">
                <tr>
                    <td>${estado.count}</td>

                    <%-- c:out escapa HTML para prevenir XSS --%>
                    <td><c:out value="${a.nombre}"/></td>
                    <td><c:out value="${a.email}"/></td>

                    <td>
                        <fmt:formatNumber value="${a.nota}" pattern="0.00"/>
                        <%-- c:choose para determinar la calificación --%>
                        <c:choose>
                            <c:when test="${a.nota >= 9}">
                                <span class="badge badge-sobresaliente">Sobresaliente</span>
                            </c:when>
                            <c:when test="${a.nota >= 7}">
                                <span class="badge badge-notable">Notable</span>
                            </c:when>
                            <c:when test="${a.nota >= 5}">
                                <span class="badge badge-aprobado">Aprobado</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-suspenso">Suspenso</span>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td><fmt:formatDate value="${a.fechaAlta}" pattern="dd/MM/yyyy"/></td>

                    <%-- c:if simple para el campo booleano --%>
                    <td>
                        <c:if test="${a.activo}">
                            <span class="badge badge-activo">Activo</span>
                        </c:if>
                        <c:if test="${not a.activo}">
                            <span class="badge badge-inactivo">Inactivo</span>
                        </c:if>
                    </td>

                    <td class="acciones">
                        <a href="${pageContext.request.contextPath}/app/alumnos/ver?id=${a.id}">Ver</a>
                        <a href="${pageContext.request.contextPath}/app/alumnos/editar?id=${a.id}">Editar</a>
                        <form method="post"
                              action="${pageContext.request.contextPath}/app/alumnos/eliminar"
                              onsubmit="return confirm('¿Eliminar a ${a.nombre}?')"
                              style="display:inline">
                            <input type="hidden" name="id" value="${a.id}">
                            <button type="submit" class="btn btn-danger"
                                    style="padding:4px 10px;font-size:0.85em">Eliminar</button>
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
