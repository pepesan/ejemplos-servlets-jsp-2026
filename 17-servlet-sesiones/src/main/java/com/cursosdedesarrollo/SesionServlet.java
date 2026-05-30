package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;

/**
 * Muestra toda la información de la sesión HTTP actual y permite
 * crear atributos o invalidarla.
 *
 * GET /sesion                    → inspecciona la sesión actual
 * GET /sesion?accion=crear       → crea la sesión con un atributo de prueba
 * GET /sesion?accion=invalidar   → destruye la sesión
 */
public class SesionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String accion = req.getParameter("accion");

        if ("invalidar".equals(accion)) {
            HttpSession s = req.getSession(false);
            if (s != null) s.invalidate();
            resp.sendRedirect("/sesion");
            return;
        }
        if ("crear".equals(accion)) {
            HttpSession s = req.getSession(true);
            s.setAttribute("mensaje",   "Atributo de prueba");
            s.setAttribute("creadaEn",  new Date().toString());
            resp.sendRedirect("/sesion");
            return;
        }

        PrintWriter out = resp.getWriter();
        Html.cabecera(out, "Sesión HTTP");
        Html.nav(out, "sesion");

        out.println("<h1>Sesión HTTP</h1>");
        out.println("<p class='sub'>La sesión identifica a un usuario concreto durante varias peticiones "
                + "sin necesidad de que éste envíe credenciales en cada una.</p>");

        // getSession(false) → no crea sesión si no existe
        HttpSession sesion = req.getSession(false);

        if (sesion == null) {
            out.println("<p class='nota ko'>No hay sesión activa para este navegador.</p>");
            out.println("<p><a href='/sesion?accion=crear'>Crear sesión con atributos</a></p>");
        } else {
            out.println("<h2>Datos de la sesión</h2>");
            out.println("<table>");
            fila(out, "Session ID",        sesion.getId());
            fila(out, "isNew()",           String.valueOf(sesion.isNew()));
            fila(out, "Creada",            new Date(sesion.getCreationTime()).toString());
            fila(out, "Último acceso",     new Date(sesion.getLastAccessedTime()).toString());
            fila(out, "Max. inactividad",  sesion.getMaxInactiveInterval() + " s ("
                    + sesion.getMaxInactiveInterval() / 60 + " min)");
            out.println("</table>");

            out.println("<h2>Atributos almacenados</h2>");
            out.println("<table>");
            out.println("<tr><th>Clave</th><th>Valor</th></tr>");
            boolean hayAtributos = false;
            for (String nombre : Collections.list(sesion.getAttributeNames())) {
                hayAtributos = true;
                out.println("<tr><td><code>" + Html.esc(nombre) + "</code></td>"
                        + "<td>" + Html.esc(String.valueOf(sesion.getAttribute(nombre))) + "</td></tr>");
            }
            if (!hayAtributos) {
                out.println("<tr><td colspan='2'><em>Sin atributos</em></td></tr>");
            }
            out.println("</table>");

            out.println("<p>");
            out.println("<a href='/sesion?accion=crear'>Añadir atributo de prueba</a> &nbsp;|&nbsp;");
            out.println("<a href='/sesion?accion=invalidar' class='ko'>Invalidar sesión</a>");
            out.println("</p>");
        }

        out.println("<h2>API de sesión</h2>");
        out.println("<pre>");
        out.println("// Obtener o crear sesión");
        out.println("HttpSession sesion = req.getSession(true);   // crea si no existe");
        out.println("HttpSession sesion = req.getSession(false);  // null si no existe");
        out.println("");
        out.println("// Guardar y leer atributos");
        out.println("sesion.setAttribute(\"usuario\", \"Ana\");");
        out.println("String usuario = (String) sesion.getAttribute(\"usuario\");");
        out.println("");
        out.println("// Ciclo de vida");
        out.println("sesion.setMaxInactiveInterval(1800); // 30 min");
        out.println("sesion.invalidate();                 // destruir");
        out.println("sesion.getId();                      // JSESSIONID");
        out.println("sesion.isNew();                      // primera petición");
        out.println("</pre>");

        out.println("<h2>Sesión vs Cookie</h2>");
        out.println("<table>");
        out.println("<tr><th></th><th>Cookie</th><th>HttpSession</th></tr>");
        out.println("<tr><td>Dónde se guarda</td><td>Navegador del cliente</td><td>Memoria del servidor</td></tr>");
        out.println("<tr><td>Tamaño máximo</td><td>~4 KB por cookie</td><td>Sin límite práctico</td></tr>");
        out.println("<tr><td>Seguridad</td><td>El cliente puede leerla/modificarla</td><td>El cliente solo ve el ID</td></tr>");
        out.println("<tr><td>Persistencia</td><td>Configurable (Max-Age)</td><td>Hasta invalidate() o timeout</td></tr>");
        out.println("<tr><td>Implementación</td><td>resp.addCookie(c)</td><td>req.getSession(true)</td></tr>");
        out.println("</table>");

        Html.pie(out);
    }

    private static void fila(PrintWriter out, String clave, String valor) {
        out.println("<tr><td><code>" + Html.esc(clave) + "</code></td>"
                + "<td>" + Html.esc(valor) + "</td></tr>");
    }
}
