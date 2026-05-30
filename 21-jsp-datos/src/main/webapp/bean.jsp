<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>JavaBeans — Servlet → JSP</title>
<style>
body{font-family:monospace;max-width:920px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}
h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.6em}p{line-height:1.6;color:#a6adc8}
a{color:#89dceb}code{color:#a6e3a1}
.grid{display:grid;grid-template-columns:1fr 1fr;gap:1em;margin:.6em 0}
pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;
    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.85em;line-height:1.7;margin:0}
.out{background:#181825;border-left:3px solid #a6e3a1;padding:.8em 1em;
     border-radius:0 4px 4px 0;font-size:.85em;margin:0;line-height:1.8}
.label{font-size:.78em;color:#585b70;margin-bottom:.2em}
table{border-collapse:collapse;width:100%;margin:.5em 0}
td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;font-size:.88em}
th{background:#313244;color:#cba6f7;white-space:nowrap}
td:first-child{color:#89b4fa}
.ok{color:#a6e3a1}.ko{color:#f38ba8}
.nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}
.nav a{color:#a6adc8;text-decoration:none;margin-right:1em}
.nav a:hover{color:#cdd6f4}
.nav .active{color:#89b4fa;font-weight:bold}
.nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;
      color:#a6adc8;margin:.5em 0;border-left:3px solid #cba6f7}
</style>
</head>
<body>

<div class="nav">
  <a href="/">Inicio</a>
  <a href="datos?vista=individual">Individuales</a>
  <a href="datos?vista=lista">Listas</a>
  <a href="datos?vista=bean" class="active">Beans</a>
</div>

<h1>Paso de JavaBeans: Servlet → JSP</h1>
<p>El servlet instancia objetos propios y los envía como atributos del request.
EL accede a sus propiedades llamando a los <strong>getters</strong>:
<code>\${producto.nombre}</code> invoca <code>producto.getNombre()</code>.</p>

<div class="nota">
  <strong>Convención JavaBean:</strong> clase con constructor vacío + getters/setters.
  EL resuelve <code>\${bean.propiedad}</code> como <code>bean.getPropiedad()</code>
  (o <code>isPropiedad()</code> para booleanos).
</div>

<%-- ══════════════════════════════════════ Un solo bean: Producto ══ --%>
<h2>1. Un solo bean — <code>Producto</code></h2>

<div class="grid">
<div>
<div class="label">DatosServlet.java</div>
<pre>
Producto p = new Producto(
    1, "Teclado mecánico",
    89.99, "Electrónica", true
);
req.setAttribute("producto", p);
</pre>
</div>
<div>
<div class="label">bean.jsp — acceso a propiedades</div>
<pre>
\${producto.id}
\${producto.nombre}
\${producto.precio}
\${producto.categoria}
\${producto.disponible}
</pre>
</div>
</div>

<div class="out">
  id:          <strong>${producto.id}</strong><br>
  nombre:      <strong>${producto.nombre}</strong><br>
  precio:      <strong><fmt:formatNumber value="${producto.precio}" type="currency" currencySymbol="€"/></strong><br>
  categoría:   <strong>${producto.categoria}</strong><br>
  disponible:  <strong class="${producto.disponible ? 'ok' : 'ko'}">${producto.disponible ? '✓ sí' : '✗ no'}</strong>
</div>

<%-- ══════════════════════════════════════ Un solo bean: Alumno ═══ --%>
<h2>2. Propiedades calculadas — <code>Alumno</code></h2>

<div class="grid">
<div>
<div class="label">Alumno.java</div>
<pre>
public String getNombreCompleto() {
    return nombre + " " + apellidos;
}
public boolean isAprobado() {
    return nota >= 5.0;
}
</pre>
</div>
<div>
<div class="label">bean.jsp — getter calculado</div>
<pre>
\${alumno.nombreCompleto}
\${alumno.nota}
\${alumno.aprobado}

&lt;c:if test="\${alumno.aprobado}"&gt;
  ✓ Aprobado
&lt;/c:if&gt;
</pre>
</div>
</div>

<div class="out">
  nombre completo: <strong>${alumno.nombreCompleto}</strong><br>
  nota:            <strong>${alumno.nota}</strong><br>
  aprobado:        <strong class="${alumno.aprobado ? 'ok' : 'ko'}">
    <c:if test="${alumno.aprobado}">✓ sí</c:if>
    <c:if test="${!alumno.aprobado}">✗ no</c:if>
  </strong>
</div>

<%-- ═══════════════════════════════════ Lista de Productos ════════ --%>
<h2>3. Lista de beans — <code>List&lt;Producto&gt;</code></h2>

<div class="grid">
<div>
<div class="label">DatosServlet.java</div>
<pre>
List&lt;Producto&gt; productos = Arrays.asList(
    new Producto(1,"Teclado",89.99,...),
    new Producto(2,"Ratón",34.50,...),
    ...
);
req.setAttribute("productos", productos);
</pre>
</div>
<div>
<div class="label">bean.jsp — tabla con c:forEach</div>
<pre>
&lt;c:forEach var="p" items="\${productos}"&gt;
  \${p.id}
  \${p.nombre}
  \${p.precio}
  \${p.categoria}
  \${p.disponible}
&lt;/c:forEach&gt;
</pre>
</div>
</div>

<table>
  <tr><th>ID</th><th>Nombre</th><th>Precio</th><th>Categoría</th><th>Stock</th></tr>
  <c:forEach var="p" items="${productos}">
  <tr>
    <td>${p.id}</td>
    <td>${p.nombre}</td>
    <td><fmt:formatNumber value="${p.precio}" type="currency" currencySymbol="€"/></td>
    <td>${p.categoria}</td>
    <td class="${p.disponible ? 'ok' : 'ko'}">${p.disponible ? '✓' : '✗'}</td>
  </tr>
  </c:forEach>
</table>

<%-- ════════════════════════════════════ Lista de Alumnos ═════════ --%>
<h2>4. Lista de beans con lógica — <code>List&lt;Alumno&gt;</code></h2>

<table>
  <tr><th>Nombre completo</th><th>Nota</th><th>Resultado</th><th>Activo</th></tr>
  <c:forEach var="a" items="${alumnos}">
  <tr>
    <td>${a.nombreCompleto}</td>
    <td>${a.nota}</td>
    <td class="${a.aprobado ? 'ok' : 'ko'}">
      <c:choose>
        <c:when test="${a.aprobado}">✓ Aprobado</c:when>
        <c:otherwise>✗ Suspenso</c:otherwise>
      </c:choose>
    </td>
    <td>${a.activo ? 'Sí' : 'No'}</td>
  </tr>
  </c:forEach>
</table>

<%-- ═════════════════════════════════════ Filtrado con c:if ══════ --%>
<h2>5. Filtrado en la JSP — solo productos disponibles</h2>

<div class="grid">
<div>
<div class="label">bean.jsp</div>
<pre>
&lt;c:forEach var="p" items="\${productos}"&gt;
  &lt;c:if test="\${p.disponible}"&gt;
    \${p.nombre}
  &lt;/c:if&gt;
&lt;/c:forEach&gt;
</pre>
</div>
<div>
<div class="label">Resultado — solo con stock</div>
<div class="out">
  <c:forEach var="p" items="${productos}">
    <c:if test="${p.disponible}">
      <span class="ok">✓</span> ${p.nombre}<br>
    </c:if>
  </c:forEach>
</div>
</div>
</div>

<p style="margin-top:2em">
  <a href="datos?vista=lista">← Listas</a> &nbsp;|&nbsp;
  <a href="/">Inicio</a>
</p>
</body>
</html>
