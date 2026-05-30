package com.cursosdedesarrollo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Demuestra el ciclo de vida completo de las cookies HTTP.
 *
 * GET /cookies                      → lista las cookies presentes en la petición
 * GET /cookies?accion=session       → crea cookie de sesión (sin Max-Age)
 * GET /cookies?accion=persistente   → crea cookie persistente (1 día)
 * GET /cookies?accion=segura        → crea cookie HttpOnly + Path=/cookies
 * GET /cookies?accion=borrar&nombre=X → elimina la cookie X (Max-Age=0)
 * GET /cookies?accion=borrar-todas  → elimina todas las cookies conocidas
 */
public class CookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String accion = nvl(req.getParameter("accion"));

        switch (accion) {
            case "session":
                resp.addCookie(crearSession("demo-session", "valor-de-sesion"));
                resp.sendRedirect("/cookies");
                return;
            case "persistente":
                resp.addCookie(crearPersistente("demo-persistente", "valor-persistente", 1));
                resp.sendRedirect("/cookies");
                return;
            case "segura":
                resp.addCookie(crearSegura("demo-segura", "valor-seguro"));
                resp.sendRedirect("/cookies");
                return;
            case "borrar":
                String nombre = nvl(req.getParameter("nombre"));
                if (!nombre.isEmpty()) {
                    resp.addCookie(crearEliminar(nombre));
                }
                resp.sendRedirect("/cookies");
                return;
            case "borrar-todas":
                Cookie[] todas = req.getCookies();
                if (todas != null) {
                    for (Cookie c : todas) {
                        resp.addCookie(crearEliminar(c.getName()));
                    }
                }
                resp.sendRedirect("/cookies");
                return;
        }

        // Vista principal: muestra las cookies actuales
        PrintWriter out = resp.getWriter();
        Html.cabecera(out, "Cookies HTTP");
        Html.nav(out);

        out.println("<h1>Cookies HTTP</h1>");
        out.println("<p class='sub'>Pequeños fragmentos de texto que el servidor envía al navegador "
                + "y éste devuelve en cada petición siguiente.</p>");

        // ── Cookies actuales ──────────────────────────────────────────────────
        out.println("<h2>Cookies presentes en esta petición</h2>");
        Cookie[] cookies = req.getCookies();
        if (cookies == null || cookies.length == 0) {
            out.println("<p class='nota'>No hay cookies. Usa los botones de abajo para crear alguna.</p>");
        } else {
            out.println("<table>");
            out.println("<tr><th>Nombre</th><th>Valor</th><th>Path</th>"
                    + "<th>Max-Age</th><th>HttpOnly</th><th>Secure</th><th>Acción</th></tr>");
            for (Cookie c : cookies) {
                out.println("<tr>");
                out.println("<td><code>" + Html.esc(c.getName()) + "</code></td>");
                out.println("<td>" + Html.esc(c.getValue()) + "</td>");
                String path = c.getPath();
                out.println("<td>" + Html.esc(path != null ? path : "(no establecido)") + "</td>");
                out.println("<td>" + (c.getMaxAge() == -1 ? "sesión" : c.getMaxAge() + " s") + "</td>");
                out.println("<td class='" + (c.isHttpOnly() ? "ok" : "ko") + "'>"
                        + (c.isHttpOnly() ? "sí" : "no") + "</td>");
                out.println("<td class='" + (c.getSecure() ? "ok" : "ko") + "'>"
                        + (c.getSecure() ? "sí" : "no") + "</td>");
                out.println("<td><a href='/cookies?accion=borrar&nombre="
                        + Html.esc(c.getName()) + "'>Borrar</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<p><a href='/cookies?accion=borrar-todas'>Borrar todas</a></p>");
        }

        // ── Acciones ──────────────────────────────────────────────────────────
        out.println("<h2>Crear cookies</h2>");
        out.println("<table>");
        out.println("<tr><th>Tipo</th><th>Código</th><th>Acción</th></tr>");

        out.println("<tr><td>Sesión<br><small>(desaparece al cerrar el navegador)</small></td>");
        out.println("<td><pre style='margin:0'>Cookie c = new Cookie(\"demo-session\", \"valor\");\n"
                + "// Sin setMaxAge → sesión\nresp.addCookie(c);</pre></td>");
        out.println("<td><a href='/cookies?accion=session'>Crear</a></td></tr>");

        out.println("<tr><td>Persistente<br><small>(1 día)</small></td>");
        out.println("<td><pre style='margin:0'>Cookie c = new Cookie(\"demo-persistente\", \"valor\");\n"
                + "c.setMaxAge(86400); // 1 día en segundos\nresp.addCookie(c);</pre></td>");
        out.println("<td><a href='/cookies?accion=persistente'>Crear</a></td></tr>");

        out.println("<tr><td>HttpOnly + Path</td>");
        out.println("<td><pre style='margin:0'>Cookie c = new Cookie(\"demo-segura\", \"valor\");\n"
                + "c.setHttpOnly(true);  // no accesible desde JS\n"
                + "c.setPath(\"/cookies\"); // solo en /cookies/*\nresp.addCookie(c);</pre></td>");
        out.println("<td><a href='/cookies?accion=segura'>Crear</a></td></tr>");

        out.println("</table>");

        out.println("<h2>Cómo funciona (cabeceras HTTP)</h2>");
        out.println("<pre>");
        out.println("// Respuesta del servidor (Set-Cookie):");
        out.println("Set-Cookie: demo-session=valor");
        out.println("Set-Cookie: demo-persistente=valor; Max-Age=86400; Expires=...");
        out.println("Set-Cookie: demo-segura=valor; Path=/cookies; HttpOnly");
        out.println("");
        out.println("// Petición del navegador (Cookie):");
        out.println("Cookie: demo-session=valor; demo-persistente=valor");
        out.println("</pre>");

        out.println("<p class='nota'>Abre las DevTools → Application → Cookies para ver todos los atributos.</p>");
        out.println("<p><a href='/tema'>Ver ejemplo práctico: preferencia de tema →</a></p>");

        Html.pie(out);
    }

    // ── Factorías de cookies (package-private para tests) ────────────────────

    static Cookie crearSession(String nombre, String valor) {
        return new Cookie(nombre, valor);
        // Sin setMaxAge → Max-Age = -1 → cookie de sesión
    }

    static Cookie crearPersistente(String nombre, String valor, int dias) {
        Cookie c = new Cookie(nombre, valor);
        c.setMaxAge(diasASegundos(dias));
        return c;
    }

    static Cookie crearSegura(String nombre, String valor) {
        Cookie c = new Cookie(nombre, valor);
        c.setHttpOnly(true);
        c.setPath("/cookies");
        return c;
    }

    static Cookie crearEliminar(String nombre) {
        Cookie c = new Cookie(nombre, "");
        c.setMaxAge(0);   // Max-Age=0 → el navegador borra la cookie inmediatamente
        c.setPath("/");
        return c;
    }

    static int diasASegundos(int dias) {
        return dias * 24 * 60 * 60;
    }

    static String nvl(String v) {
        return v == null ? "" : v.trim();
    }
}
