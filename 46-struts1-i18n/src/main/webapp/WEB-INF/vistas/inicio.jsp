<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"    prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><bean:message key="app.titulo"/></title>
    <style>
        body  { font-family: sans-serif; max-width: 680px; margin: 40px auto; color: #333; padding: 0 16px; }
        h1    { border-bottom: 3px solid #2980b9; padding-bottom: 10px; }
        h2    { margin-top: 28px; color: #444; }
        p     { line-height: 1.65; }
        code  { background: #f4f4f4; padding: 2px 6px; border-radius: 3px; font-size: .92em; }
        pre   { background: #f8f8f8; border: 1px solid #e0e0e0; border-radius: 4px;
                padding: 14px; overflow-x: auto; font-size: .88em; }
        .lang-bar { background: #eaf4fc; border: 1px solid #aed6f1; border-radius: 6px;
                    padding: 12px 16px; margin: 16px 0; display: flex; align-items: center; gap: 12px; flex-wrap: wrap; }
        .lang-bar span { font-weight: bold; color: #2980b9; }
        .lang-bar a { display: inline-block; padding: 6px 16px; border-radius: 4px;
                      background: #2980b9; color: #fff; text-decoration: none; font-size: .9em; }
        .lang-bar a:hover { background: #2471a3; }
        .lang-bar a.activo { background: #1a5276; }
        .saludo { background: #eafaf1; border-left: 4px solid #27ae60;
                  padding: 12px 16px; border-radius: 4px; margin: 12px 0; font-size: 1.05em; }
        .form-saludo { background: #fafafa; border: 1px solid #ddd; border-radius: 6px;
                       padding: 16px; margin: 16px 0; }
        .form-saludo label { display: block; margin-bottom: 6px; font-weight: bold; }
        .form-saludo input[type=text] { width: 100%; padding: 8px; border: 1px solid #ccc;
                                        border-radius: 4px; font-size: 1em; box-sizing: border-box; }
        .form-saludo input[type=submit] { margin-top: 10px; padding: 8px 20px; background: #2980b9;
                                          color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: .95em; }
        .form-saludo input[type=submit]:hover { background: #2471a3; }
        .info { background: #fef9e7; border-left: 4px solid #f39c12;
                padding: 10px 14px; border-radius: 4px; margin: 12px 0; font-size: .9em; }
    </style>
</head>
<body>

<h1><bean:message key="app.titulo"/></h1>
<p><bean:message key="app.descripcion"/></p>

<%-- Barra de cambio de idioma --%>
<div class="lang-bar">
    <span><bean:message key="app.idioma.actual"/>:</span>
    <a href="${pageContext.request.contextPath}/idioma.do?lang=es">🇪🇸 Español</a>
    <a href="${pageContext.request.contextPath}/idioma.do?lang=en">🇬🇧 English</a>
</div>

<p><bean:message key="app.bienvenida"/></p>

<%-- Saludo personalizado (si viene de SaludoAction) --%>
<c:if test="${not empty saludo}">
    <div class="saludo">${saludo}</div>
</c:if>

<%-- Formulario de saludo --%>
<div class="form-saludo">
    <h2><bean:message key="label.saludo"/></h2>
    <html:form action="/saludo.do" method="post">
        <label for="nombre"><bean:message key="campo.nombre"/>:</label>
        <html:text property="nombre" styleId="nombre" styleClass=""/>
        <input type="submit" value="<bean:message key='boton.saludar'/>">
    </html:form>
</div>

<h2><bean:message key="app.como.funciona"/></h2>

<div class="info">
    <strong>Locale en sesión:</strong>
    <code>session["org.apache.struts.action.LOCALE"] = ${sessionScope["org.apache.struts.action.LOCALE"]}</code>
</div>

<pre>// CambiarIdiomaAction.java — guardar el locale en sesión
request.getSession().setAttribute(Globals.LOCALE_KEY, new Locale("es"));

// inicio.jsp — leer mensajes según el locale activo
&lt;bean:message key="app.titulo"/&gt;
&lt;%-- Struts lee sessionScope["org.apache.struts.action.LOCALE"]
     y carga ApplicationResources_es.properties --%&gt;

// SaludoAction.java — mensaje con parámetro {0}
MessageResources messages = getResources(request);
String saludo = messages.getMessage(locale, "saludo.resultado", nombre);
// saludo.resultado=¡Hola, {0}! Bienvenido a Struts i18n.</pre>

<h2><bean:message key="fecha.actual"/></h2>
<p>
    <fmt:formatDate value="<%= new java.util.Date() %>" type="both"
                   dateStyle="full" timeStyle="short"/>
</p>
<p style="font-size:.85em;color:#888">
    La taglib <code>&lt;fmt:formatDate&gt;</code> de JSTL también respeta el
    locale activo para formatear fechas y números.
</p>

<p style="margin-top:24px"><a href="${pageContext.request.contextPath}/">← Inicio</a></p>
</body>
</html>
