<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado — sin excepción</title>
    <style>
        body  { font-family: sans-serif; max-width: 560px; margin: 40px auto; color: #333; }
        h1    { color: #27ae60; }
        .ok   { background: #eafaf1; border-left: 4px solid #27ae60;
                padding: 12px 16px; border-radius: 4px; margin: 16px 0; }
        a     { color: #27ae60; }
    </style>
</head>
<body>
    <h1>&#10003; Operación completada</h1>
    <div class="ok">
        <p>${mensaje}</p>
        <p>No se lanzó ninguna excepción. La Action ejecutó
           <code>mapping.findForward("ok")</code> normalmente.</p>
    </div>
    <p><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
