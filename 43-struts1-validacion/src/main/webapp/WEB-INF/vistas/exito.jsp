<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro correcto</title>
    <style>
        body  { font-family: sans-serif; max-width: 520px; margin: 40px auto; color: #333; }
        h1    { color: #27ae60; }
        table { border-collapse: collapse; margin-top: 16px; width: 100%; }
        th, td { padding: 10px 14px; border: 1px solid #ddd; text-align: left; }
        th    { background: #f5f5f5; }
        a     { color: #2980b9; }
    </style>
</head>
<body>
    <h1>Registro correcto</h1>
    <p>Todos los campos superaron la validación de Struts Validator.</p>

    <%-- registroForm está en request scope (scope="request" en struts-config.xml) --%>
    <table>
        <tr><th>Campo</th><th>Valor recibido</th></tr>
        <tr><td>Nombre</td><td>${registroForm.nombre}</td></tr>
        <tr><td>Correo</td><td>${registroForm.email}</td></tr>
        <tr><td>Edad</td>  <td>${registroForm.edad}</td></tr>
    </table>

    <p style="margin-top:20px">
        <a href="${pageContext.request.contextPath}/formulario.do">← Nuevo registro</a>
    </p>
    <p><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
