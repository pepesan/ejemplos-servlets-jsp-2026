<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
    DEMOSTRACIÓN: <%@ include file="..." %> — inclusión ESTÁTICA

    El preprocesador copia el contenido del fragmento en esta JSP
    antes de compilarla. El resultado es un único servlet Java.
    Consecuencia: el fragmento puede leer variables locales del padre.
--%>

<%-- 'paginaTitulo' será leída por _pie.jspf gracias a la inclusión estática --%>
<%! String paginaTitulo = "estatico.jsp"; %>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><title>Include estático</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.4em}p{color:#a6adc8;line-height:1.6}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.diff{background:#313244;padding:.7em 1em;border-radius:4px;font-size:.88em;color:#a6adc8;margin:.5em 0}
.nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}
.nav a{color:#a6adc8;text-decoration:none;margin-right:1em}.nav a:hover{color:#cdd6f4}
</style>
</head>
<body>

<div class="nav">
  <a href="/">Inicio</a>
  <a href="estatico.jsp" style="color:#89b4fa">Estático</a>
  <a href="dinamico.jsp">Dinámico</a>
  <a href="con-params.jsp">Con parámetros</a>
</div>

<h1>Include estático — <code>&#37;@ include</code></h1>

<h2>Directiva usada en esta página</h2>
<pre>
&lt;%! String paginaTitulo = "estatico.jsp"; %&gt;

&lt;%@ include file="WEB-INF/_pie.jspf" %&gt;
</pre>

<h2>Qué ocurre en tiempo de compilación</h2>
<p>El compilador JSP copia el texto de <code>_pie.jspf</code> aquí, justo donde
aparece la directiva, antes de generar el servlet Java.
El resultado es <strong>un único</strong> <code>_jspService()</code>.</p>

<pre>
// Servlet generado (simplificado):
void _jspService(...) {
    String paginaTitulo = "estatico.jsp";  ← declarada en esta página
    // ... HTML de estatico.jsp ...
    // ... HTML de _pie.jspf ...           ← copiado aquí
    // _pie.jspf puede usar paginaTitulo ↑
}
</pre>

<div class="diff">
  <strong>Por qué puede el fragmento leer <code>paginaTitulo</code>:</strong>
  ambos ficheros comparten la misma compilación → las mismas variables locales.
  Con <code>&lt;jsp:include&gt;</code> esto no sería posible.
</div>

<h2>Pie incluido estáticamente (resultado real)</h2>
<p>El fragmento <code>WEB-INF/_pie.jspf</code> se incluye a continuación.
Observa que imprime el nombre de la variable <code>paginaTitulo</code>
declarada <em>arriba</em> en esta misma página:</p>

<%@ include file="WEB-INF/_pie.jspf" %>

<p style="margin-top:1.5em">
  <a href="dinamico.jsp">Siguiente: Include dinámico →</a>
</p>
</body>
</html>
