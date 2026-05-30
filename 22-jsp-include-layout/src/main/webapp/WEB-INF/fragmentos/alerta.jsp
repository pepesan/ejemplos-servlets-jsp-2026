<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
    Componente reutilizable: caja de alerta.
    Parámetros recibidos vía <jsp:param>:
      - tipo    : "info" | "ok" | "aviso" | "error"  (defecto: "info")
      - mensaje : texto a mostrar
--%>
<%
    String tipo    = request.getParameter("tipo");
    String mensaje = request.getParameter("mensaje");
    if (tipo    == null || tipo.isEmpty())    tipo    = "info";
    if (mensaje == null || mensaje.isEmpty()) mensaje = "(sin mensaje)";

    String color;
    if      ("ok".equals(tipo))    color = "#a6e3a1";
    else if ("aviso".equals(tipo)) color = "#f9e2af";
    else if ("error".equals(tipo)) color = "#f38ba8";
    else                           color = "#89dceb";
%>
<div style="border-left:4px solid <%= color %>;background:#313244;padding:.6em 1em;
            border-radius:0 4px 4px 0;margin:.4em 0;font-size:.9em">
  <span style="color:<%= color %>;font-weight:bold;text-transform:uppercase;font-size:.8em"><%= tipo %></span>
  &nbsp; <%= mensaje %>
</div>
