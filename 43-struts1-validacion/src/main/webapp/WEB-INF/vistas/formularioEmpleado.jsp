<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Alta de empleado — Bean Validation</title>
    <style>
        body  { font-family: sans-serif; max-width: 540px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #8e44ad; padding-bottom: 8px; }
        label { display: block; margin-top: 16px; font-weight: bold; }
        input[type=text] {
            width: 100%; padding: 8px; box-sizing: border-box;
            border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; font-size: 1em;
        }
        .errores-bloque { background: #fdecea; border-left: 4px solid #e74c3c;
                          padding: 10px 14px; border-radius: 4px; margin-bottom: 16px; }
        .errores-bloque ul { margin: 0; padding-left: 18px; }
        .errores-bloque li { color: #c0392b; margin: 2px 0; }
        .error-campo { color: #c0392b; font-size: .9em; margin-top: 3px; }
        .error-campo ul { margin: 2px 0; padding-left: 16px; }
        .error-campo li { list-style: none; padding: 0; }
        input[type=submit] { margin-top: 20px; padding: 10px 26px; background: #8e44ad;
                             color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        input[type=submit]:hover { background: #7d3c98; }
        a { color: #8e44ad; }
        .nota { background: #f5eef8; border-left: 4px solid #8e44ad; padding: 8px 14px;
                margin-bottom: 16px; font-size: .9em; border-radius: 4px; }
    </style>
</head>
<body>
    <h1>Alta de empleado</h1>

    <div class="nota">
        Validación con <strong>Bean Validation JSR-380</strong> (Hibernate Validator).<br>
        Las reglas están en las anotaciones de <code>EmpleadoForm</code>.
        <code>BeanValidationForm.validate()</code> convierte las violaciones en errores de Struts.
    </div>

    <%-- Todos los errores agrupados al inicio --%>
    <html:errors/>

    <html:form action="/empleado.do" method="post">

        <label for="nombre">Nombre</label>
        <html:text property="nombre" styleId="nombre"/>
        <div class="error-campo"><html:errors property="nombre"/></div>

        <label for="email">Correo electrónico</label>
        <html:text property="email" styleId="email"/>
        <div class="error-campo"><html:errors property="email"/></div>

        <label for="telefono">Teléfono (9–15 dígitos)</label>
        <html:text property="telefono" styleId="telefono"/>
        <div class="error-campo"><html:errors property="telefono"/></div>

        <label for="nif">NIF (ej. 12345678Z)</label>
        <html:text property="nif" styleId="nif"/>
        <div class="error-campo"><html:errors property="nif"/></div>

        <input type="submit" value="Dar de alta">

    </html:form>

    <p style="margin-top:24px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
