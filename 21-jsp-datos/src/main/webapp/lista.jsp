<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Listas — Servlet → JSP</title>
<style>
body{font-family:monospace;max-width:900px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
.grid{display:grid;grid-template-columns:1fr 1fr;gap:1em;margin:.6em 0}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.85em;line-height:1.7;margin:0}
.out{background:#181825;border-left:3px solid #a6e3a1;padding:.8em 1em;
     border-radius:0 4px 4px 0;font-size:.85em;margin:0}
.label{font-size:.78em;color:#585b70;margin-bottom:.2em}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;font-size:.88em}
th{background:#313244;color:#cba6f7;white-space:nowrap}
.idx{color:#585b70}.first{color:#a6e3a1}.last{color:#f38ba8}
.nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}
.nav a{color:#a6adc8;text-decoration:none;margin-right:1em}
.nav a:hover{color:#cdd6f4}
.nav .active{color:#89b4fa;font-weight:bold}
</style>
</head>
<body>

<div class="nav">
  <a href="/">Inicio</a>
  <a href="datos?vista=individual">Individuales</a>
  <a href="datos?vista=lista" class="active">Listas</a>
  <a href="datos?vista=bean">Beans</a>
</div>

<h1>Paso de listas: Servlet → JSP</h1>
<p>El servlet envía <code>List&lt;T&gt;</code> como atributo del request.
En la JSP se itera con <code>c:forEach</code> y se accede por índice con
<code>\${lista[0]}</code>.</p>

<%-- ═══════════════════════════════════ Lista simple de Strings ═════ --%>
<h2>1. Lista de Strings — <code>List&lt;String&gt;</code></h2>
<div class="grid">
<div>
<div class="label">DatosServlet.java</div>
<pre>
List&lt;String&gt; colores = Arrays.asList(
    "Rojo", "Verde", "Azul",
    "Amarillo", "Naranja"
);
req.setAttribute("colores", colores);
</pre>
</div>
<div>
<div class="label">lista.jsp — iteración básica</div>
<pre>
Tamaño: \${colores.size()}
Primero: \${colores[0]}

&lt;c:forEach var="c" items="\${colores}"&gt;
  \${c}
&lt;/c:forEach&gt;
</pre>
</div>
</div>

<p>Tamaño: <strong>${colores.size()}</strong> &nbsp;|&nbsp;
   Primero: <strong>${colores[0]}</strong> &nbsp;|&nbsp;
   Último: <strong>${colores[colores.size()-1]}</strong></p>

<table>
  <tr><th>Elemento</th></tr>
  <c:forEach var="color" items="${colores}">
  <tr><td>${color}</td></tr>
  </c:forEach>
</table>

<%-- ══════════════════════════════════════════ varStatus ═══════════ --%>
<h2>2. <code>varStatus</code> — posición y metadatos del bucle</h2>
<div class="grid">
<div>
<div class="label">lista.jsp</div>
<pre>
&lt;c:forEach var="c" items="\${colores}"
           varStatus="st"&gt;
  \${st.index}   → 0..n-1
  \${st.count}   → 1..n
  \${st.first}   → true en el primero
  \${st.last}    → true en el último
&lt;/c:forEach&gt;
</pre>
</div>
<div>
<div class="label">Resultado</div>
<div class="out">
<table style="margin:0;width:auto">
  <tr><th>index</th><th>count</th><th>first</th><th>last</th><th>valor</th></tr>
  <c:forEach var="color" items="${colores}" varStatus="st">
  <tr>
    <td class="idx">${st.index}</td>
    <td>${st.count}</td>
    <td class="${st.first ? 'first' : ''}">${st.first}</td>
    <td class="${st.last  ? 'last'  : ''}">${st.last}</td>
    <td>${color}</td>
  </tr>
  </c:forEach>
</table>
</div>
</div>
</div>

<%-- ══════════════════════════════════ Lista de números (Integer) ══ --%>
<h2>3. Lista de números — <code>List&lt;Integer&gt;</code></h2>
<div class="grid">
<div>
<div class="label">DatosServlet.java</div>
<pre>
List&lt;Integer&gt; nums = Arrays.asList(
    10, 20, 30, 40, 50
);
req.setAttribute("nums", nums);
</pre>
</div>
<div>
<div class="label">lista.jsp — acumulador con c:set</div>
<pre>
&lt;c:set var="suma" value="0"/&gt;
&lt;c:forEach var="n" items="\${nums}"&gt;
  &lt;c:set var="suma" value="\${suma + n}"/&gt;
  \${n}
&lt;/c:forEach&gt;
Suma: \${suma}
</pre>
</div>
</div>

<c:set var="suma" value="0"/>
<c:forEach var="n" items="${nums}" varStatus="st">
  <c:set var="suma" value="${suma + n}"/>
</c:forEach>

<p>Valores: <c:forEach var="n" items="${nums}" varStatus="st">${n}<c:if test="${!st.last}">, </c:if></c:forEach></p>
<p>Suma total: <strong>${suma}</strong></p>

<%-- ════════════════════════════════════════ Lista mezclada ════════ --%>
<h2>4. Lista de tipos heterogéneos — <code>List&lt;Object&gt;</code></h2>
<div class="grid">
<div>
<div class="label">DatosServlet.java</div>
<pre>
List&lt;Object&gt; mezclada = Arrays.asList(
    "texto", 99, 3.14, true, new Date()
);
req.setAttribute("mezclada", mezclada);
</pre>
</div>
<div>
<div class="label">lista.jsp</div>
<pre>
&lt;c:forEach var="item" items="\${mezclada}"&gt;
  \${item}
&lt;/c:forEach&gt;
</pre>
</div>
</div>

<table>
  <tr><th>#</th><th>Valor (EL lo convierte a String)</th></tr>
  <c:forEach var="item" items="${mezclada}" varStatus="st">
  <tr><td>${st.count}</td><td>${item}</td></tr>
  </c:forEach>
</table>

<p style="margin-top:2em">
  <a href="datos?vista=individual">← Individuales</a> &nbsp;|&nbsp;
  <a href="datos?vista=bean">Siguiente: Beans →</a>
</p>
</body>
</html>
