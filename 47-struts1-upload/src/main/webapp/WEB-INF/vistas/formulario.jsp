<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Subir fichero — Struts</title>
    <style>
        body  { font-family: sans-serif; max-width: 560px; margin: 40px auto; color: #333; }
        h1    { border-bottom: 2px solid #8e44ad; padding-bottom: 8px; }
        label { display: block; margin-top: 16px; font-weight: bold; }
        .errores { background: #fdecea; border-left: 4px solid #e74c3c;
                   padding: 10px 14px; border-radius: 4px; margin-bottom: 16px; }
        .errores ul { margin: 0; padding-left: 18px; }
        .errores li { color: #c0392b; margin: 2px 0; }
        input[type=submit] { margin-top: 20px; padding: 10px 26px; background: #8e44ad;
                             color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; }
        input[type=submit]:hover { background: #7d3c98; }
        input[type=text] { width: 100%; padding: 8px; box-sizing: border-box;
                           border: 1px solid #ccc; border-radius: 4px; margin-top: 4px; }
        .nota { background: #f5eef8; border-left: 4px solid #8e44ad; padding: 8px 14px;
                margin-bottom: 16px; font-size: .9em; border-radius: 4px; }
        a { color: #8e44ad; }
    </style>
</head>
<body>
    <h1>Subir fichero con Struts</h1>

    <div class="nota">
        Tipos permitidos: JPEG · PNG · GIF · WEBP · PDF · TXT &mdash; máximo 2 MB
    </div>

    <html:errors/>

    <%--
      enctype="multipart/form-data" es obligatorio.
      Struts usa CommonsMultipartRequestHandler para parsear la petición
      y poblar el campo FormFile del ActionForm.
    --%>
    <html:form action="/subir.do" method="post" enctype="multipart/form-data">

        <label for="archivo">Fichero</label>
        <html:file property="archivo" styleId="archivo"
                   accept="image/*,.pdf,.txt"/>

        <label for="descripcion">Descripción <span style="font-weight:normal;color:#888">(opcional)</span></label>
        <html:text property="descripcion" styleId="descripcion"/>

        <input type="submit" value="Subir →">

    </html:form>

    <p style="margin-top:24px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
