<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro — DynaValidatorForm</title>
    <style>
        body  { font-family: sans-serif; max-width: 520px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #8e44ad; padding-bottom: 8px; }
        label { display: block; margin-top: 16px; font-weight: bold; }
        input[type=text] { width: 100%; padding: 8px; box-sizing: border-box;
                           border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; font-size: 1em; }
        .errores { background: #fdecea; border-left: 4px solid #e74c3c;
                   padding: 10px 14px; border-radius: 4px; margin-bottom: 16px; }
        .errores ul { margin: 0; padding-left: 18px; }
        .errores li { color: #c0392b; margin: 2px 0; }
        input[type=submit] { margin-top: 20px; padding: 10px 26px; background: #8e44ad;
                             color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        input[type=submit]:hover { background: #7d3c98; }
        a { color: #8e44ad; }
        .nota { background: #f5eef8; border-left: 4px solid #8e44ad; padding: 8px 14px;
                margin-bottom: 16px; font-size: .9em; border-radius: 4px; }
    </style>
</head>
<body>
    <h1>Formulario de registro</h1>

    <div class="nota">
        No existe ninguna clase Java para este formulario.
        Los campos están declarados en <code>struts-config.xml</code> como
        <code>&lt;form-property&gt;</code>.
    </div>

    <html:errors/>

    <%--
      <html:text property="nombre"> funciona igual que con un ActionForm ordinario.
      Struts accede al valor vía DynaBean.get("nombre") en lugar de form.getNombre().
    --%>
    <html:form action="/registro.do" method="post">

        <label for="nombre">Nombre</label>
        <html:text property="nombre" styleId="nombre"/>

        <label for="email">Correo electrónico</label>
        <html:text property="email" styleId="email"/>

        <label for="edad">Edad</label>
        <html:text property="edad" styleId="edad"/>

        <input type="submit" value="Registrar">

    </html:form>

    <p style="margin-top:24px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
