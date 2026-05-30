<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8"><title>JSTL fn: — Funciones</title>
<style>
body{font-family:monospace;max-width:940px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{color:#a6adc8;line-height:1.6}
a{color:#89dceb}code{color:#a6e3a1}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;font-size:.88em;vertical-align:top}
th{background:#313244;color:#cba6f7;white-space:nowrap}
td:first-child{color:#89b4fa;font-size:.85em}
td:nth-child(2){color:#fab387}
td:nth-child(3){color:#a6e3a1}
.section{background:#181825;border-left:3px solid #cba6f7;padding:.3em .8em;
         margin:1.2em 0 .3em;font-size:.78em;color:#cba6f7;letter-spacing:.05em}
.nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}
.nav a{color:#a6adc8;text-decoration:none;margin-right:1em}.nav a:hover{color:#cdd6f4}
</style>
</head>
<body>

<%-- Datos de demostración --%>
<c:set var="s"    value="  Hola, Mundo JSP!  " />
<c:set var="csv"  value="java,servlet,jsp,jstl,el" />
<c:set var="html" value='&lt;script&gt;alert("xss")&lt;/script&gt;' />
<c:set var="arr"  value="${fn:split(csv, ',')}" />

<div class="nav"><a href="/">← Inicio</a></div>

<h1>JSTL — Biblioteca de funciones (<code>fn:</code>)</h1>
<p>Requiere <code>&#37;@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" &#37;</code>.<br>
Cadena de trabajo: <code>"${s}"</code> &nbsp;|&nbsp; CSV: <code>"${csv}"</code></p>

<div class="section">LONGITUD</div>
<table>
<tr><th>Expresión EL</th><th>Código</th><th>Resultado</th></tr>
<tr>
  <td><code>fn:length(s)</code></td>
  <td><code>\${fn:length(s)}</code></td>
  <td>${fn:length(s)}</td>
</tr>
<tr>
  <td><code>fn:length(arr)</code> (array/colección)</td>
  <td><code>\${fn:length(arr)}</code></td>
  <td>${fn:length(arr)}</td>
</tr>
</table>

<div class="section">MAYÚSCULAS / MINÚSCULAS</div>
<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:toUpperCase</code></td><td><code>\${fn:toUpperCase(s)}</code></td><td>${fn:toUpperCase(s)}</td></tr>
<tr><td><code>fn:toLowerCase</code></td><td><code>\${fn:toLowerCase(s)}</code></td><td>${fn:toLowerCase(s)}</td></tr>
<tr><td><code>fn:trim</code></td>        <td><code>"|" += fn:trim(s) += "|"</code></td><td>|${fn:trim(s)}|</td></tr>
</table>

<div class="section">BÚSQUEDA Y POSICIÓN</div>
<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:contains</code></td>
    <td><code>\${fn:contains(s, 'JSP')}</code></td>
    <td>${fn:contains(s, 'JSP')}</td></tr>
<tr><td><code>fn:containsIgnoreCase</code></td>
    <td><code>\${fn:containsIgnoreCase(s, 'hola')}</code></td>
    <td>${fn:containsIgnoreCase(s, 'hola')}</td></tr>
<tr><td><code>fn:startsWith</code></td>
    <td><code>\${fn:startsWith(fn:trim(s), 'Hola')}</code></td>
    <td>${fn:startsWith(fn:trim(s), 'Hola')}</td></tr>
<tr><td><code>fn:endsWith</code></td>
    <td><code>\${fn:endsWith(fn:trim(s), '!')}</code></td>
    <td>${fn:endsWith(fn:trim(s), '!')}</td></tr>
<tr><td><code>fn:indexOf</code></td>
    <td><code>\${fn:indexOf(s, 'Mundo')}</code></td>
    <td>${fn:indexOf(s, 'Mundo')}</td></tr>
</table>

<div class="section">SUBCADENAS</div>
<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:substring(s, inicio, fin)</code></td>
    <td><code>\${fn:substring(fn:trim(s), 0, 4)}</code></td>
    <td>${fn:substring(fn:trim(s), 0, 4)}</td></tr>
<tr><td><code>fn:substringBefore</code></td>
    <td><code>\${fn:substringBefore(s, ',')}</code></td>
    <td>${fn:substringBefore(csv, ',')}</td></tr>
<tr><td><code>fn:substringAfter</code></td>
    <td><code>\${fn:substringAfter(csv, ',')}</code></td>
    <td>${fn:substringAfter(csv, ',')}</td></tr>
</table>

<div class="section">REEMPLAZAR</div>
<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:replace</code></td>
    <td><code>\${fn:replace(s, 'Mundo', 'Curso')}</code></td>
    <td>${fn:replace(s, 'Mundo', 'Curso')}</td></tr>
<tr><td><code>fn:replace</code> (eliminar)</td>
    <td><code>\${fn:replace(s, ' ', '')}</code></td>
    <td>${fn:replace(s, ' ', '')}</td></tr>
</table>

<div class="section">SPLIT Y JOIN</div>
<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:split</code></td>
    <td><code>\${fn:split(csv, ',')}</code></td>
    <td>Array de ${fn:length(arr)} elementos</td></tr>
<tr><td><code>fn:join</code> (reunir)</td>
    <td><code>\${fn:join(arr, ' | ')}</code></td>
    <td>${fn:join(arr, ' | ')}</td></tr>
<tr><td><code>fn:join</code> (con espacio)</td>
    <td><code>\${fn:join(arr, ' · ')}</code></td>
    <td>${fn:join(arr, ' · ')}</td></tr>
</table>

<div class="section">SEGURIDAD — escapeXml</div>
<table>
<tr><th>Función</th><th>Input</th><th>Resultado (HTML escapado)</th></tr>
<tr>
  <td><code>fn:escapeXml</code></td>
  <td><code>&lt;script&gt;alert("xss")&lt;/script&gt;</code></td>
  <td>${fn:escapeXml('&lt;script&gt;alert("xss")&lt;/script&gt;')}</td>
</tr>
<tr>
  <td>Sin escapar (peligroso)</td>
  <td><code>&lt;b&gt;negrita&lt;/b&gt;</code></td>
  <td><b>negrita</b> ← el HTML se interpreta</td>
</tr>
<tr>
  <td>Con <code>fn:escapeXml</code></td>
  <td><code>&lt;b&gt;negrita&lt;/b&gt;</code></td>
  <td>${fn:escapeXml('&lt;b&gt;negrita&lt;/b&gt;')} ← el HTML se muestra como texto</td>
</tr>
</table>

<div class="section">COMBINANDO FUNCIONES</div>
<table>
<tr><th>Expresión</th><th>Resultado</th></tr>
<tr>
  <td><code>\${fn:toUpperCase(fn:trim(s))}</code></td>
  <td>${fn:toUpperCase(fn:trim(s))}</td>
</tr>
<tr>
  <td><code>\${fn:length(fn:split(csv, ','))}</code></td>
  <td>${fn:length(fn:split(csv, ','))}</td>
</tr>
<tr>
  <td><code>\${fn:replace(fn:toLowerCase(csv), ',', ' | ')}</code></td>
  <td>${fn:replace(fn:toLowerCase(csv), ',', ' | ')}</td>
</tr>
<tr>
  <td><code>\${fn:contains(fn:toLowerCase(s), 'mundo')}</code></td>
  <td>${fn:contains(fn:toLowerCase(s), 'mundo')}</td>
</tr>
</table>

<div class="section">USO CON c:forEach</div>
<p>Iterar sobre el resultado de <code>fn:split</code>:</p>
<table>
<tr><th>#</th><th>Elemento</th><th>Mayúsculas</th><th>Longitud</th></tr>
<c:forEach var="palabra" items="${fn:split(csv, ',')}" varStatus="st">
<tr>
  <td>${st.count}</td>
  <td>${palabra}</td>
  <td>${fn:toUpperCase(palabra)}</td>
  <td>${fn:length(palabra)}</td>
</tr>
</c:forEach>
</table>

</body>
</html>
