<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro — Struts Validator</title>
    <style>
        body  { font-family: sans-serif; max-width: 520px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #2980b9; padding-bottom: 8px; }
        label { display: block; margin-top: 16px; font-weight: bold; }
        input[type=text] { width: 100%; padding: 8px; box-sizing: border-box;
                           border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; font-size: 1em; }
        .errores { background: #fdecea; border-left: 4px solid #e74c3c;
                   padding: 10px 14px; border-radius: 4px; margin-bottom: 16px; }
        .errores ul { margin: 0; padding-left: 18px; }
        .errores li { color: #c0392b; margin: 2px 0; }
        input[type=submit] { margin-top: 20px; padding: 10px 26px; background: #2980b9;
                             color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        input[type=submit]:hover { background: #1f6fa0; }
        a { color: #2980b9; }
    </style>
</head>
<body>
    <h1>Formulario de registro</h1>

    <%--
      <html:errors/> muestra los errores que Struts Validator depositó en ActionMessages.
      El formato HTML (div.errores > ul > li) lo define errors.header/prefix/suffix/footer
      en ApplicationResources.properties.
      Si no hay errores, no renderiza nada.
    --%>
    <html:errors/>

    <%--
      La acción apunta a /registro.do (validate="true").
      Al enviar el POST, Struts llama a RegistroForm.validate() antes de RegistroAction.execute().
      Si hay errores, reenvía a input="/WEB-INF/vistas/formulario.jsp" (esta misma vista)
      conservando los valores escritos en el form bean.
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
