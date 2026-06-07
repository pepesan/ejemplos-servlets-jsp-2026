<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <c:choose>
        <c:when test="${not empty contactoForm.id and contactoForm.id != '0'}">
            <title>Editar contacto — Struts 1.x CRUD</title>
        </c:when>
        <c:otherwise>
            <title>Nuevo contacto — Struts 1.x CRUD</title>
        </c:otherwise>
    </c:choose>
    <style>
        body   { font-family: sans-serif; max-width: 520px; margin: 40px auto; color: #333; }
        h1     { border-bottom: 2px solid #e67e22; padding-bottom: 8px; }
        label  { display: block; margin-top: 16px; font-weight: bold; }
        input[type=text] { width: 100%; padding: 8px; box-sizing: border-box;
                           border: 1px solid #ccc; border-radius: 4px;
                           margin-top: 4px; font-size: 1em; }
        .errors { background: #fdecea; border-left: 4px solid #e74c3c;
                  padding: 10px 14px; border-radius: 4px; margin-bottom: 16px; }
        .errors li { color: #c0392b; margin: 0; }
        .acciones  { margin-top: 24px; display: flex; gap: 12px; }
        input[type=submit] { padding: 10px 24px; background: #e67e22;
                             color: #fff; border: none; border-radius: 4px;
                             cursor: pointer; font-size: 1em; }
        input[type=submit]:hover { background: #ca6f1e; }
        a.cancelar { padding: 10px 20px; color: #666; border: 1px solid #ccc;
                     border-radius: 4px; text-decoration: none; font-size: 1em; }
        a.cancelar:hover { background: #f5f5f5; }
    </style>
</head>
<body>
    <c:choose>
        <c:when test="${not empty contactoForm.id and contactoForm.id != '0'}">
            <h1>Editar contacto #<c:out value="${contactoForm.id}"/></h1>
        </c:when>
        <c:otherwise>
            <h1>Nuevo contacto</h1>
        </c:otherwise>
    </c:choose>

    <%-- html:errors muestra los mensajes guardados con Action.saveErrors() --%>
    <html:errors/>

    <%--
      html:form apunta siempre a /guardar.do.
      html:hidden preserva el id en edición (vacío en creación).
      html:text vincula cada campo con la propiedad del ContactoForm.
    --%>
    <html:form action="/guardar.do" method="post">

        <html:hidden property="id"/>

        <label for="nombre">Nombre <span style="color:#e74c3c">*</span></label>
        <html:text property="nombre" styleId="nombre"/>

        <label for="email">Email <span style="color:#e74c3c">*</span></label>
        <html:text property="email" styleId="email"/>

        <label for="telefono">Teléfono</label>
        <html:text property="telefono" styleId="telefono"/>

        <div class="acciones">
            <input type="submit" value="Guardar"/>
            <a class="cancelar"
               href="${pageContext.request.contextPath}/listar.do">Cancelar</a>
        </div>

    </html:form>
</body>
</html>
