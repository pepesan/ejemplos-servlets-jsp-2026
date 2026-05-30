<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.cursosdedesarrollo.LayoutHelper" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><title>Include con parámetros</title>
<style>
body{font-family:monospace;max-width:900px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.4em}p{color:#a6adc8;line-height:1.6}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}
.nav a{color:#a6adc8;text-decoration:none;margin-right:1em}.nav a:hover{color:#cdd6f4}
.migas{font-size:.82em;color:#585b70;margin-bottom:1em}
</style>
</head>
<body>

<div class="nav">
  <a href="/">Inicio</a>
  <a href="estatico.jsp">Estático</a>
  <a href="dinamico.jsp">Dinámico</a>
  <a href="con-params.jsp" style="color:#89b4fa">Con parámetros</a>
</div>

<%-- Migas de pan usando LayoutHelper --%>
<p class="migas"><%= LayoutHelper.migas("Inicio", "Módulo 22", "Con parámetros") %></p>

<h1>Layout con componentes parametrizados</h1>
<p>Misma plantilla (<code>tarjeta.jsp</code>), distintos parámetros.
El componente lee <code>${param.titulo}</code>, <code>${param.texto}</code>
y <code>${param.accentColor}</code> para personalizarse.</p>

<h2>Código</h2>
<pre>
&lt;jsp:include page="WEB-INF/fragmentos/tarjeta.jsp"&gt;
    &lt;jsp:param name="titulo"      value="Java" /&gt;
    &lt;jsp:param name="texto"       value="Lenguaje principal del backend JEE" /&gt;
    &lt;jsp:param name="accentColor" value="#89b4fa" /&gt;
&lt;/jsp:include&gt;

&lt;jsp:include page="WEB-INF/fragmentos/tarjeta.jsp"&gt;
    &lt;jsp:param name="titulo"      value="Servlet" /&gt;
    &lt;jsp:param name="texto"       value="Controlador HTTP del lado servidor" /&gt;
    &lt;jsp:param name="accentColor" value="#cba6f7" /&gt;
&lt;/jsp:include&gt;
</pre>

<h2>Resultado</h2>

<jsp:include page="WEB-INF/fragmentos/tarjeta.jsp">
    <jsp:param name="titulo"      value="Java" />
    <jsp:param name="texto"       value="Lenguaje principal del backend JEE. Tipado estático, POO, ecosistema maduro." />
    <jsp:param name="accentColor" value="#89b4fa" />
</jsp:include>

<jsp:include page="WEB-INF/fragmentos/tarjeta.jsp">
    <jsp:param name="titulo"      value="Servlet" />
    <jsp:param name="texto"       value="Controlador HTTP del lado servidor. Recibe HttpServletRequest y escribe la respuesta." />
    <jsp:param name="accentColor" value="#cba6f7" />
</jsp:include>

<jsp:include page="WEB-INF/fragmentos/tarjeta.jsp">
    <jsp:param name="titulo"      value="JSP" />
    <jsp:param name="texto"       value="Java Server Pages: HTML con scriptlets, EL y JSTL. Se compila a servlet en el primer acceso." />
    <jsp:param name="accentColor" value="#a6e3a1" />
</jsp:include>

<jsp:include page="WEB-INF/fragmentos/tarjeta.jsp">
    <jsp:param name="titulo"      value="JSTL" />
    <jsp:param name="texto"       value="Biblioteca estándar de etiquetas JSP: c:forEach, c:if, fmt:formatDate, fn:length…" />
    <jsp:param name="accentColor" value="#fab387" />
</jsp:include>

<h2>Ruta dinámica: <code>page</code> puede ser una expresión</h2>
<pre>
&lt;%
    String fragmento = condicion ? "tarjeta.jsp" : "alerta.jsp";
%&gt;
&lt;jsp:include page="WEB-INF/fragmentos/&lt;%= fragmento %&gt;" /&gt;
</pre>
<p>Esto no es posible con <code>&#37;@ include</code> porque la ruta debe ser
un literal fijo en tiempo de compilación.</p>

<h2>LayoutHelper — migas de pan</h2>
<pre>
&lt;%@ page import="com.cursosdedesarrollo.LayoutHelper" %&gt;
&lt;%= LayoutHelper.migas("Inicio", "Módulo 22", "Con parámetros") %&gt;
</pre>
<p>Resultado: <code><%= LayoutHelper.migas("Inicio", "Módulo 22", "Con parámetros") %></code></p>

<p><a href="dinamico.jsp">← Dinámico</a> &nbsp;|&nbsp; <a href="/">Inicio</a></p>
</body>
</html>
