package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Patrón 1 — Coincidencia exacta: url-pattern = "/exacto"
 *
 * Solo responde a GET /exacto.
 * Cualquier variación (con o sin barra final, con sub-ruta) devuelve 404
 * o cae en otro servlet:
 *   /exacto        → este servlet
 *   /exacto/       → DefaultServlet (no hay coincidencia exacta)
 *   /exacto/algo   → DefaultServlet
 *   /exactos       → DefaultServlet
 */
public class ExactoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Coincidencia exacta");
        Html.nav(out);

        out.println("<h1><span class='badge b1'>1</span> Coincidencia exacta — <code>/exacto</code></h1>");
        out.println("<p class='sub'>El contenedor solo invoca este servlet cuando la URI es <em>exactamente</em> " +
                "<code>/exacto</code>.</p>");

        out.println("<h2>Valores de la petición</h2>");
        Html.tablaInfo(out, req);

        out.println("<h2>Comportamiento de variantes</h2>");
        out.println("<table>");
        out.println("<tr><th>URL</th><th>¿Llega aquí?</th><th>¿Quién responde?</th></tr>");
        out.println("<tr><td><a href='/exacto'><code>/exacto</code></a></td>" +
                "<td class='ok'>Sí</td><td>ExactoServlet</td></tr>");
        out.println("<tr><td><a href='/exacto/'><code>/exacto/</code></a></td>" +
                "<td class='ko'>No</td><td>DefaultServlet</td></tr>");
        out.println("<tr><td><a href='/exacto/algo'><code>/exacto/algo</code></a></td>" +
                "<td class='ko'>No</td><td>DefaultServlet</td></tr>");
        out.println("<tr><td><a href='/exactos'><code>/exactos</code></a></td>" +
                "<td class='ko'>No</td><td>DefaultServlet</td></tr>");
        out.println("<tr><td><a href='/exacto?param=1'><code>/exacto?param=1</code></a></td>" +
                "<td class='ok'>Sí</td><td>ExactoServlet (query string no afecta)</td></tr>");
        out.println("</table>");

        out.println("<h2>En web.xml</h2>");
        out.println("<pre>");
        out.println("&lt;servlet-mapping&gt;");
        out.println("    &lt;servlet-name&gt;exacto&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/exacto&lt;/url-pattern&gt;");
        out.println("&lt;/servlet-mapping&gt;");
        out.println("</pre>");
        out.println("<p>O con anotación: <code>@WebServlet(\"/exacto\")</code></p>");

        out.println("<h2>Casos de uso</h2>");
        out.println("<ul>");
        out.println("<li>Rutas únicas: <code>/login</code>, <code>/logout</code>, <code>/health</code></li>");
        out.println("<li>Endpoints de API con URL fija: <code>/api/version</code></li>");
        out.println("<li>Páginas que no tienen sub-rutas ni parámetros de path</li>");
        out.println("</ul>");

        out.println("<p><a href='/'>← Inicio</a> &nbsp;|&nbsp; <a href='/patrones'>Referencia</a></p>");
        Html.pie(out);
    }
}
