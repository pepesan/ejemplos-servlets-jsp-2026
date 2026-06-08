package com.cursosdedesarrollo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Demuestra varios urlPatterns en un mismo servlet usando @WebServlet.
 *
 * Patrones mapeados:
 *   /inicio       → alias en español
 *   /home         → alias en inglés
 *   /bienvenida   → tercer alias
 *
 * Las tres URLs llegan al mismo servlet; lo que varía es getServletPath().
 */
@WebServlet(urlPatterns = {"/inicio", "/home", "/bienvenida"})
public class AliasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String urlActual = req.getServletPath();

        Html.cabecera(out, "Varios patrones – @WebServlet");
        Html.nav(out);

        out.println("<h1>Varios urlPatterns — <code>" + Html.esc(urlActual) + "</code></h1>");
        out.println("<p class='sub'>Un servlet puede atender múltiples URLs. " +
                "La URL que usó el cliente determina <code>getServletPath()</code>.</p>");

        out.println("<div class='nota'>Has llegado desde: " +
                "<strong><code>" + Html.esc(urlActual) + "</code></strong></div>");

        out.println("<h2>Prueba las tres URLs</h2>");
        out.println("<table>");
        out.println("<tr><th>URL</th><th>getServletPath()</th><th>Estado</th></tr>");
        String[] urls = {"/inicio", "/home", "/bienvenida"};
        for (String u : urls) {
            boolean activa = u.equals(urlActual);
            out.println("<tr><td><a href='" + u + "'><code>" + Html.esc(u) + "</code></a></td>" +
                    "<td><code>" + Html.esc(u) + "</code></td>" +
                    "<td>" + (activa ? "<span class='ok'>← estás aquí</span>" : "") + "</td></tr>");
        }
        out.println("</table>");

        out.println("<h2>Declaración con @WebServlet</h2>");
        out.println("<pre>");
        out.println("// Varios patrones: usar urlPatterns (array)");
        out.println("@WebServlet(urlPatterns = {\"/inicio\", \"/home\", \"/bienvenida\"})");
        out.println("");
        out.println("// Un solo patrón: forma abreviada");
        out.println("@WebServlet(\"/inicio\")               // equivale a urlPatterns={\"/inicio\"}");
        out.println("@WebServlet(value = \"/inicio\")       // ídem con atributo explícito");
        out.println("</pre>");

        out.println("<h2>Equivalente en web.xml</h2>");
        out.println("<pre>");
        out.println("&lt;servlet&gt;");
        out.println("    &lt;servlet-name&gt;alias&lt;/servlet-name&gt;");
        out.println("    &lt;servlet-class&gt;...AliasServlet&lt;/servlet-class&gt;");
        out.println("&lt;/servlet&gt;");
        out.println("&lt;!-- Un &lt;servlet-mapping&gt; por cada patrón --&gt;");
        out.println("&lt;servlet-mapping&gt;&lt;servlet-name&gt;alias&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/inicio&lt;/url-pattern&gt;&lt;/servlet-mapping&gt;");
        out.println("&lt;servlet-mapping&gt;&lt;servlet-name&gt;alias&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/home&lt;/url-pattern&gt;&lt;/servlet-mapping&gt;");
        out.println("&lt;servlet-mapping&gt;&lt;servlet-name&gt;alias&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/bienvenida&lt;/url-pattern&gt;&lt;/servlet-mapping&gt;");
        out.println("</pre>");

        out.println("<p class='nota'>Si necesitas mezclar tipos de patrón (p.ej. " +
                "<code>/exacto</code> y <code>*.ext</code>) en un mismo servlet, " +
                "usa <code>urlPatterns = {\"/exacto\", \"*.ext\"}</code>. " +
                "El tipo de patrón de cada entrada se determina de forma independiente.</p>");

        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }
}
