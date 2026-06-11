<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Recurso no encontrado</title>
    <style>
        body  { font-family: sans-serif; max-width: 640px; margin: 40px auto; color: #333; }
        h1    { color: #8e44ad; }
        .errores { background: #f5eef8; border-left: 4px solid #8e44ad;
                   padding: 12px 16px; border-radius: 4px; margin: 16px 0; }
        .errores ul { margin: 0; padding-left: 18px; }
        .errores li { color: #8e44ad; margin: 4px 0; }
        .detalle { background: #f8f8f8; border: 1px solid #ddd; border-radius: 4px;
                   padding: 12px 16px; margin-top: 16px; }
        .detalle h3 { margin: 0 0 8px; font-size: .95em; color: #555; }
        pre   { margin: 0; font-size: .82em; overflow-x: auto; white-space: pre-wrap;
                max-height: 200px; }
        .tag  { display: inline-block; background: #8e44ad; color: #fff; padding: 2px 8px;
                border-radius: 3px; font-size: .8em; margin-bottom: 8px; }
        a     { color: #8e44ad; }
    </style>
</head>
<body>
    <span class="tag">global-exceptions › RecursoNoEncontradoException</span>
    <h1>Recurso no encontrado</h1>

    <%-- Mensaje de error desde ApplicationResources.properties --%>
    <html:errors/>

    <div class="detalle">
        <h3>Detalles técnicos</h3>
        <c:set var="ex" value="${requestScope['org.apache.struts.action.EXCEPTION']}"/>
        <c:if test="${not empty ex}">
            <p><strong>Tipo:</strong> <code>${ex['class'].name}</code></p>
            <p><strong>Mensaje:</strong> <code>${ex.message}</code></p>
        </c:if>
        <p style="font-size:.85em;color:#888">
            Handler: <code>LoggingExceptionHandler</code> (personalizado — loggea y delega)
        </p>
        <c:if test="${not empty requestScope.stacktrace}">
            <p style="font-size:.85em;color:#888;margin-bottom:4px">Stacktrace (del handler):</p>
            <pre>${requestScope.stacktrace}</pre>
        </c:if>
    </div>

    <p style="margin-top:20px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
