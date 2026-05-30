<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Datos individuales — Servlet → JSP</title>
<style>
body{font-family:monospace;max-width:900px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
.grid{display:grid;grid-template-columns:1fr 1fr;gap:1em;margin:.6em 0}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.85em;line-height:1.7;margin:0}
.out{background:#181825;border-left:3px solid #a6e3a1;padding:.8em 1em;
     border-radius:0 4px 4px 0;font-size:.85em;line-height:1.9;margin:0}
.label{font-size:.78em;color:#585b70;margin-bottom:.2em}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;font-size:.88em}
th{background:#313244;color:#cba6f7}
td:first-child{color:#89b4fa;white-space:nowrap}
td:nth-child(2){color:#fab387}
.nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}
.nav a{color:#a6adc8;text-decoration:none;margin-right:1em}
.nav a:hover{color:#cdd6f4}
.nav .active{color:#89b4fa;font-weight:bold}
</style>
</head>
<body>

<div class="nav">
  <a href="/">Inicio</a>
  <a href="datos?vista=individual" class="active">Individuales</a>
  <a href="datos?vista=lista">Listas</a>
  <a href="datos?vista=bean">Beans</a>
</div>

<h1>Paso de datos individuales: Servlet → JSP</h1>
<p>El servlet usa <code>req.setAttribute("clave", valor)</code> y
la JSP lo lee con EL <code>\${clave}</code>.
El tipo Java original se conserva y EL lo convierte automáticamente.</p>

<h2>Datos recibidos del servlet</h2>
<table>
<tr><th>Clave</th><th>Tipo Java</th><th>Valor en EL</th><th>Cómo se envió</th></tr>
<tr>
  <td><code>texto</code></td>
  <td>String</td>
  <td><strong>${texto}</strong></td>
  <td><code>req.setAttribute("texto", "Hola...")</code></td>
</tr>
<tr>
  <td><code>entero</code></td>
  <td>int / Integer</td>
  <td><strong>${entero}</strong></td>
  <td><code>req.setAttribute("entero", 42)</code></td>
</tr>
<tr>
  <td><code>decimal</code></td>
  <td>double / Double</td>
  <td><strong>${decimal}</strong></td>
  <td><code>req.setAttribute("decimal", 3.14159)</code></td>
</tr>
<tr>
  <td><code>activo</code></td>
  <td>boolean / Boolean</td>
  <td><strong>${activo}</strong></td>
  <td><code>req.setAttribute("activo", true)</code></td>
</tr>
<tr>
  <td><code>ahora</code></td>
  <td>java.util.Date</td>
  <td><fmt:formatDate value="${ahora}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
  <td><code>req.setAttribute("ahora", new Date())</code></td>
</tr>
<tr>
  <td><code>nulo</code></td>
  <td>null</td>
  <td>«<strong>${nulo}</strong>» → cadena vacía</td>
  <td><code>req.setAttribute("nulo", null)</code></td>
</tr>
</table>

<h2>Código del servlet</h2>
<div class="grid">
<div>
<div class="label">DatosServlet.java</div>
<pre>
req.setAttribute("texto",   "Hola desde el servlet");
req.setAttribute("entero",  42);
req.setAttribute("decimal", 3.14159);
req.setAttribute("activo",  true);
req.setAttribute("ahora",   new Date());
req.setAttribute("nulo",    null);

req.getRequestDispatcher("/individual.jsp")
   .forward(req, resp);
</pre>
</div>
<div>
<div class="label">individual.jsp — lectura con EL</div>
<pre>
\${texto}    → cadena directa
\${entero}   → número sin cast
\${decimal}  → número sin cast
\${activo}   → boolean

&lt;fmt:formatDate value="\${ahora}"
    pattern="dd/MM/yyyy HH:mm:ss"/&gt;

\${empty nulo ? 'no hay dato' : nulo}
</pre>
</div>
</div>

<h2>Comportamiento de EL con tipos</h2>
<div class="grid">
<div>
<div class="label">Código JSP</div>
<pre>
\${entero + 8}
\${decimal * 2}
\${activo ? 'Sí' : 'No'}
\${empty nulo}
\${empty texto}
\${texto.length()}
</pre>
</div>
<div>
<div class="label">Resultado renderizado</div>
<div class="out">
  ${entero + 8}<br>
  ${decimal * 2}<br>
  ${activo ? 'Sí' : 'No'}<br>
  ${empty nulo}<br>
  ${empty texto}<br>
  ${texto.length()}
</div>
</div>
</div>

<h2>Protección ante null con <code>c:if</code> y <code>empty</code></h2>
<div class="grid">
<div>
<div class="label">Código JSP</div>
<pre>
&lt;c:if test="\${not empty texto}"&gt;
  Texto recibido: \${texto}
&lt;/c:if&gt;

&lt;c:out value="\${nulo}"
       default="sin dato"/&gt;
</pre>
</div>
<div>
<div class="label">Resultado renderizado</div>
<div class="out">
  <c:if test="${not empty texto}">
    Texto recibido: ${texto}<br>
  </c:if>
  <c:out value="${nulo}" default="sin dato"/>
</div>
</div>
</div>

<p style="margin-top:2em">
  <a href="datos?vista=lista">Siguiente: Listas →</a>
</p>
</body>
</html>
