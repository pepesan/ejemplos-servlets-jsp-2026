<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Fichero subido correctamente</title>
    <style>
        body  { font-family: sans-serif; max-width: 560px; margin: 40px auto; color: #333; }
        h1    { color: #27ae60; }
        .ok   { background: #eafaf1; border-left: 4px solid #27ae60;
                padding: 12px 16px; border-radius: 4px; margin: 16px 0; }
        table { border-collapse: collapse; width: 100%; margin-top: 16px; }
        th, td { padding: 10px 14px; border: 1px solid #ddd; text-align: left; }
        th    { background: #f5f5f5; }
        .acciones { margin-top: 16px; display: flex; gap: 12px; flex-wrap: wrap; }
        .btn { display: inline-block; padding: 8px 18px; border-radius: 4px; text-decoration: none;
               font-size: .95em; }
        .btn-dl  { background: #27ae60; color: #fff; }
        .btn-dl:hover  { background: #219a52; }
        .btn-ver { background: #8e44ad; color: #fff; }
        .btn-ver:hover { background: #7d3c98; }
        a     { color: #8e44ad; }
    </style>
</head>
<body>
    <h1>&#10003; Fichero subido correctamente</h1>

    <div class="ok">Guardado en el directorio temporal del servidor.</div>

    <table>
        <tr><th>Propiedad</th><th>Valor</th></tr>
        <tr><td>Nombre original</td><td>${nombreOriginal}</td></tr>
        <tr><td>Nombre en servidor</td><td><code>${nombreSeguro}</code></td></tr>
        <tr><td>MIME type</td><td><code>${mime}</code></td></tr>
        <tr><td>Tamaño</td><td>${tamanio}</td></tr>
        <tr><td>Ruta temporal</td><td><code>${ruta}</code></td></tr>
        <c:if test="${not empty descripcion}">
            <tr><td>Descripción</td><td>${descripcion}</td></tr>
        </c:if>
    </table>

    <div class="acciones">
        <a class="btn btn-dl"
           href="${pageContext.request.contextPath}/descargar.do?nombre=${nombreUrl}">
            &#8595; Descargar fichero
        </a>
        <a class="btn btn-ver"
           href="${pageContext.request.contextPath}/descargar.do?nombre=${nombreUrl}&amp;ver=true">
            &#128065; Ver en el navegador
        </a>
    </div>

    <p style="margin-top:20px">
        <a href="${pageContext.request.contextPath}/formulario.do">← Subir otro fichero</a>
    </p>
    <p><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
