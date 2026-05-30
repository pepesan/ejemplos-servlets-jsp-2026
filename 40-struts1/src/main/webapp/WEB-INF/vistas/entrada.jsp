<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de saludo — Struts 1.x</title>
    <style>
        body   { font-family: sans-serif; max-width: 480px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #e67e22; padding-bottom: 8px; }
        label  { display: block; margin-top: 14px; font-weight: bold; }
        input[type=text] { width: 100%; padding: 8px; box-sizing: border-box;
                           border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; font-size: 1em; }
        .errors { background: #fdecea; border-left: 4px solid #e74c3c;
                  padding: 10px 14px; border-radius: 4px; margin-bottom: 12px; }
        .errors li { color: #c0392b; margin: 0; }
        input[type=submit] { margin-top: 18px; padding: 10px 24px; background: #e67e22;
                             color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        input[type=submit]:hover { background: #ca6f1e; }
        a { color: #e67e22; }
    </style>
</head>
<body>
    <h1>Formulario de saludo</h1>

    <%-- <html:errors/> muestra los errores guardados con Action.saveErrors() --%>
    <html:errors/>

    <%--
      <html:form>  genera <form action="/saludo.do" method="post">
      <html:text>  genera <input type="text" name="nombre" value="...">
                   y vincula automáticamente el valor con SaludoForm.nombre
    --%>
    <html:form action="/saludo.do" method="post">

        <label for="nombre">Tu nombre</label>
        <html:text property="nombre" styleId="nombre" styleClass="campo"/>

        <input type="submit" value="Saludar">

    </html:form>

    <p style="margin-top: 20px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
