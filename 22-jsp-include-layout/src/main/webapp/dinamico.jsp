<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
    DEMOSTRACIÓN: <jsp:include page="..." /> — inclusión DINÁMICA

    En tiempo de ejecución, el contenedor invoca la página incluida como
    una sub-petición separada. Cada página tiene su propio _jspService().
    NO comparten variables locales: hay que pasar datos por request o params.
--%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><title>Include dinámico</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.4em}p{color:#a6adc8;line-height:1.6}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.grid{display:grid;grid-template-columns:1fr 1fr;gap:1em;margin:.6em 0}
.col{background:#181825;padding:.8em 1em;border-radius:4px}
.label{font-size:.78em;color:#585b70;margin-bottom:.3em}
.nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}
.nav a{color:#a6adc8;text-decoration:none;margin-right:1em}.nav a:hover{color:#cdd6f4}
.aviso{background:#313244;border-left:3px solid #f9e2af;padding:.6em 1em;
       border-radius:0 4px 4px 0;font-size:.88em;color:#f9e2af;margin:.4em 0}
</style>
</head>
<body>

<div class="nav">
  <a href="/">Inicio</a>
  <a href="estatico.jsp">Estático</a>
  <a href="dinamico.jsp" style="color:#89b4fa">Dinámico</a>
  <a href="con-params.jsp">Con parámetros</a>
</div>

<h1>Include dinámico — <code>&lt;jsp:include&gt;</code></h1>

<h2>Sintaxis básica</h2>
<pre>
&lt;jsp:include page="WEB-INF/fragmentos/alerta.jsp" /&gt;
</pre>

<h2>Con <code>&lt;jsp:param&gt;</code> para pasar datos</h2>
<pre>
&lt;jsp:include page="WEB-INF/fragmentos/alerta.jsp"&gt;
    &lt;jsp:param name="tipo"    value="ok" /&gt;
    &lt;jsp:param name="mensaje" value="Operación realizada" /&gt;
&lt;/jsp:include&gt;
</pre>

<h2>Resultado — misma plantilla, distintos parámetros</h2>

<jsp:include page="WEB-INF/fragmentos/alerta.jsp">
    <jsp:param name="tipo"    value="info"  />
    <jsp:param name="mensaje" value="Tipo info: fondo azul" />
</jsp:include>

<jsp:include page="WEB-INF/fragmentos/alerta.jsp">
    <jsp:param name="tipo"    value="ok"    />
    <jsp:param name="mensaje" value="Tipo ok: fondo verde" />
</jsp:include>

<jsp:include page="WEB-INF/fragmentos/alerta.jsp">
    <jsp:param name="tipo"    value="aviso" />
    <jsp:param name="mensaje" value="Tipo aviso: fondo amarillo" />
</jsp:include>

<jsp:include page="WEB-INF/fragmentos/alerta.jsp">
    <jsp:param name="tipo"    value="error" />
    <jsp:param name="mensaje" value="Tipo error: fondo rojo" />
</jsp:include>

<h2>Comparativa estático vs dinámico</h2>
<div class="grid">
<div class="col">
<div class="label">&#37;@ include — estático</div>
<pre style="margin:0;border:none;background:transparent;padding:0">
Cuándo: compilación
Alcance: comparte variables
Compilación: un servlet
Flexibilidad: ruta fija
Uso: cabecera/pie fijos</pre>
</div>
<div class="col">
<div class="label">&lt;jsp:include&gt; — dinámico</div>
<pre style="margin:0;border:none;background:transparent;padding:0">
Cuándo: ejecución
Alcance: aislado (params)
Compilación: servlets separados
Flexibilidad: ruta dinámica
Uso: componentes reutilizables</pre>
</div>
</div>

<div class="aviso">
  &#9888; Con <code>&lt;jsp:include&gt;</code>, si el fragmento intenta leer una
  variable local del padre (<code>paginaTitulo</code>), obtendrá error de compilación
  porque son clases Java independientes.
</div>

<p><a href="estatico.jsp">← Estático</a> &nbsp;|&nbsp;
   <a href="con-params.jsp">Siguiente: Con parámetros →</a></p>
</body>
</html>
