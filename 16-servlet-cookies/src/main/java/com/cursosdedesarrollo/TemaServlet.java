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

        String temaActual = leerTema(req.getCookies());

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Preferencia de tema");
        Html.nav(out);

        out.println("<h1>Ejemplo práctico: preferencia de tema</h1>");
        out.println("<p class='sub'>La preferencia se guarda en una cookie persistente de "
                + DIAS_EXPIRA + " días.</p>");

        out.println("<h2>Tema activo: <strong>" + Html.esc(temaActual) + "</strong></h2>");

        String[] temas = {"oscuro", "claro", "sistema"};
        out.println("<form method='post' action='/tema'>");
        for (String t : temas) {
            out.println("<button type='submit' name='tema' value='" + t + "' "
                    + (t.equals(temaActual) ? "style='background:#a6e3a1;color:#1e1e2e'" : "")
                    + ">" + t + "</button> ");
        }
        out.println("</form>");

        out.println("<h2>Cookie en el navegador</h2>");
        out.println("<pre>");
        out.println("Set-Cookie: tema=" + Html.esc(temaActual)
                + "; Max-Age=" + CookieServlet.diasASegundos(DIAS_EXPIRA)
                + "; Path=/");
        out.println("</pre>");

        out.println("<h2>Código del servlet</h2>");
        out.println("<pre>");
        out.println("// Leer la preferencia guardada:");
        out.println("Cookie[] cookies = req.getCookies();");
        out.println("String tema = buscarValor(cookies, \"tema\");  // \"oscuro\" si no existe");
        out.println("");
        out.println("// Guardar la nueva preferencia:");
        out.println("Cookie c = new Cookie(\"tema\", nuevaTema);");
        out.println("c.setMaxAge(30 * 24 * 60 * 60);  // 30 días");
        out.println("c.setPath(\"/\");");
        out.println("resp.addCookie(c);");
        out.println("resp.sendRedirect(\"/tema\");  // PRG");
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
