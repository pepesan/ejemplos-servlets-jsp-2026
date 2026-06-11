<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error de cálculo</title>
    <style>
        body  { font-family: sans-serif; max-width: 640px; margin: 40px auto; color: #333; }
        h1    { color: #e67e22; }
        .errores { background: #fef5e7; border-left: 4px solid #e67e22;
                   padding: 12px 16px; border-radius: 4px; margin: 16px 0; }
        .errores ul { margin: 0; padding-left: 18px; }
        .errores li { color: #e67e22; margin: 4px 0; }
        .detalle { background: #f8f8f8; border: 1px solid #ddd; border-radius: 4px;
                   padding: 12px 16px; margin-top: 16px; }
        .detalle h3 { margin: 0 0 8px; font-size: .95em; color: #555; }
        .tag  { display: inline-block; background: #e67e22; color: #fff; padding: 2px 8px;
                border-radius: 3px; font-size: .8em; margin-bottom: 8px; }
        a     { color: #e67e22; }
    </style>
</head>
<body>
    <span class="tag">local (action /producto) › ArithmeticException</span>
    <h1>Error de cálculo</h1>

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
            Handler: <code>ExceptionHandler</code> por defecto —
            declarado como excepción <strong>local</strong> en la action
            <code>/producto</code>, no como global.
        </p>
        <p style="font-size:.85em;color:#888">
            Aunque <code>ArithmeticException extends RuntimeException</code>,
            el handler local tiene prioridad sobre el global de
            <code>RuntimeException</code>.
        </p>
    </div>

    <p style="margin-top:20px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
