<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Saludo — Struts 1.x</title>
    <style>
        body { font-family: sans-serif; max-width: 480px; margin: 40px auto; color: #333; }
        .saludo { font-size: 1.6em; background: #fef9f0; border-left: 4px solid #e67e22;
                  padding: 20px 24px; border-radius: 4px; margin: 24px 0; }
        a { color: #e67e22; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Resultado</h1>

    <div class="saludo">${saludo}</div>

    <p><a href="${pageContext.request.contextPath}/saludo.do">← Volver al formulario</a></p>
    <p><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
