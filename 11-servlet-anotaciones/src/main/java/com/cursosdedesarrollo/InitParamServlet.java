package com.cursosdedesarrollo;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Demuestra @WebInitParam: parámetros de inicialización declarados
 * en la propia anotación, sin necesidad de web.xml.
 *
 * Los init-params se leen en init() o en doGet() mediante
 * getInitParameter(nombre) y getInitParameterNames().
 * Son de solo lectura: el servlet no puede modificarlos en tiempo de ejecución.
 */
@WebServlet(
    urlPatterns = "/config",
    initParams  = {
        @WebInitParam(name = "autor",        value = "Curso de Servlets 2026"),
        @WebInitParam(name = "version",      value = "1.0"),
        @WebInitParam(name = "idioma",       value = "es"),
        @WebInitParam(name = "maxResultados", value = "50")
    }
)
public class InitParamServlet extends HttpServlet {

    private String autor;
    private String version;

    @Override
    public void init() {
        // Lectura típica en init(): se guardan en campos del servlet
        autor   = getInitParameter("autor");
        version = getInitParameter("version");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "@WebInitParam – Parámetros de inicialización");
        Html.nav(out);

        out.println("<h1>@WebInitParam — Parámetros de inicialización</h1>");
        out.println("<p class='sub'>Equivalente a <code>&lt;init-param&gt;</code> en web.xml, " +
                "pero declarado junto al servlet en el código fuente.</p>");

        // ── Parámetros configurados ────────────────────────────────────────
        out.println("<h2>Init-params de este servlet</h2>");
        out.println("<table>");
        out.println("<tr><th>Nombre</th><th>Valor (leído con getInitParameter)</th></tr>");
        Enumeration<String> nombres = getServletConfig().getInitParameterNames();
        while (nombres.hasMoreElements()) {
            String nombre = nombres.nextElement();
            out.println("<tr><td><code>" + Html.esc(nombre) + "</code></td>" +
                    "<td>" + Html.esc(getInitParameter(nombre)) + "</td></tr>");
        }
        out.println("</table>");
        out.println("<p>Campos leídos en <code>init()</code>: " +
                "autor = <strong>" + Html.esc(autor) + "</strong>, " +
                "version = <strong>" + Html.esc(version) + "</strong></p>");

        // ── Código fuente ──────────────────────────────────────────────────
        out.println("<h2>Código con @WebServlet + @WebInitParam</h2>");
        out.println("<pre>");
        out.println("@WebServlet(");
        out.println("    urlPatterns = \"/config\",");
        out.println("    initParams  = {");
        out.println("        @WebInitParam(name = \"autor\",   value = \"Curso 2026\"),");
        out.println("        @WebInitParam(name = \"version\", value = \"1.0\")");
        out.println("    }");
        out.println(")");
        out.println("public class InitParamServlet extends HttpServlet {");
        out.println("");
        out.println("    @Override");
        out.println("    public void init() {");
        out.println("        String autor = getInitParameter(\"autor\");   // \"Curso 2026\"");
        out.println("        String v     = getInitParameter(\"version\"); // \"1.0\"");
        out.println("    }");
        out.println("}");
        out.println("</pre>");

        // ── Equivalente en web.xml ─────────────────────────────────────────
        out.println("<h2>Equivalente en web.xml</h2>");
        out.println("<pre>");
        out.println("&lt;servlet&gt;");
        out.println("    &lt;servlet-name&gt;config&lt;/servlet-name&gt;");
        out.println("    &lt;servlet-class&gt;...InitParamServlet&lt;/servlet-class&gt;");
        out.println("    &lt;init-param&gt;");
        out.println("        &lt;param-name&gt;autor&lt;/param-name&gt;");
        out.println("        &lt;param-value&gt;Curso 2026&lt;/param-value&gt;");
        out.println("    &lt;/init-param&gt;");
        out.println("    &lt;init-param&gt;");
        out.println("        &lt;param-name&gt;version&lt;/param-name&gt;");
        out.println("        &lt;param-value&gt;1.0&lt;/param-value&gt;");
        out.println("    &lt;/init-param&gt;");
        out.println("&lt;/servlet&gt;");
        out.println("</pre>");

        // ── Scope: init-param vs context-param ───────────────────────────
        out.println("<h2>init-param vs context-param</h2>");
        out.println("<table>");
        out.println("<tr><th></th><th>init-param / @WebInitParam</th><th>context-param</th></tr>");
        out.println("<tr><td>Ámbito</td><td>Un servlet concreto</td><td>Toda la aplicación</td></tr>");
        out.println("<tr><td>Lectura</td><td><code>getInitParameter()</code></td>" +
                "<td><code>getServletContext().getInitParameter()</code></td></tr>");
        out.println("<tr><td>Declaración con anotación</td><td><code>@WebInitParam</code></td>" +
                "<td>Solo en web.xml (<code>&lt;context-param&gt;</code>)</td></tr>");
        out.println("<tr><td>Cuándo usarlo</td><td>Config específica del servlet (timeout, ruta, modo)</td>" +
                "<td>Config global (BBDD, URL base, feature flags)</td></tr>");
        out.println("</table>");

        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }
}
