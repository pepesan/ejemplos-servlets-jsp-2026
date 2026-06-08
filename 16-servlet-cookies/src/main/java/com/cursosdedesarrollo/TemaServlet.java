package com.cursosdedesarrollo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Ejemplo práctico: guarda la preferencia de tema (oscuro/claro/sistema)
 * en una cookie persistente de 30 días.
 *
 * GET  /tema          → muestra la página con el tema guardado
 * POST /tema?tema=X   → cambia el tema y redirige (PRG)
 */
public class TemaServlet extends HttpServlet {

    static final String NOMBRE_COOKIE = "tema";
    static final String TEMA_DEFECTO  = "oscuro";
    static final int    DIAS_EXPIRA   = 30;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Cookie[] cookiesEntrada = req.getCookies();
        boolean  tieneCookie    = tieneCookieTema(cookiesEntrada);
        String   temaActual     = leerTema(cookiesEntrada);

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Preferencia de tema");
        Html.nav(out);

        out.println("<h1>Ejemplo práctico: preferencia de tema</h1>");
        out.println("<p class='sub'>Caso de uso real: guardar una preferencia del usuario "
                + "sin sesión ni base de datos, solo con una cookie persistente.</p>");

        // ── Estado actual ────────────────────────────────────────────────────
        out.println("<h2>Estado actual</h2>");
        if (tieneCookie) {
            out.println("<p class='ok'>&#10003; El navegador ha enviado la cookie <code>tema="
                    + Html.esc(temaActual) + "</code>. Preferencia cargada.</p>");
        } else {
            out.println("<p class='nota'>&#9432; No hay cookie <code>tema</code>. "
                    + "Se usa el valor por defecto: <strong>" + TEMA_DEFECTO + "</strong>.</p>");
        }
        out.println("<p>Tema activo: <strong>" + Html.esc(temaActual) + "</strong></p>");

        // ── Selector ─────────────────────────────────────────────────────────
        out.println("<h2>Cambiar preferencia</h2>");
        String[] temas = {"oscuro", "claro", "sistema"};
        out.println("<form method='post' action='/tema'>");
        for (String t : temas) {
            boolean activo = t.equals(temaActual);
            out.println("<button type='submit' name='tema' value='" + t + "' "
                    + (activo ? "style='background:#a6e3a1;color:#1e1e2e'" : "")
                    + ">" + t + (activo ? " ✓" : "") + "</button> ");
        }
        out.println("</form>");
        out.println("<p class='nota'>Al pulsar un botón: POST → el servidor graba la cookie → "
                + "redirect 302 → GET (patrón PRG). Recarga la página y la cookie ya estará.</p>");

        // ── Flujo HTTP ───────────────────────────────────────────────────────
        out.println("<h2>Flujo HTTP completo</h2>");
        out.println("<pre>");
        out.println("── 1ª visita (sin cookie) ──────────────────────────────────────");
        out.println("GET /tema HTTP/1.1");
        out.println("  → No hay cabecera Cookie: tema=...");
        out.println("  → El servlet usa el valor por defecto: \"" + TEMA_DEFECTO + "\"");
        out.println("");
        out.println("── El usuario pulsa un botón ────────────────────────────────────");
        out.println("POST /tema HTTP/1.1");
        out.println("tema=claro");
        out.println("");
        out.println("  Respuesta del servidor:");
        out.println("  HTTP/1.1 302 Found");
        out.println("  Set-Cookie: tema=claro; Max-Age=" + CookieServlet.diasASegundos(DIAS_EXPIRA)
                + "; Path=/");
        out.println("  Location: /tema");
        out.println("");
        out.println("── Visitas siguientes (con cookie) ──────────────────────────────");
        out.println("GET /tema HTTP/1.1");
        out.println("Cookie: tema=claro              ← el navegador la envía solo");
        out.println("  → req.getCookies() devuelve la cookie");
        out.println("  → leerTema() extrae \"claro\" y lo usa");
        out.println("</pre>");

        // ── Cookie recibida ahora ─────────────────────────────────────────────
        out.println("<h2>Cabecera Cookie recibida en esta petición</h2>");
        if (tieneCookie) {
            out.println("<pre>Cookie: tema=" + Html.esc(temaActual) + "</pre>");
        } else {
            out.println("<pre class='nota'>(cabecera Cookie ausente — sin cookie de tema)</pre>");
        }

        // ── Código clave ─────────────────────────────────────────────────────
        out.println("<h2>Código clave</h2>");
        out.println("<pre>");
        out.println("// doGet: leer la preferencia guardada");
        out.println("String tema = leerTema(req.getCookies()); // defecto si no existe");
        out.println("");
        out.println("// doPost: guardar la nueva preferencia y redirigir (PRG)");
        out.println("Cookie c = new Cookie(\"tema\", nuevoTema);");
        out.println("c.setMaxAge(" + DIAS_EXPIRA + " * 24 * 60 * 60); // "
                + DIAS_EXPIRA + " días = " + CookieServlet.diasASegundos(DIAS_EXPIRA) + " s");
        out.println("c.setPath(\"/\");          // visible en toda la app");
        out.println("resp.addCookie(c);");
        out.println("resp.sendRedirect(\"/tema\"); // PRG: evita reenvío del POST al recargar");
        out.println("");
        out.println("// Buscar una cookie por nombre:");
        out.println("static String leerTema(Cookie[] cookies) {");
        out.println("    if (cookies == null) return TEMA_DEFECTO;");
        out.println("    for (Cookie c : cookies)");
        out.println("        if (\"tema\".equals(c.getName())) return c.getValue();");
        out.println("    return TEMA_DEFECTO;");
        out.println("}");
        out.println("</pre>");

        out.println("<p><a href='/cookies'>← Volver a inspección de cookies</a></p>");
        Html.pie(out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String nuevoTema = req.getParameter("tema");
        if (!esValido(nuevoTema)) {
            nuevoTema = TEMA_DEFECTO;
        }

        Cookie c = new Cookie(NOMBRE_COOKIE, nuevoTema);
        c.setMaxAge(CookieServlet.diasASegundos(DIAS_EXPIRA));
        c.setPath("/");
        resp.addCookie(c);

        resp.sendRedirect("/tema");
    }

    static boolean tieneCookieTema(Cookie[] cookies) {
        if (cookies == null) return false;
        for (Cookie c : cookies)
            if (NOMBRE_COOKIE.equals(c.getName())) return true;
        return false;
    }

    /** Lee el valor de la cookie de tema; devuelve el valor por defecto si no existe. */
    static String leerTema(Cookie[] cookies) {
        if (cookies == null) return TEMA_DEFECTO;
        for (Cookie c : cookies) {
            if (NOMBRE_COOKIE.equals(c.getName())) {
                return esValido(c.getValue()) ? c.getValue() : TEMA_DEFECTO;
            }
        }
        return TEMA_DEFECTO;
    }

    static boolean esValido(String tema) {
        return "oscuro".equals(tema) || "claro".equals(tema) || "sistema".equals(tema);
    }
}
