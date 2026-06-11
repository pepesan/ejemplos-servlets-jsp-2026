<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Contacto enviado</title>
    <style>
        body  { font-family: sans-serif; max-width: 520px; margin: 40px auto; color: #333; }
        h1    { color: #27ae60; }
        table { border-collapse: collapse; margin-top: 16px; width: 100%; }
        th, td { padding: 10px 14px; border: 1px solid #ddd; text-align: left; }
        th    { background: #f5f5f5; }
        a     { color: #27ae60; }
    </style>
</head>
<body>
    <h1>Contacto enviado correctamente</h1>
    <p>Datos validados por <code>ContactoForm.validate()</code>:</p>

    <table>
        <tr><th>Campo</th><th>Valor recibido</th></tr>
        <tr><td>Nombre</td>  <td>${contactoForm.nombre}</td></tr>
        <tr><td>Correo</td>  <td>${contactoForm.email}</td></tr>
        <tr><td>Mensaje</td> <td>${contactoForm.mensaje}</td></tr>
    </table>

    <p style="margin-top:20px">
        <a href="${pageContext.request.contextPath}/mostrarContacto.do">← Nuevo contacto</a>
    </p>
    <p><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
