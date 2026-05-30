<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="WEB-INF/_menu.jspf" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Directivas JSP</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.resultado{background:#1e1e2e;border-left:3px solid #a6e3a1;padding:.6em 1em;
           margin-top:-.2em;font-size:.9em}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;
      color:#a6adc8;margin:.5em 0}
</style>
</head>
<body>

<h1>Directivas JSP</h1>
<p>Las directivas son instrucciones para el compilador JSP. Se escriben con
<code>&#37;@</code> y se procesan en tiempo de <strong>compilación</strong>,
no en tiempo de ejecución.</p>


<%-- ═══════════════════════════════════════════════════════ @page ═══ --%>
<h2>1. &#37;@ page</h2>
<p>Configura la página: idioma, codificación, imports Java, gestión de errores…</p>

<pre>
&lt;%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="java.util.Date" %&gt;
</pre>

<div class="resultado">
  <strong>Atributos más usados:</strong><br>
  <code>language</code> → lenguaje de scripting (siempre "java")<br>
  <code>contentType</code> → MIME type y charset de la respuesta<br>
  <code>pageEncoding</code> → codificación del fichero .jsp en disco<br>
  <code>import</code> → clases Java disponibles en la página (como import en .java)<br>
  <code>session</code> → "true" (defecto) / "false" para deshabilitar sesión<br>
  <code>errorPage</code> → URL de la página de error personalizada<br>
  <code>isErrorPage</code> → "true" activa el objeto implícito <code>exception</code>
</div>

<div class="nota">
  Esta misma página usa: <code>import="java.util.Date"</code> →
  resultado: <strong><%= new Date() %></strong>
</div>


<%-- ══════════════════════════════════════════════════════ @taglib ══ --%>
<h2>2. &#37;@ taglib</h2>
<p>Declara una biblioteca de etiquetas (tag library). Sin esta directiva,
las etiquetas <code>&lt;c:...&gt;</code> o <code>&lt;fmt:...&gt;</code> provocan un error.</p>

<pre>
&lt;%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %&gt;
&lt;%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %&gt;
&lt;%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %&gt;
</pre>

<div class="resultado">
  Esta página declara JSTL core con prefijo <code>c</code>.
  Ejemplo inmediato: <c:out value="hola desde c:out" />
</div>


<%-- ═══════════════════════════════════════════════════ @include ══ --%>
<h2>3. &#37;@ include</h2>
<p>Incluye el contenido de otro fichero <strong>en tiempo de compilación</strong>.
El resultado es como si el texto del fichero estuviera pegado aquí.
El menú de navegación que aparece arriba se incluyó así:</p>

<pre>
&lt;%@ include file="WEB-INF/_menu.jspf" %&gt;
</pre>

<div class="nota">
  <strong>&#37;@ include vs &lt;jsp:include&gt;</strong><br>
  <code>&#37;@ include</code> → inclusión <em>estática</em>, en compilación.
  El fichero incluido comparte variables y directivas con la página padre.<br>
  <code>&lt;jsp:include&gt;</code> → inclusión <em>dinámica</em>, en ejecución.
  Se puede usar con atributos calculados en tiempo real.
</div>

<p><a href="/">← Inicio</a> &nbsp;|&nbsp; <a href="expresiones.jsp">Siguiente: Expresiones →</a></p>
</body>
</html>
