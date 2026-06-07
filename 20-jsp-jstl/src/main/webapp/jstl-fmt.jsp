<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>JSTL Fmt</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
.resultado{background:#1e1e2e;border-left:3px solid #a6e3a1;padding:.6em 1em;
           margin-top:-.2em;font-size:.9em;line-height:2}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.35em .8em;text-align:left;font-size:.88em}
th{background:#313244;color:#cba6f7}
td:first-child{color:#89b4fa}
</style>
</head>
<body>

<%@ include file="WEB-INF/_menu.jspf" %>

<h1>JSTL — Biblioteca de formato (<code>fmt:</code>)</h1>
<p>Esta página se sirve a través de <code>DatosServlet</code>, que deposita
fechas y números en el <code>request</code>.</p>


<%-- ══════════════════════════════════════ fmt:formatDate ════════ --%>
<h2>1. <code>fmt:formatDate</code> — formatear fechas</h2>

<pre>
&lt;fmt:formatDate value="${ahora}" type="both"  pattern="dd/MM/yyyy HH:mm:ss" /&gt;
&lt;fmt:formatDate value="${ahora}" type="date"  dateStyle="full" /&gt;
&lt;fmt:formatDate value="${ahora}" type="time"  timeStyle="short" /&gt;
&lt;fmt:formatDate value="${ahora}" type="both"  dateStyle="medium" timeStyle="short" /&gt;
</pre>

<div class="resultado">
  Patrón dd/MM/yyyy HH:mm:ss → <strong><fmt:formatDate value="${ahora}" type="both" pattern="dd/MM/yyyy HH:mm:ss" /></strong><br>
  dateStyle full             → <strong><fmt:formatDate value="${ahora}" type="date" dateStyle="full" /></strong><br>
  timeStyle short            → <strong><fmt:formatDate value="${ahora}" type="time" timeStyle="short" /></strong><br>
  ISO 8601                   → <strong><fmt:formatDate value="${ahora}" type="both" pattern="yyyy-MM-dd'T'HH:mm:ss" /></strong>
</div>


<%-- ══════════════════════════════════════ fmt:formatNumber ══════ --%>
<h2>2. <code>fmt:formatNumber</code> — formatear números</h2>

<pre>
&lt;fmt:formatNumber value="${precio}" type="number"   minFractionDigits="2" /&gt;
&lt;fmt:formatNumber value="${precio}" type="currency" currencySymbol="€" /&gt;
&lt;fmt:formatNumber value="${pct}"    type="percent"  /&gt;
&lt;fmt:formatNumber value="1234567.89" groupingUsed="true" /&gt;
</pre>

<div class="resultado">
  number (2 decimales) → <strong><fmt:formatNumber value="${precio}" type="number" minFractionDigits="2" /></strong><br>
  currency (€)         → <strong><fmt:formatNumber value="${precio}" type="currency" currencySymbol="€" /></strong><br>
  percent              → <strong><fmt:formatNumber value="${pct}" type="percent" /></strong><br>
  con separadores      → <strong><fmt:formatNumber value="1234567.89" groupingUsed="true" /></strong>
</div>


<%-- ══════════════════════════════════════ fmt:setLocale ═════════ --%>
<h2>3. <code>fmt:setLocale</code> — cambiar el locale</h2>

<pre>
&lt;fmt:setLocale value="es_ES" /&gt;
&lt;fmt:formatNumber value="${precio}" type="currency" /&gt;

&lt;fmt:setLocale value="en_US" /&gt;
&lt;fmt:formatNumber value="${precio}" type="currency" /&gt;

&lt;fmt:setLocale value="de_DE" /&gt;
&lt;fmt:formatNumber value="${precio}" type="currency" /&gt;
</pre>

<table>
  <tr><th>Locale</th><th>Moneda formateada</th><th>Fecha formateada</th></tr>
  <tr>
    <td>es_ES</td>
    <td><fmt:setLocale value="es_ES" /><fmt:formatNumber value="${precio}" type="currency" /></td>
    <td><fmt:formatDate value="${ahora}" type="date" dateStyle="medium" /></td>
  </tr>
  <tr>
    <td>en_US</td>
    <td><fmt:setLocale value="en_US" /><fmt:formatNumber value="${precio}" type="currency" /></td>
    <td><fmt:formatDate value="${ahora}" type="date" dateStyle="medium" /></td>
  </tr>
  <tr>
    <td>de_DE</td>
    <td><fmt:setLocale value="de_DE" /><fmt:formatNumber value="${precio}" type="currency" /></td>
    <td><fmt:formatDate value="${ahora}" type="date" dateStyle="medium" /></td>
  </tr>
  <tr>
    <td>ja_JP</td>
    <td><fmt:setLocale value="ja_JP" /><fmt:formatNumber value="${precio}" type="currency" /></td>
    <td><fmt:formatDate value="${ahora}" type="date" dateStyle="medium" /></td>
  </tr>
</table>


<%-- ══════════════════════════════════════ fmt:parseDate ═════════ --%>
<h2>4. <code>fmt:parseDate</code> — parsear una cadena a fecha</h2>

<pre>
&lt;fmt:parseDate value="25/12/2026" pattern="dd/MM/yyyy" var="navidad" /&gt;
Navidad: &lt;fmt:formatDate value="${navidad}" type="date" dateStyle="full" /&gt;
</pre>

<div class="resultado">
  <fmt:setLocale value="es_ES" />
  <fmt:parseDate value="25/12/2026" pattern="dd/MM/yyyy" var="navidad" />
  Navidad: <strong><fmt:formatDate value="${navidad}" type="date" dateStyle="full" /></strong>
</div>

<p><a href="/">← Inicio</a> &nbsp;|&nbsp;
   <a href="datos">← JSTL core</a></p>
</body>
</html>
