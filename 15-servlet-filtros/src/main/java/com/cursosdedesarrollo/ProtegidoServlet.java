package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Recurso protegido: solo accesible si AuthFilter ha dejado pasar la petición,
 * es decir, si hay un atributo "usuario" en la sesión HTTP.
 *
 * URL: GET /protegido/area
 */
public class ProtegidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession sesion  = req.getSession(false);
        String      usuario = (sesion != null) ? (String) sesion.getAttribute("usuario") : "?";

        Html.cabecera(out, "Área protegida");
        out.println("<h1>&#128274; Área protegida</h1>");
        out.println("<p class='sub'>Has llegado aquí porque AuthFilter ha verificado tu sesión.</p>");

        out.println("<h2>Datos de sesión</h2>");
        out.println("<table>");
        out.println("<tr><th>Atributo</th><th>Valor</th></tr>");
        out.println("<tr><td><code>usuario</code></td><td class='ok'>" + Html.esc(usuario) + "</td></tr>");
        if (sesion != null) {
            out.println("<tr><td><code>session id</code></td><td>" + Html.esc(sesion.getId()) + "</td></tr>");
            out.println("<tr><td><code>creada</code></td><td>" + new java.util.Date(sesion.getCreationTime()) + "</td></tr>");
        }
        out.println("</table>");

        out.println("<h2>Cómo funciona</h2>");
        out.println("<ol>");
        out.println("<li><code>CharsetFilter</code> fija UTF-8 en la petición y respuesta.</li>");
        out.println("<li><code>LoggingFilter</code> registra la petición y mide el tiempo.</li>");
        out.println("<li><code>AuthFilter</code> comprueba <code>session.getAttribute(\"usuario\")</code>.</li>");
        out.println("<li>Si hay usuario en sesión → deja pasar → llega aquí.</li>");
        out.println("<li>Si no hay usuario → <code>resp.sendRedirect(\"/login\")</code>.</li>");
        out.println("</ol>");

        out.println("<p><a href='logout'>Cerrar sesión</a> &nbsp;|&nbsp; <a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }
}
