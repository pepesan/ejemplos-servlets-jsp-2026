<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>JSTL fn: — Funciones</title>
<style>
body{font-family:monospace;max-width:940px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{color:#a6adc8;line-height:1.6}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.resultado{background:#1e1e2e;border-left:3px solid #a6e3a1;padding:.6em 1em;
           margin-top:-.2em;font-size:.9em}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;font-size:.88em;vertical-align:top}
th{background:#313244;color:#cba6f7;white-space:nowrap}
td:first-child{color:#89b4fa;font-size:.85em}
td:nth-child(2){color:#fab387}
td:nth-child(3){color:#a6e3a1}
</style>
</head>
<body>

<%@ include file="WEB-INF/_menu.jspf" %>

<%-- Datos de demostración --%>
<c:set var="s"   value="  Hola, Mundo JSP!  " />
<c:set var="csv" value="java,servlet,jsp,jstl,el" />
<c:set var="arr" value="${fn:split(csv, ',')}" />

<h1>JSTL — Biblioteca de funciones (<code>fn:</code>)</h1>
<p>Requiere <code>&#37;@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" &#37;</code>.<br>
Cadena de trabajo: <code>"${s}"</code> &nbsp;|&nbsp; CSV: <code>"${csv}"</code></p>


<%-- ══════════════════════════════════════════ LONGITUD ══════════ --%>
<h2>1. <code>fn:length</code></h2>

<pre>
&lt;c:set var="s"   value="  Hola, Mundo JSP!  " /&gt;
&lt;c:set var="arr" value="${fn:split(csv, ',')}" /&gt;
${fn:length(s)}    &lt;!-- longitud de cadena (incluye espacios) --&gt;
${fn:length(arr)}  &lt;!-- tamaño de array / colección --&gt;
</pre>

<div class="resultado">
  fn:length(s) = <strong>${fn:length(s)}</strong> (incluye los espacios del inicio y fin)<br>
  fn:length(arr) = <strong>${fn:length(arr)}</strong> (5 palabras del CSV)
</div>


<%-- ══════════════════════════════ MAYÚSCULAS / MINÚSCULAS ══════ --%>
<h2>2. <code>fn:toUpperCase</code>, <code>fn:toLowerCase</code>, <code>fn:trim</code></h2>

<pre>
${fn:toUpperCase(s)}
${fn:toLowerCase(s)}
|${fn:trim(s)}|
</pre>

<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:toUpperCase</code></td><td><code>\${fn:toUpperCase(s)}</code></td><td>${fn:toUpperCase(s)}</td></tr>
<tr><td><code>fn:toLowerCase</code></td><td><code>\${fn:toLowerCase(s)}</code></td><td>${fn:toLowerCase(s)}</td></tr>
<tr><td><code>fn:trim</code></td>       <td><code>"|" + fn:trim(s) + "|"</code></td><td>|${fn:trim(s)}|</td></tr>
</table>


<%-- ══════════════════════════════ BÚSQUEDA Y POSICIÓN ══════════ --%>
<h2>3. <code>fn:contains</code>, <code>fn:startsWith</code>, <code>fn:endsWith</code>, <code>fn:indexOf</code></h2>
<p>
  <code>fn:contains</code>, <code>fn:startsWith</code> y <code>fn:endsWith</code>
  devuelven <code>boolean</code> (true/false) y son case-sensitive.<br>
  <code>fn:containsIgnoreCase</code> hace lo mismo ignorando mayúsculas/minúsculas.<br>
  <code>fn:indexOf</code> devuelve la posición (base 0) de la primera ocurrencia,
  o <strong>-1</strong> si no se encuentra.<br>
  Recuerda que <code>s = "&nbsp;&nbsp;Hola, Mundo JSP!&nbsp;&nbsp;"</code> tiene espacios al inicio y al final,
  por eso <code>fn:startsWith</code> y <code>fn:endsWith</code> necesitan <code>fn:trim</code> primero.
</p>

<pre>
&lt;!-- s = "  Hola, Mundo JSP!  " --&gt;
${fn:contains(s, 'JSP')}               &lt;!-- true: 'JSP' aparece en s --&gt;
${fn:contains(s, 'java')}              &lt;!-- false: case-sensitive, 'java' != 'JSP' --&gt;
${fn:containsIgnoreCase(s, 'hola')}    &lt;!-- true: ignora mayúsculas --&gt;
${fn:startsWith(s, 'Hola')}            &lt;!-- false: s empieza por espacios --&gt;
${fn:startsWith(fn:trim(s), 'Hola')}   &lt;!-- true: tras trim ya empieza por 'Hola' --&gt;
${fn:endsWith(fn:trim(s), '!')}        &lt;!-- true: tras trim termina en '!' --&gt;
${fn:indexOf(s, 'Mundo')}              &lt;!-- 8: posición base 0 --&gt;
${fn:indexOf(s, 'xyz')}                &lt;!-- -1: no encontrado --&gt;
</pre>

<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:contains</code> (encontrado)</td>
    <td><code>\${fn:contains(s, 'JSP')}</code></td>
    <td>${fn:contains(s, 'JSP')}</td></tr>
<tr><td><code>fn:contains</code> (no encontrado — case-sensitive)</td>
    <td><code>\${fn:contains(s, 'java')}</code></td>
    <td>${fn:contains(s, 'java')}</td></tr>
<tr><td><code>fn:containsIgnoreCase</code></td>
    <td><code>\${fn:containsIgnoreCase(s, 'hola')}</code></td>
    <td>${fn:containsIgnoreCase(s, 'hola')}</td></tr>
<tr><td><code>fn:startsWith</code> sin trim (falla por espacios)</td>
    <td><code>\${fn:startsWith(s, 'Hola')}</code></td>
    <td>${fn:startsWith(s, 'Hola')}</td></tr>
<tr><td><code>fn:startsWith</code> con trim</td>
    <td><code>\${fn:startsWith(fn:trim(s), 'Hola')}</code></td>
    <td>${fn:startsWith(fn:trim(s), 'Hola')}</td></tr>
<tr><td><code>fn:endsWith</code> con trim</td>
    <td><code>\${fn:endsWith(fn:trim(s), '!')}</code></td>
    <td>${fn:endsWith(fn:trim(s), '!')}</td></tr>
<tr><td><code>fn:indexOf</code> (encontrado → posición base 0)</td>
    <td><code>\${fn:indexOf(s, 'Mundo')}</code></td>
    <td>${fn:indexOf(s, 'Mundo')}</td></tr>
<tr><td><code>fn:indexOf</code> (no encontrado → -1)</td>
    <td><code>\${fn:indexOf(s, 'xyz')}</code></td>
    <td>${fn:indexOf(s, 'xyz')}</td></tr>
</table>


<%-- ════════════════════════════════════════ SUBCADENAS ══════════ --%>
<h2>4. <code>fn:substring</code>, <code>fn:substringBefore</code>, <code>fn:substringAfter</code></h2>

<pre>
${fn:substring(fn:trim(s), 0, 4)}   &lt;!-- "Hola" --&gt;
${fn:substringBefore(csv, ',')}     &lt;!-- "java" --&gt;
${fn:substringAfter(csv, ',')}      &lt;!-- "servlet,jsp,jstl,el" --&gt;
</pre>

<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:substring(s, inicio, fin)</code></td>
    <td><code>\${fn:substring(fn:trim(s), 0, 4)}</code></td>
    <td>${fn:substring(fn:trim(s), 0, 4)}</td></tr>
<tr><td><code>fn:substringBefore</code></td>
    <td><code>\${fn:substringBefore(csv, ',')}</code></td>
    <td>${fn:substringBefore(csv, ',')}</td></tr>
<tr><td><code>fn:substringAfter</code></td>
    <td><code>\${fn:substringAfter(csv, ',')}</code></td>
    <td>${fn:substringAfter(csv, ',')}</td></tr>
</table>


<%-- ══════════════════════════════════════════ REEMPLAZAR ════════ --%>
<h2>5. <code>fn:replace</code></h2>

<pre>
${fn:replace(s, 'Mundo', 'Curso')}  &lt;!-- sustituir subcadena --&gt;
${fn:replace(s, ' ', '')}           &lt;!-- eliminar espacios --&gt;
</pre>

<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:replace</code> (sustituir)</td>
    <td><code>\${fn:replace(s, 'Mundo', 'Curso')}</code></td>
    <td>${fn:replace(s, 'Mundo', 'Curso')}</td></tr>
<tr><td><code>fn:replace</code> (eliminar)</td>
    <td><code>\${fn:replace(s, ' ', '')}</code></td>
    <td>${fn:replace(s, ' ', '')}</td></tr>
</table>


<%-- ════════════════════════════════════════ SPLIT Y JOIN ════════ --%>
<h2>6. <code>fn:split</code> y <code>fn:join</code></h2>

<pre>
&lt;c:set var="arr" value="${fn:split(csv, ',')}" /&gt;
${fn:length(arr)}           &lt;!-- 5 --&gt;
${fn:join(arr, ' | ')}      &lt;!-- "java | servlet | ..." --&gt;
</pre>

<table>
<tr><th>Función</th><th>Código</th><th>Resultado</th></tr>
<tr><td><code>fn:split</code></td>
    <td><code>\${fn:split(csv, ',')}</code></td>
    <td>Array de ${fn:length(arr)} elementos</td></tr>
<tr><td><code>fn:join</code> (barra)</td>
    <td><code>\${fn:join(arr, ' | ')}</code></td>
    <td>${fn:join(arr, ' | ')}</td></tr>
<tr><td><code>fn:join</code> (punto)</td>
    <td><code>\${fn:join(arr, ' · ')}</code></td>
    <td>${fn:join(arr, ' · ')}</td></tr>
</table>


<%-- ══════════════════════════ SEGURIDAD — escapeXml ═══════════ --%>
<h2>7. <code>fn:escapeXml</code></h2>

<pre>
${fn:escapeXml('&lt;script&gt;alert("xss")&lt;/script&gt;')}
</pre>

<table>
<tr><th>Función</th><th>Input</th><th>Resultado</th></tr>
<tr>
  <td><code>fn:escapeXml</code></td>
  <td><code>&lt;script&gt;alert("xss")&lt;/script&gt;</code></td>
  <td>${fn:escapeXml('<script>alert("xss")</script>')}</td>
</tr>
<tr>
  <td>Sin escapar (peligroso)</td>
  <td><code>&lt;b&gt;negrita&lt;/b&gt;</code></td>
  <td><b>negrita</b> ← HTML interpretado</td>
</tr>
<tr>
  <td>Con <code>fn:escapeXml</code></td>
  <td><code>&lt;b&gt;negrita&lt;/b&gt;</code></td>
  <td>${fn:escapeXml('<b>negrita</b>')} ← mostrado como texto</td>
</tr>
</table>


<%-- ═══════════════════════════════ COMBINANDO FUNCIONES ════════ --%>
<h2>8. Combinando funciones</h2>

<pre>
${fn:toUpperCase(fn:trim(s))}
${fn:length(fn:split(csv, ','))}
${fn:replace(fn:toLowerCase(csv), ',', ' | ')}
${fn:contains(fn:toLowerCase(s), 'mundo')}
</pre>

<table>
<tr><th>Expresión</th><th>Resultado</th></tr>
<tr><td><code>\${fn:toUpperCase(fn:trim(s))}</code></td>
    <td>${fn:toUpperCase(fn:trim(s))}</td></tr>
<tr><td><code>\${fn:length(fn:split(csv, ','))}</code></td>
    <td>${fn:length(fn:split(csv, ','))}</td></tr>
<tr><td><code>\${fn:replace(fn:toLowerCase(csv), ',', ' | ')}</code></td>
    <td>${fn:replace(fn:toLowerCase(csv), ',', ' | ')}</td></tr>
<tr><td><code>\${fn:contains(fn:toLowerCase(s), 'mundo')}</code></td>
    <td>${fn:contains(fn:toLowerCase(s), 'mundo')}</td></tr>
</table>


<%-- ═══════════════════════════ USO CON c:forEach ═══════════════ --%>
<h2>9. Uso con <code>c:forEach</code></h2>
<p>Iterar sobre el resultado de <code>fn:split</code> y aplicar funciones a cada elemento:</p>

<pre>
&lt;c:forEach var="palabra" items="${fn:split(csv, ',')}" varStatus="st"&gt;
    ${st.count}. ${palabra} → ${fn:toUpperCase(palabra)} (${fn:length(palabra)} chars)
&lt;/c:forEach&gt;
</pre>

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

<p><a href="${pageContext.request.contextPath}/datos?vista=fmt">← JSTL fmt</a></p>
</body>
</html>
