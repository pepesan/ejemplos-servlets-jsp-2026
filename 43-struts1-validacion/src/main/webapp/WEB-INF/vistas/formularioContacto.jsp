<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Contacto — Validación programática</title>
    <style>
        body  { font-family: sans-serif; max-width: 520px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #27ae60; padding-bottom: 8px; }
        label { display: block; margin-top: 16px; font-weight: bold; }
        input[type=text], textarea {
            width: 100%; padding: 8px; box-sizing: border-box;
            border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; font-size: 1em;
        }
        textarea { height: 100px; resize: vertical; }
        .errores { background: #fdecea; border-left: 4px solid #e74c3c;
                   padding: 10px 14px; border-radius: 4px; margin-bottom: 16px; }
        .errores ul { margin: 0; padding-left: 18px; }
        .errores li { color: #c0392b; margin: 2px 0; }
        input[type=submit] { margin-top: 20px; padding: 10px 26px; background: #27ae60;
                             color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        input[type=submit]:hover { background: #1e8449; }
        a { color: #27ae60; }
        .nota { background: #eafaf1; border-left: 4px solid #27ae60; padding: 8px 14px;
                margin-bottom: 16px; font-size: .9em; border-radius: 4px; }
    </style>
</head>
<body>
    <h1>Formulario de contacto</h1>

    <div class="nota">
        Validación programática: las reglas están en
        <code>ContactoForm.validate()</code>, no en XML.
    </div>

    <%-- Errores generados por ContactoForm.validate() --%>
    <html:errors/>

    <html:form action="/contacto.do" method="post">

        <label for="nombre">Nombre</label>
        <html:text property="nombre" styleId="nombre"/>

        <label for="email">Correo electrónico</label>
        <html:text property="email" styleId="email"/>

        <label for="mensaje">Mensaje (mínimo 10 caracteres)</label>
        <html:textarea property="mensaje" styleId="mensaje"/>

        <input type="submit" value="Enviar">

    </html:form>

    <p style="margin-top:24px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
