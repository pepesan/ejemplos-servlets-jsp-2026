package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Contador de visitas por usuario almacenado en sesión.
 * Cada petición GET incrementa el contador de ESE usuario.
 * Dos navegadores distintos tienen contadores independientes.
 *
 * GET /contador → muestra y actualiza el contador
 * GET /contador?accion=reset → reinicia a 0
 */
public class ContadorServlet extends HttpServlet {

    static final String ATTR = "visitas";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = req.getSession(true);

        if ("reset".equals(req.getParameter("accion"))) {
            sesion.setAttribute(ATTR, 0);
            resp.sendRedirect("/contador");
            return;
        }

        int visitas = incrementar(sesion);

        PrintWriter out = resp.getWriter();
        Html.cabecera(out, "Contador de visitas");
        Html.nav(out, "contador");

        out.println("<h1>Contador de visitas por usuario</h1>");
        out.println("<p class='sub'>Cada petición a esta página incrementa TU contador, "
                + "guardado en sesión. Otro navegador tiene un contador distinto.</p>");

        out.println("<h2 style='font-size:3em;color:#a6e3a1;margin:.2em 0'>" + visitas + "</h2>");
        out.println("<p>petición" + (visitas == 1 ? "" : "es") + " a esta página en tu sesión.</p>");

        out.println("<p>");
        out.println("<a href='/contador'>Recargar (incrementar)</a> &nbsp;|&nbsp;");
        out.println("<a href='/contador?accion=reset'>Reiniciar contador</a> &nbsp;|&nbsp;");
        out.println("<a href='/sesion?accion=invalidar'>Invalidar sesión</a>");
        out.println("</p>");

        out.println("<h2>Código</h2>");
        out.println("<pre>");
        out.println("HttpSession sesion = req.getSession(true);");
        out.println("Integer actual = (Integer) sesion.getAttribute(\"visitas\");");
        out.println("int nuevo = (actual == null) ? 1 : actual + 1;");
        out.println("sesion.setAttribute(\"visitas\", nuevo);");
        out.println("</pre>");

        out.println("<p class='nota'>Session ID: <code>" + Html.esc(sesion.getId()) + "</code></p>");
        Html.pie(out);
    }

    /** Incrementa el contador en sesión y devuelve el nuevo valor. */
    static int incrementar(HttpSession sesion) {
        Integer actual = (Integer) sesion.getAttribute(ATTR);
        int nuevo = (actual == null) ? 1 : actual + 1;
        sesion.setAttribute(ATTR, nuevo);
        return nuevo;
    }
}
