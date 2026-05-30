<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
    Componente reutilizable: tarjeta de contenido.
    Parámetros recibidos vía <jsp:param>:
      - titulo    : título de la tarjeta
      - texto     : cuerpo de la tarjeta
      - accentColor : color hexadecimal del borde superior (defecto: #89b4fa)
--%>
<%
    String titulo  = request.getParameter("titulo");
    String texto   = request.getParameter("texto");
    String accent  = request.getParameter("accentColor");
    if (titulo == null) titulo  = "Sin título";
    if (texto  == null) texto   = "";
    if (accent == null) accent  = "#89b4fa";
%>
<div style="background:#313244;border-radius:6px;padding:1em 1.4em;margin:.5em 0;
            border-top:3px solid <%= accent %>">
  <h3 style="margin:0 0 .4em;color:<%= accent %>;font-size:1em"><%= titulo %></h3>
  <p style="margin:0;color:#a6adc8;font-size:.9em;line-height:1.5"><%= texto %></p>
</div>
