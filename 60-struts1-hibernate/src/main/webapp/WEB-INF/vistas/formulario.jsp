<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo empleado — Struts + Hibernate</title>
    <style>
        body   { font-family: sans-serif; max-width: 500px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #2980b9; padding-bottom: 8px; }
        label  { display: block; margin-top: 14px; font-weight: bold; }
        input[type=text] { width: 100%; padding: 8px; box-sizing: border-box;
                           border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; font-size: 1em; }
        input[type=text]:focus { border-color: #2980b9; outline: none; }
        .errors { background: #fdecea; border-left: 4px solid #e74c3c;
                  padding: 10px 14px; border-radius: 4px; margin-bottom: 14px; }
        .errors li { color: #c0392b; margin: 0; }
        input[type=submit] { margin-top: 20px; padding: 10px 28px; background: #2980b9;
                             color: #fff; border: none; border-radius: 4px;
                             cursor: pointer; font-size: 1em; }
        input[type=submit]:hover { background: #2471a3; }
        a { color: #2980b9; }
    </style>
</head>
<body>
    <h1>Nuevo empleado</h1>

    <%-- html:errors muestra los ActionErrors guardados con saveErrors() --%>
    <html:errors/>

    <%--
      html:form genera <form action="/crear.do" method="post">.
      html:text vincula cada campo con la propiedad correspondiente de EmpleadoForm.
    --%>
    <html:form action="/crear.do" method="post">

        <label for="nombre">Nombre</label>
        <html:text property="nombre" styleId="nombre"/>

        <label for="departamento">Departamento</label>
        <html:text property="departamento" styleId="departamento"/>

        <label for="salario">Salario (€)</label>
        <html:text property="salario" styleId="salario"/>

        <input type="submit" value="Guardar">

    </html:form>

    <p style="margin-top: 20px">
        <a href="${pageContext.request.contextPath}/listar.do">← Volver a la lista</a>
    </p>
</body>
</html>
