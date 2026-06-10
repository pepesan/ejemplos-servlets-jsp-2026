<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>JSTL Core</title>
<style>
body{font-family:monospace;max-width:900px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.resultado{background:#1e1e2e;border-left:3px solid #a6e3a1;padding:.6em 1em;
           margin-top:-.2em;font-size:.9em}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.35em .8em;text-align:left;font-size:.88em}
th{background:#313244;color:#cba6f7}
.ok{color:#a6e3a1}.ko{color:#f38ba8}
</style>
</head>
<body>

<%@ include file="WEB-INF/_menu.jspf" %>

<h1>JSTL — Biblioteca Core (<code>c:</code>)</h1>
<p>Esta página se sirve a través de <code>DatosServlet</code>, que deposita
listas y valores en el <code>request</code> antes de hacer el forward aquí.</p>


<%-- ════════════════════════════════════════════════ c:out ════════ --%>
<h2>1. <code>c:out</code> — salida segura (escapa HTML)</h2>

<pre>
&lt;c:out value="${nombre}" /&gt;
&lt;c:out value="${nulo}" default="valor por defecto" /&gt;
&lt;c:out value="&lt;script&gt;alert('xss')&lt;/script&gt;" /&gt;
</pre>

<div class="resultado">
  nombre (del servlet): <strong><c:out value="${nombre}" default="(no recibido)" /></strong><br>
  nulo con default:     <strong><c:out value="${nulo}" default="valor por defecto" /></strong><br>
  HTML escapado:        <strong><c:out value="<script>alert('xss')</script>" /></strong>
</div>


<%-- ════════════════════════════════════════════════ c:set ════════ --%>
<h2>2. <code>c:set</code> y <code>c:remove</code></h2>

<pre>
&lt;c:set var="saludo" value="Buenos días" /&gt;
&lt;c:set var="doble"  value="${2 * 21}" /&gt;
${saludo} — ${doble}
&lt;c:remove var="saludo" /&gt;
Tras remove: ${saludo}
</pre>

<c:set var="saludo" value="Buenos días" />
<c:set var="doble"  value="${2 * 21}" />
<div class="resultado">
  ${saludo} — ${doble}<br>
  <c:remove var="saludo" />
  Tras c:remove: «<strong>${saludo}</strong>»
</div>


<%-- ══════════════════════════════════════════════ c:if ══════════ --%>
<h2>3. <code>c:if</code></h2>

<pre>
&lt;c:if test="${not empty frutas}"&gt;
    La lista tiene ${frutas.size()} elementos.
&lt;/c:if&gt;
&lt;c:if test="${empty frutas}"&gt;
    Lista vacía.
&lt;/c:if&gt;
</pre>

<div class="resultado">
  <c:if test="${not empty frutas}">
    La lista <strong>frutas</strong> tiene <strong>${frutas.size()}</strong> elementos.
  </c:if>
  <c:if test="${empty frutas}">
    frutas está vacía o no fue cargada.
  </c:if>
</div>


<%-- ══════════════════════════════════════════ c:choose ══════════ --%>
<h2>4. <code>c:choose / c:when / c:otherwise</code></h2>

<pre>
&lt;c:choose&gt;
  &lt;c:when test="${frutas.size() gt 3}"&gt;Lista grande&lt;/c:when&gt;
  &lt;c:when test="${frutas.size() gt 0}"&gt;Lista pequeña&lt;/c:when&gt;
  &lt;c:otherwise&gt;Sin datos&lt;/c:otherwise&gt;
&lt;/c:choose&gt;
</pre>

<div class="resultado">
  <c:choose>
    <c:when test="${frutas.size() gt 3}">Lista grande (más de 3 elementos)</c:when>
    <c:when test="${frutas.size() gt 0}">Lista pequeña</c:when>
    <c:otherwise>Sin datos del servlet</c:otherwise>
  </c:choose>
</div>


<%-- ═══════════════════════════════════════ c:forEach ═══════════ --%>
<h2>5. <code>c:forEach</code> — lista simple</h2>

<pre>
&lt;c:forEach var="fruta" items="${frutas}" varStatus="st"&gt;
    ${st.index + 1}. ${fruta}
&lt;/c:forEach&gt;
</pre>

<div class="resultado">
  <c:forEach var="fruta" items="${frutas}" varStatus="st">
    <span>${st.index + 1}. <strong>${fruta}</strong>
    <c:if test="${st.first}"> ← primero</c:if>
    <c:if test="${st.last}"> ← último</c:if>
    </span><br>
  </c:forEach>
</div>


<%-- ════════════════════════════ c:forEach — lista de objetos ════ --%>
<h2>6. <code>c:forEach</code> — lista de objetos (productos)</h2>

<pre>
&lt;c:forEach var="p" items="${productos}"&gt;
    ${p.nombre} — ${p.precio} € —
    &lt;c:if test="${p.disponible}"&gt;disponible&lt;/c:if&gt;
&lt;/c:forEach&gt;
</pre>

<table>
  <tr><th>#</th><th>Nombre</th><th>Precio</th><th>Disponible</th></tr>
  <c:forEach var="p" items="${productos}" varStatus="st">
  <tr>
    <td>${st.count}</td>
    <td>${p.nombre}</td>
    <td>${p.precio} €</td>
    <td class="${p.disponible ? 'ok' : 'ko'}">${p.disponible ? '✓' : '✗'}</td>
  </tr>
  </c:forEach>
</table>


<%-- ═══════════════════════════════════════════ c:url ═══════════ --%>
<h2>7. <code>c:url</code> — construir URLs</h2>

<pre>
&lt;c:url value="/el.jsp" var="urlEl"&gt;
    &lt;c:param name="nombre" value="Ana" /&gt;
    &lt;c:param name="edad"   value="28" /&gt;
&lt;/c:url&gt;
&lt;a href="${urlEl}"&gt;Ver EL con params&lt;/a&gt;
</pre>

<div class="resultado">
  <c:url value="/el.jsp" var="urlEl">
    <c:param name="nombre" value="Ana" />
    <c:param name="edad"   value="28" />
  </c:url>
  URL construida: <code>${urlEl}</code><br>
  <a href="${urlEl}">Abrir el.jsp con esos params</a>
</div>

<p><a href="${pageContext.request.contextPath}/">← Inicio</a> &nbsp;|&nbsp;
   <a href="el.jsp">← EL</a> &nbsp;|&nbsp;
   <a href="datos?vista=fmt">Siguiente: JSTL fmt →</a></p>
</body>
</html>
