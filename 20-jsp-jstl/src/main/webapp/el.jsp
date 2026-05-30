<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Expression Language (EL)</title>
<style>
body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.35em .8em;text-align:left;font-size:.88em}
th{background:#313244;color:#cba6f7}
td:first-child{color:#89b4fa}
td:nth-child(2){color:#fab387}
.resultado{background:#1e1e2e;border-left:3px solid #a6e3a1;padding:.6em 1em;
           margin-top:-.2em;font-size:.9em}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;color:#a6adc8;margin:.5em 0}
</style>
</head>
<body>

<p style="margin:0 0 1.5em"><a href="/">← Inicio</a></p>

<h1>Expression Language (EL)</h1>
<p>EL permite leer atributos, parámetros y propiedades de beans
con una sintaxis <strong>mucho más limpia</strong> que los scriptlets.
Todas las expresiones tienen la forma <code>\${...}</code>.</p>


<%-- ═══════════════════════════════════════ Parámetros URL ════════ --%>
<h2>1. Parámetros de la URL — <code>param</code></h2>
<p>Esta página se abrió con
<code>?nombre=<strong>${param.nombre}</strong>&amp;edad=<strong>${param.edad}</strong></code></p>

<pre>
\${param.nombre}          &lt;!-- req.getParameter("nombre") --&gt;
\${param.edad}
\${param['nombre']}       &lt;!-- sintaxis alternativa para nombres con guión --&gt;
</pre>

<div class="resultado">
  nombre → <strong>${param.nombre}</strong><br>
  edad   → <strong>${param.edad}</strong><br>
  param que no existe → «<strong>${param.noExiste}</strong>» (cadena vacía, no null)
</div>

<p class="nota">
  Prueba con distintos valores:
  <a href="el.jsp?nombre=Luis&edad=40">?nombre=Luis&amp;edad=40</a> &nbsp;|&nbsp;
  <a href="el.jsp">sin parámetros</a>
</p>


<%-- ════════════════════════════════════════════ Session scope ════ --%>
<h2>2. Sesión — <code>sessionScope</code></h2>

<pre>
\${sessionScope.usuario}
\${sessionScope['ultimaVisita']}
</pre>

<div class="resultado">
  usuario      → <strong>${sessionScope.usuario}</strong><br>
  ultimaVisita → <strong>${sessionScope.ultimaVisita}</strong><br>
  <em>(visita <a href="objetos-implicitos.jsp">objetos-implicitos.jsp</a> primero para cargar estos valores)</em>
</div>


<%-- ═══════════════════════════════════════════ Header scope ══════ --%>
<h2>3. Cabeceras HTTP — <code>header</code></h2>

<pre>
\${header['User-Agent']}
\${header['Accept-Language']}
</pre>

<div class="resultado">
  User-Agent       → <strong>${header['User-Agent']}</strong><br>
  Accept-Language  → <strong>${header['Accept-Language']}</strong>
</div>


<%-- ════════════════════════════════════════════ Operadores ══════ --%>
<h2>4. Operadores</h2>

<table>
<tr><th>Expresión EL</th><th>Resultado</th><th>Equivalente</th></tr>
<tr><td><code>\${3 + 4}</code></td>          <td>${3 + 4}</td>          <td>aritmético</td></tr>
<tr><td><code>\${10 div 3}</code></td>       <td>${10 div 3}</td>       <td>división</td></tr>
<tr><td><code>\${10 mod 3}</code></td>       <td>${10 mod 3}</td>       <td>módulo</td></tr>
<tr><td><code>\${3 > 2}</code></td>          <td>${3 > 2}</td>          <td>comparación</td></tr>
<tr><td><code>\${3 gt 2}</code></td>         <td>${3 gt 2}</td>         <td>gt = &gt;</td></tr>
<tr><td><code>\${true and false}</code></td> <td>${true and false}</td> <td>lógico and</td></tr>
<tr><td><code>\${not true}</code></td>       <td>${not true}</td>       <td>negación</td></tr>
<tr><td><code>\${empty param.nombre}</code></td>
    <td>${empty param.nombre}</td>
    <td>true si null o vacío</td></tr>
<tr><td><code>\${param.nombre eq 'Ana'}</code></td>
    <td>${param.nombre eq 'Ana'}</td>
    <td>eq = ==</td></tr>
</table>


<%-- ══════════════════════════════════════ Operador condicional ════ --%>
<h2>5. Operador condicional</h2>

<pre>
\${empty param.nombre ? 'Anonimo' : param.nombre}
</pre>

<div class="resultado">
  Bienvenido, <strong>${empty param.nombre ? 'Anonimo' : param.nombre}</strong>
</div>

<div class="nota">
  <strong>EL vs Scriptlet</strong><br>
  Scriptlet: <code>&lt;%= request.getParameter("nombre") %&gt;</code><br>
  EL:        <code>\${param.nombre}</code><br>
  EL es más conciso, no necesita casting y devuelve "" en vez de null.
</div>

<p><a href="/">← Inicio</a> &nbsp;|&nbsp;
   <a href="objetos-implicitos.jsp">← Objetos implícitos</a> &nbsp;|&nbsp;
   <a href="datos">Siguiente: JSTL core →</a></p>
</body>
</html>
