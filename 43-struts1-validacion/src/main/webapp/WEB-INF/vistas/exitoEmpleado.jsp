<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Empleado dado de alta</title>
    <style>
        body  { font-family: sans-serif; max-width: 540px; margin: 40px auto; color: #333; }
        h1    { color: #8e44ad; }
        table { border-collapse: collapse; margin-top: 16px; width: 100%; }
        th, td { padding: 10px 14px; border: 1px solid #ddd; text-align: left; }
        th    { background: #f5eef8; }
        a     { color: #8e44ad; }
    </style>
</head>
<body>
    <h1>Empleado dado de alta correctamente</h1>
    <p>Todos los campos superaron la validación de Hibernate Validator.</p>

    <%-- empleadoForm está en request scope (scope="request" en struts-config.xml) --%>
    <table>
        <tr><th>Campo</th><th>Valor recibido</th></tr>
        <tr><td>Nombre</td>   <td>${empleadoForm.nombre}</td></tr>
        <tr><td>Correo</td>   <td>${empleadoForm.email}</td></tr>
        <tr><td>Teléfono</td> <td>${empleadoForm.telefono}</td></tr>
        <tr><td>NIF</td>      <td>${empleadoForm.nif}</td></tr>
    </table>

    <p style="margin-top:20px">
        <a href="${pageContext.request.contextPath}/mostrarEmpleado.do">← Nueva alta</a>
    </p>
    <p><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
