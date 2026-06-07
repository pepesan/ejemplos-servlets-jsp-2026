<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.Date" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Expresiones JSP</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.resultado{background:#1e1e2e;border-left:3px solid #a6e3a1;padding:.6em 1em;
           margin-top:-.2em;font-size:.9em}
.aviso{background:#313244;border-left:3px solid #f38ba8;padding:.6em 1em;
       border-radius:0 4px 4px 0;font-size:.88em;color:#f38ba8}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;color:#a6adc8}
</style>
</head>
<body>

<%@ include file="WEB-INF/_menu.jspf" %>

<h1>Scripting JSP: declaraciones, scriptlets y expresiones</h1>
<p>Son los tres elementos de scripting de JSP. En proyectos modernos
se evitan a favor de EL y JSTL, pero es fundamental conocerlos.</p>


<%-- ═══════════════════════════════════════════ Declaraciones ═════ --%>
<h2>1. Declaraciones <code>&lt;&#37;! ... &#37;&gt;</code></h2>
<p>Declaran miembros de la clase servlet generada: campos de instancia y métodos.
Se compilan <strong>fuera</strong> del método <code>_jspService</code>.</p>

<%!
    // Este campo es compartido por TODAS las peticiones (como campo de instancia)
    private int visitas = 0;

    private String saludar(String nombre) {
        return "Hola, " + nombre + "!";
    }
%>

<pre>
&lt;%!
    private int visitas = 0;          // campo de instancia

    private String saludar(String n) {
        return "Hola, " + n + "!";
    }
%&gt;
</pre>

<div class="aviso">
  &#9888; Los campos de instancia son compartidos entre peticiones concurrentes.
  Usar solo valores inmutables o con sincronización.
</div>


<%-- ═══════════════════════════════════════════════ Scriptlets ════ --%>
<h2>2. Scriptlets <code>&lt;&#37; ... &#37;&gt;</code></h2>
<p>Código Java que se ejecuta en cada petición, dentro de <code>_jspService</code>.
Las variables declaradas aquí son <strong>locales</strong> a cada petición.</p>

<%
    visitas++;                          // incrementa campo de instancia
    String idioma = request.getHeader("Accept-Language");
    String agente  = request.getHeader("User-Agent");
    String ahora   = new Date().toString();
%>

<pre>
&lt;%
    visitas++;
    String idioma = request.getHeader("Accept-Language");
    String agente = request.getHeader("User-Agent");
    String ahora  = new Date().toString();
%&gt;
</pre>

<div class="nota">
  Estas variables ya están disponibles para las expresiones de abajo.
</div>


<%-- ═════════════════════════════════════════════ Expresiones ═════ --%>
<h2>3. Expresiones <code>&lt;&#37;= ... &#37;&gt;</code></h2>
<p>Evalúan una expresión Java y escriben el resultado en la respuesta.
Equivalen a <code>out.print(...)</code>. <strong>No</strong> llevan punto y coma.</p>

<pre>
Visita nº: &lt;%= visitas %&gt;
Saludo:    &lt;%= saludar("Mundo") %&gt;
Fecha:     &lt;%= ahora %&gt;
Idioma:    &lt;%= idioma %&gt;
</pre>

<div class="resultado">
  Visita nº: <strong><%= visitas %></strong><br>
  Saludo:    <strong><%= saludar("Mundo") %></strong><br>
  Fecha:     <strong><%= ahora %></strong><br>
  Idioma:    <strong><%= idioma %></strong>
</div>


<%-- ════════════════════════════════════ Flujo con scriptlets ═════ --%>
<h2>4. Control de flujo con scriptlets</h2>
<p>El HTML puede intercalarse con bloques de código abiertos:</p>

<pre>
&lt;% if (visitas == 1) { %&gt;
    &lt;p&gt;¡Primera visita!&lt;/p&gt;
&lt;% } else { %&gt;
    &lt;p&gt;Visita número &lt;%= visitas %&gt;&lt;/p&gt;
&lt;% } %&gt;
</pre>

<div class="resultado">
<% if (visitas == 1) { %>
    <p>&#127881; ¡Primera visita a esta página desde que arrancó Tomcat!</p>
<% } else { %>
    <p>Visita número <strong><%= visitas %></strong> desde que arrancó Tomcat.</p>
<% } %>
</div>

<div class="aviso">
  &#9888; <strong>Por qué evitar scriptlets en producción:</strong>
  mezclan lógica con presentación, son difíciles de testear, reutilizar y mantener.
  Usa EL + JSTL o un framework MVC en su lugar.
</div>

<p><a href="/">← Inicio</a> &nbsp;|&nbsp;
   <a href="directivas.jsp">← Directivas</a> &nbsp;|&nbsp;
   <a href="objetos-implicitos.jsp">Siguiente: Objetos implícitos →</a></p>
</body>
</html>
