<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         errorPage="/mi-error.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>demos.jsp — directiva errorPage</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.4em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.err{background:#f38ba820;border-left:3px solid #f38ba8;padding:.6em 1em;
     border-radius:0 4px 4px 0;font-size:.88em;color:#f38ba8;margin:.4em 0}
.nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}
.nav a{color:#a6adc8;text-decoration:none;margin-right:1em}.nav a:hover{color:#cdd6f4}
.links a{background:#45475a;padding:.2em .7em;border-radius:3px;text-decoration:none;
         font-size:.85em;color:#cdd6f4;margin-right:.4em}
.links a:hover{background:#585b70}
.links a.e{background:#f38ba833;color:#f38ba8}.links a.e:hover{background:#f38ba855}
</style>
</head>
<body>

<%-- ══════════════════════════════════════════════════════════════
     Este bloque se ejecuta ANTES de producir ningún HTML.
     Si lanza una excepción, el contenedor hace forward a errorPage.
     ══════════════════════════════════════════════════════════════ --%>
<%
    String forzar = request.getParameter("forzar");
    if ("division".equals(forzar)) {
        // ArithmeticException: / by zero
        int resultado = 1 / 0;
    } else if ("npe".equals(forzar)) {
        // NullPointerException
        String nulo = null;
        int len = nulo.length();
    } else if ("custom".equals(forzar)) {
        // Excepción de aplicación
        throw new IllegalArgumentException("Valor fuera del rango permitido: " + forzar);
    }
    // Si forzar es null o desconocido, la página se muestra normalmente
%>

<div class="nav">
  <a href="/">← Inicio</a>
</div>

<h1>demos.jsp — directiva <code>errorPage</code></h1>

<h2>La directiva en esta misma página</h2>
<pre>
&lt;%@ page errorPage="/mi-error.jsp" %&gt;
</pre>
<p>Esta única línea es suficiente. Si cualquier código de esta JSP lanza
una excepción no capturada, el contenedor hace un forward a
<code>/mi-error.jsp</code>, pasándole la excepción en los atributos del request.</p>

<h2>La página de error receptora debe declarar</h2>
<pre>
&lt;%@ page isErrorPage="true" %&gt;
</pre>
<p>Con <code>isErrorPage="true"</code> el contenedor activa el objeto implícito
<code>exception</code>, que contiene la excepción original. Sin esta directiva,
<code>exception</code> no existe y se produce un error de compilación.</p>

<h2>Cómo se activa el mecanismo</h2>
<pre>
&lt;%-- En demos.jsp: cualquiera de estos lanza una excepción ---%gt;

&lt;%  int x = 1 / 0;                        // ArithmeticException  %&gt;
&lt;%  String s = null; s.length();           // NullPointerException %&gt;
&lt;%  throw new IllegalArgumentException();  // excepción custom     %&gt;
</pre>

<div class="err">
    &#9888; El bloque scriptlet que lanza la excepción debe estar <strong>antes</strong>
    de escribir cualquier byte de respuesta. Si el buffer ya se volcó al cliente
    (<code>out.flush()</code>), el forward al errorPage no puede cambiar las cabeceras
    y el resultado puede ser imprevisible.
</div>

<h2>Probarlo ahora</h2>
<div class="links">
  <a href="demos.jsp">Sin error</a>
  <a href="demos.jsp?forzar=division" class="e">ArithmeticException (÷0)</a>
  <a href="demos.jsp?forzar=npe" class="e">NullPointerException</a>
  <a href="demos.jsp?forzar=custom" class="e">IllegalArgumentException</a>
</div>

</body>
</html>
