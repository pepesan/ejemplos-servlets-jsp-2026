package com.cursosdedesarrollo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet sin loadOnStartup (comportamiento por defecto: inicialización perezosa).
 *
 * init() se llama en la primera petición, no al arrancar el servidor.
 * Comparar con LoadOnStartupServlet para ver la diferencia de tiempos.
 */
@WebServlet(urlPatterns = "/perezoso", loadOnStartup = 0)
public class LazyServlet extends HttpServlet {

    private long initMs;
    private long primeraMs = -1;
    private int  numeroPeticion;

    @Override
    public void init() {
        initMs = System.currentTimeMillis();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        numeroPeticion++;
        if (primeraMs == -1) primeraMs = System.currentTimeMillis();
        long ahoraMs = System.currentTimeMillis();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Sin loadOnStartup – Inicialización perezosa");
        Html.nav(out);

        out.println("<h1>Sin loadOnStartup — Inicialización perezosa</h1>");
        out.println("<p class='sub'>Sin <code>loadOnStartup</code>, <code>init()</code> " +
                "se ejecuta al recibir la primera petición. " +
                "Es el comportamiento por defecto.</p>");

        out.println("<h2>Tiempos registrados</h2>");
        out.println("<table>");
        out.println("<tr><th>Evento</th><th>Marca de tiempo</th><th>Diferencia vs init()</th></tr>");
        out.println("<tr><td><code>init()</code> ejecutado</td>" +
                "<td><code>" + LoadOnStartupServlet.fmt(initMs) + "</code></td><td>—</td></tr>");
        out.println("<tr><td>Primera petición recibida</td>" +
                "<td><code>" + LoadOnStartupServlet.fmt(primeraMs) + "</code></td>" +
                "<td>" + LoadOnStartupServlet.diferencia(initMs, primeraMs) + "</td></tr>");
        out.println("<tr><td>Esta petición (#" + numeroPeticion + ")</td>" +
                "<td><code>" + LoadOnStartupServlet.fmt(ahoraMs) + "</code></td>" +
                "<td>" + LoadOnStartupServlet.diferencia(initMs, ahoraMs) + "</td></tr>");
        out.println("</table>");
        out.println("<p class='nota'>Aquí <code>init()</code> y la primera petición " +
                "tienen tiempos casi idénticos (milisegundos de diferencia), " +
                "porque la inicialización ocurre <em>dentro</em> del procesamiento " +
                "de la primera petición.</p>");

        out.println("<h2>Comparación con loadOnStartup</h2>");
        out.println("<table>");
        out.println("<tr><th></th><th>LazyServlet (este)</th><th>LoadOnStartupServlet</th></tr>");
        out.println("<tr><td>Declaración</td><td><code>@WebServlet(\"/perezoso\")</code></td>" +
                "<td><code>@WebServlet(..., loadOnStartup = 1)</code></td></tr>");
        out.println("<tr><td>Cuándo se llama init()</td><td>En la primera petición</td>" +
                "<td>Al arrancar el servidor</td></tr>");
        out.println("<tr><td>Diferencia init-primera petición</td><td class='ko'>≈ 0 ms</td>" +
                "<td class='ok'>Varios segundos</td></tr>");
        out.println("<tr><td>Primera petición más lenta</td><td class='ko'>Sí</td>" +
                "<td class='ok'>No (ya estaba caliente)</td></tr>");
        out.println("</table>");

        out.println("<p><a href='/precargado'>Ver demo comparativo: LoadOnStartupServlet →</a></p>");
        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }
}
