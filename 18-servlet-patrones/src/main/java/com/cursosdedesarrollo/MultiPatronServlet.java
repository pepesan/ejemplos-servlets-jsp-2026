package com.cursosdedesarrollo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Varios patrones en un mismo servlet — declarado con @WebServlet.
 *
 * Un servlet puede atender varias URLs distintas. Útil cuando la lógica
 * es la misma pero quieres ofrecer alias (p.ej. /buscar y /search).
 *
 * URLs mapeadas:
 *   GET /buscar    → este servlet
 *   GET /search    → este servlet
 *   GET /encuentra → este servlet
 *
 * Nota: este servlet NO aparece en web.xml; está registrado solo con la
 * anotación @WebServlet para mostrar el contraste entre ambas formas.
 */
@WebServlet(urlPatterns = {"/buscar", "/search", "/encuentra"})
public class MultiPatronServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Varios patrones – @WebServlet");
        Html.nav(out);

        out.println("<h1><span class='badge b1'>multi</span> Varios patrones para un servlet</h1>");
        out.println("<p class='sub'>Un solo servlet puede responder a varias URLs independientes. " +
                "Cada una es un patrón exacto independiente (no un prefijo).</p>");

        out.println("<div class='nota'>Has llegado aquí desde: " +
                "<strong><code>" + Html.esc(req.getServletPath()) + "</code></strong></div>");

        out.println("<h2>Valores de la petición</h2>");
        Html.tablaInfo(out, req);

        out.println("<h2>Prueba las tres URLs</h2>");
        out.println("<p>Las tres llegan al mismo servlet; solo cambia <code>getServletPath()</code>:</p>");
        out.println("<table>");
        out.println("<tr><th>URL</th><th>getServletPath()</th></tr>");
        out.println("<tr><td><a href='/buscar'><code>/buscar</code></a></td><td><code>/buscar</code></td></tr>");
        out.println("<tr><td><a href='/search'><code>/search</code></a></td><td><code>/search</code></td></tr>");
        out.println("<tr><td><a href='/encuentra'><code>/encuentra</code></a></td><td><code>/encuentra</code></td></tr>");
        out.println("</table>");

        out.println("<h2>Con anotación <code>@WebServlet</code></h2>");
        out.println("<pre>");
        out.println("// Un patrón: forma abreviada");
        out.println("@WebServlet(\"/buscar\")");
        out.println("");
        out.println("// Varios patrones: obligatorio usar urlPatterns={}");
        out.println("@WebServlet(urlPatterns = {\"/buscar\", \"/search\", \"/encuentra\"})");
        out.println("public class MultiPatronServlet extends HttpServlet { ... }");
        out.println("</pre>");

        out.println("<h2>Equivalente en <code>web.xml</code></h2>");
        out.println("<pre>");
        out.println("&lt;servlet&gt;");
        out.println("    &lt;servlet-name&gt;multi&lt;/servlet-name&gt;");
        out.println("    &lt;servlet-class&gt;com.cursosdedesarrollo.MultiPatronServlet&lt;/servlet-class&gt;");
        out.println("&lt;/servlet&gt;");
        out.println("");
        out.println("&lt;!-- Un &lt;servlet-mapping&gt; por patrón --&gt;");
        out.println("&lt;servlet-mapping&gt;");
        out.println("    &lt;servlet-name&gt;multi&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/buscar&lt;/url-pattern&gt;");
        out.println("&lt;/servlet-mapping&gt;");
        out.println("&lt;servlet-mapping&gt;");
        out.println("    &lt;servlet-name&gt;multi&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/search&lt;/url-pattern&gt;");
        out.println("&lt;/servlet-mapping&gt;");
        out.println("&lt;servlet-mapping&gt;");
        out.println("    &lt;servlet-name&gt;multi&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/encuentra&lt;/url-pattern&gt;");
        out.println("&lt;/servlet-mapping&gt;");
        out.println("</pre>");

        out.println("<h2>¿Cuándo usarlo?</h2>");
        out.println("<ul>");
        out.println("<li>Alias bilingües: <code>/buscar</code> y <code>/search</code></li>");
        out.println("<li>Compatibilidad hacia atrás: la URL antigua y la nueva apuntan al mismo sitio</li>");
        out.println("<li>Extensión + ruta exacta: <code>*.action</code> y <code>/api/accion</code></li>");
        out.println("</ul>");

        out.println("<p class='nota'>Mezcla con precaución: si un servlet tiene patrones exactos " +
                "y de extensión a la vez, puede ser difícil de depurar. " +
                "Úsalo principalmente para alias exactos del mismo tipo.</p>");

        out.println("<p><a href='/'>← Inicio</a> &nbsp;|&nbsp; <a href='/patrones'>Referencia</a></p>");
        Html.pie(out);
    }
}
