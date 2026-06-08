package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Explicación del patrón /* (wildcard total).
 *
 * Este servlet está mapeado a la URL exacta /comodin-total para poder
 * explicar /* sin registrarlo realmente (registrarlo rompería los demos
 * de extensión y default de este módulo).
 *
 * /* es un prefijo wildcard cuyo prefijo es la cadena vacía: coincide
 * con CUALQUIER URI. Su prioridad es la de prefijo (2ª), por lo que:
 *   - Pierde ante coincidencias exactas y prefijos más largos
 *   - Gana ante extensiones (*.do) y el servlet por defecto (/)
 */
public class ComodinTotalServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Patrón /* – wildcard total");
        Html.nav(out);

        out.println("<h1><span class='badge b2'>/*</span> Wildcard total — <code>/*</code></h1>");
        out.println("<p class='sub'><code>/*</code> es un prefijo cuyo prefijo es vacío: coincide con cualquier URI. " +
                "Gana sobre extensiones y sobre el servlet por defecto.</p>");

        out.println("<div class='nota'>");
        out.println("<strong>Por qué no está registrado en este módulo:</strong> " +
                "si registrásemos <code>/*</code>, las URLs <code>*.do</code> y las no mapeadas " +
                "irían a él en vez de a los demos de ExtensionServlet y DefaultServlet. " +
                "En su lugar, aquí se explica el comportamiento con simulaciones.");
        out.println("</div>");

        out.println("<h2>Prioridad de <code>/*</code> frente al resto</h2>");
        out.println("<table>");
        out.println("<tr><th>URL solicitada</th><th>Sin <code>/*</code></th><th>Con <code>/*</code></th><th>Motivo</th></tr>");

        String[][] filas = {
            {"/exacto",           "ExactoServlet",   "ExactoServlet",    "Exacto (prioridad 1) siempre gana"},
            {"/catalogo/1",       "CatalogoServlet", "CatalogoServlet",  "/catalogo/* es más largo que /*"},
            {"/patrones",         "ResumenServlet",  "ResumenServlet",   "Exacto siempre gana"},
            {"/listar.do",        "ExtensionServlet","&#9888; /*",       "Prefijo /* gana sobre extensión *.do"},
            {"/imagen.png",       "DefaultServlet",  "&#9888; /*",       "Prefijo /* gana sobre default /"},
            {"/no-existe/foo",    "DefaultServlet",  "&#9888; /*",       "Prefijo /* gana sobre default /"},
        };
        out.println("<tr><th>URL</th><th>Sin <code>/*</code></th><th>Con <code>/*</code></th><th>Razón</th></tr>");
        for (String[] f : filas) {
            boolean interceptado = f[2].contains("/*");
            out.println("<tr>");
            out.println("<td><code>" + Html.esc(f[0]) + "</code></td>");
            out.println("<td>" + f[1] + "</td>");
            out.println("<td class='" + (interceptado ? "warn" : "ok") + "'>" + f[2] + "</td>");
            out.println("<td>" + Html.esc(f[3]) + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<h2>Diferencia entre <code>/*</code> y <code>/</code></h2>");
        out.println("<table>");
        out.println("<tr><th>Patrón</th><th>Tipo</th><th>Prioridad</th><th>Pierde ante</th></tr>");
        out.println("<tr><td><code>/*</code></td><td>Prefijo wildcard</td><td><span class='badge b2'>2ª</span></td>" +
                "<td>Exactos y prefijos más largos</td></tr>");
        out.println("<tr><td><code>/</code></td><td>Servlet por defecto</td><td><span class='badge b4'>4ª</span></td>" +
                "<td>Todo lo anterior, incluidos <code>*.ext</code> y <code>/*</code></td></tr>");
        out.println("</table>");

        out.println("<h2>Declaración</h2>");
        out.println("<pre>");
        out.println("// web.xml:");
        out.println("&lt;servlet-mapping&gt;");
        out.println("    &lt;servlet-name&gt;comodin&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;");
        out.println("&lt;/servlet-mapping&gt;");
        out.println("");
        out.println("// @WebServlet:");
        out.println("@WebServlet(\"/*\")");
        out.println("public class MiFrontController extends HttpServlet { ... }");
        out.println("</pre>");

        out.println("<h2>Casos de uso reales</h2>");
        out.println("<ul>");
        out.println("<li><strong>Filtros</strong>: <code>&lt;filter-mapping&gt;&lt;url-pattern&gt;/*&lt;/url-pattern&gt;</code> " +
                "es lo habitual para filtros que actúan sobre todas las peticiones " +
                "(logging, charset, autenticación). En filtros <code>/*</code> es normal y correcto.</li>");
        out.println("<li><strong>Front Controller</strong>: algunos frameworks caseros mapean todo a un " +
                "único servlet con <code>/*</code> y enrutan internamente. Spring MVC lo hace " +
                "con <code>/</code> (default), no con <code>/*</code>, precisamente para no " +
                "interceptar recursos estáticos.</li>");
        out.println("<li><strong>Proxy inverso</strong>: un servlet que reenvía todo a otro servidor.</li>");
        out.println("</ul>");

        out.println("<p class='nota'>Regla práctica: en servlets usa <code>/</code> (default) en lugar de <code>/*</code> " +
                "si quieres un Front Controller, para no bloquear extensiones y recursos estáticos. " +
                "Reserva <code>/*</code> para filtros o para proxies que de verdad deben capturarlo todo.</p>");

        out.println("<p><a href='/'>← Inicio</a> &nbsp;|&nbsp; <a href='/patrones'>Referencia</a></p>");
        Html.pie(out);
    }
}
