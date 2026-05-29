package com.cursosdedesarrollo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Muestra en detalle todo lo que contiene un HttpServletRequest:
 * línea de petición, cabeceras HTTP, parámetros de query string,
 * cookies y datos de sesión.
 *
 * URL: GET /request
 * URL: GET /request?nombre=Ana&edad=30   (para ver query params)
 */
public class InspeccionRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Inspección de HttpServletRequest");

        out.println("<h1>HttpServletRequest</h1>");
        out.println("<p class='sub'>Todo lo que el servidor recibe del cliente en cada petición HTTP.</p>");

        // ── 1. Línea de petición ──────────────────────────────────────────────
        out.println("<h2>1. Línea de petición</h2>");
        out.println("<table>");
        fila(out, "Método HTTP",        req.getMethod());
        fila(out, "URI",                req.getRequestURI());
        fila(out, "URL completa",       req.getRequestURL().toString());
        fila(out, "Query string",       nvl(req.getQueryString(), "(ninguno)"));
        fila(out, "Protocolo",          req.getProtocol());
        fila(out, "Context path",       nvl(req.getContextPath(), "(raíz)"));
        fila(out, "Servlet path",       req.getServletPath());
        out.println("</table>");

        // ── 2. Parámetros (query string o body form-urlencoded) ───────────────
        out.println("<h2>2. Parámetros</h2>");
        out.println("<p class='hint'>Prueba añadiendo <code>?nombre=Ana&amp;edad=30</code> a la URL.</p>");
        out.println("<table>");
        out.println("<tr><th>Nombre</th><th>Valor(es)</th></tr>");
        for (String param : Collections.list(req.getParameterNames())) {
            String[] valores = req.getParameterValues(param);
            fila(out, param, String.join(", ", valores));
        }
        if (req.getParameterNames() == null || !req.getParameterNames().hasMoreElements()) {
            out.println("<tr><td colspan='2'><em>Sin parámetros</em></td></tr>");
        }
        out.println("</table>");

        // ── 3. Cabeceras HTTP ─────────────────────────────────────────────────
        out.println("<h2>3. Cabeceras HTTP</h2>");
        out.println("<table>");
        out.println("<tr><th>Cabecera</th><th>Valor</th></tr>");
        Enumeration<String> cabeceras = req.getHeaderNames();
        while (cabeceras != null && cabeceras.hasMoreElements()) {
            String nombre = cabeceras.nextElement();
            fila(out, nombre, req.getHeader(nombre));
        }
        out.println("</table>");

        // ── 4. Información del cliente ────────────────────────────────────────
        out.println("<h2>4. Información del cliente</h2>");
        out.println("<table>");
        fila(out, "IP remota",          req.getRemoteAddr());
        fila(out, "Host remoto",        req.getRemoteHost());
        fila(out, "Puerto remoto",      String.valueOf(req.getRemotePort()));
        fila(out, "Servidor (host)",    req.getServerName());
        fila(out, "Servidor (puerto)",  String.valueOf(req.getServerPort()));
        fila(out, "Esquema",            req.getScheme());
        fila(out, "Es HTTPS",           String.valueOf(req.isSecure()));
        fila(out, "Locale",             req.getLocale().toString());
        out.println("</table>");

        // ── 5. Cookies ────────────────────────────────────────────────────────
        out.println("<h2>5. Cookies</h2>");
        out.println("<p class='hint'>Visita <a href='/respuesta?tipo=cookie'>GET /respuesta?tipo=cookie</a> "
                + "para que el servidor establezca una cookie; luego recarga esta página.</p>");
        out.println("<table>");
        out.println("<tr><th>Nombre</th><th>Valor</th><th>Path</th><th>MaxAge</th></tr>");
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("<tr><td>" + Html.esc(c.getName()) + "</td>"
                        + "<td>" + Html.esc(c.getValue()) + "</td>"
                        + "<td>" + nvl(c.getPath(), "/") + "</td>"
                        + "<td>" + c.getMaxAge() + "</td></tr>");
            }
        } else {
            out.println("<tr><td colspan='4'><em>Sin cookies</em></td></tr>");
        }
        out.println("</table>");

        // ── 6. Sesión ─────────────────────────────────────────────────────────
        out.println("<h2>6. Sesión HTTP</h2>");
        out.println("<p class='hint'>Visita <a href='/respuesta?tipo=sesion'>GET /respuesta?tipo=sesion</a> "
                + "para guardar un valor en sesión; luego recarga esta página.</p>");
        // getSession(false) no crea sesión si no existe
        HttpSession sesion = req.getSession(false);
        out.println("<table>");
        if (sesion != null) {
            fila(out, "Session ID",         sesion.getId());
            fila(out, "Creada",             String.valueOf(sesion.getCreationTime()));
            fila(out, "Último acceso",      String.valueOf(sesion.getLastAccessedTime()));
            fila(out, "Es nueva",           String.valueOf(sesion.isNew()));
            for (String attr : Collections.list(sesion.getAttributeNames())) {
                fila(out, "Atributo: " + attr, String.valueOf(sesion.getAttribute(attr)));
            }
        } else {
            out.println("<tr><td colspan='2'><em>Sin sesión activa</em></td></tr>");
        }
        out.println("</table>");

        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    private static void fila(PrintWriter out, String nombre, String valor) {
        out.println("<tr><td><code>" + Html.esc(nombre) + "</code></td>"
                + "<td>" + Html.esc(nvl(valor, "")) + "</td></tr>");
    }

    private static String nvl(String valor, String defecto) {
        return (valor == null || valor.isEmpty()) ? defecto : valor;
    }
}
