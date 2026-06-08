package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Patrón 3 — Extensión: url-pattern = "*.do"
 *
 * Se activa para cualquier URL cuyo último segmento termine en ".do",
 * sin importar el path anterior:
 *   /listar.do             → acción "listar"
 *   /pedido/guardar.do     → acción "guardar"
 *   /admin/usuario/crear.do → acción "crear"
 *
 * Importante:
 *   - getServletPath() devuelve la URI completa (ej. "/pedido/guardar.do")
 *   - getPathInfo() siempre es null en extensión
 *   - La extensión NO puede combinarse con path: "/*.do" es inválido en la spec
 *
 * Este es el patrón que usa Struts 1.x (ActionServlet con *.do).
 */
public class ExtensionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String accion = extraerAccion(req.getServletPath());

        Html.cabecera(out, "Extensión *.do");
        Html.nav(out);

        out.println("<h1><span class='badge b3'>3</span> Extensión — <code>*.do</code></h1>");
        out.println("<p class='sub'>Captura cualquier URL que termine en <code>.do</code>, " +
                "independientemente del path anterior.</p>");

        out.println("<div class='nota'>");
        out.println("Acción extraída de <code>getServletPath()</code>: " +
                "<strong>" + Html.esc(accion.isEmpty() ? "(ninguna)" : accion) + "</strong>");
        out.println("</div>");

        out.println("<h2>Valores de la petición</h2>");
        Html.tablaInfo(out, req);

        out.println("<h2>URLs que llegan a este servlet</h2>");
        out.println("<table>");
        out.println("<tr><th>URL</th><th>servletPath</th><th>pathInfo</th><th>Acción extraída</th></tr>");
        String[][] demos = {
            {"/listar.do",            "/listar.do",            "null", "listar"},
            {"/crear.do",             "/crear.do",             "null", "crear"},
            {"/pedido/guardar.do",    "/pedido/guardar.do",    "null", "guardar"},
            {"/admin/user/editar.do", "/admin/user/editar.do", "null", "editar"},
        };
        for (String[] d : demos) {
            out.println("<tr>");
            out.println("<td><a href='" + d[0] + "'><code>" + Html.esc(d[0]) + "</code></a></td>");
            out.println("<td><code>" + Html.esc(d[1]) + "</code></td>");
            out.println("<td><span class='ko'>" + d[2] + "</span></td>");
            out.println("<td><strong>" + Html.esc(d[3]) + "</strong></td>");
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<h2>URLs que NO llegan aquí</h2>");
        out.println("<table>");
        out.println("<tr><th>URL</th><th>¿Dónde va?</th><th>Motivo</th></tr>");
        out.println("<tr><td><a href='/listar'><code>/listar</code></a></td>" +
                "<td>DefaultServlet</td><td>Sin extensión registrada</td></tr>");
        out.println("<tr><td><a href='/archivo.doc'><code>/archivo.doc</code></a></td>" +
                "<td>DefaultServlet</td><td>Extensión <code>.doc</code> no registrada</td></tr>");
        out.println("</table>");

        out.println("<h2>&#9888; Colisión: prefijo vs extensión — prueba en vivo</h2>");
        out.println("<p>¿Adónde va <a href='/catalogo/listar.do'><code>/catalogo/listar.do</code></a>? " +
                "Termina en <code>.do</code>, pero también empieza por <code>/catalogo/</code>.</p>");
        out.println("<p><strong>Gana CatalogoServlet</strong> (<code>/catalogo/*</code>), " +
                "porque prefijo (prioridad 2ª) gana sobre extensión (3ª). " +
                "Pulsa el enlace para comprobarlo en vivo.</p>");
        out.println("<table>");
        out.println("<tr><th>URL</th><th>Patrones candidatos</th><th>Gana</th><th>Por qué</th></tr>");
        out.println("<tr>");
        out.println("<td><a href='/catalogo/listar.do'><code>/catalogo/listar.do</code></a></td>");
        out.println("<td><code>/catalogo/*</code> (prefijo) y <code>*.do</code> (extensión)</td>");
        out.println("<td class='ok'>CatalogoServlet</td>");
        out.println("<td>Prefijo (2ª) &gt; extensión (3ª)</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td><a href='/pedido/guardar.do'><code>/pedido/guardar.do</code></a></td>");
        out.println("<td><code>*.do</code> (extensión). <code>/catalogo/*</code> no coincide.</td>");
        out.println("<td class='ok'>ExtensionServlet</td>");
        out.println("<td>No hay prefijo que coincida → cae en extensión</td>");
        out.println("</tr>");
        out.println("</table>");

        out.println("<h2>Limitación importante de la spec</h2>");
        out.println("<pre>");
        out.println("VÁLIDO:   *.do        (extensión pura)");
        out.println("INVÁLIDO: /*.do       (no se puede combinar prefijo / con extensión)");
        out.println("INVÁLIDO: /api/*.do   (ídem)");
        out.println("");
        out.println("Si necesitas 'solo las .do bajo /api', usa un filtro que compruebe");
        out.println("la URI, o un segundo servlet con url-pattern=/api/* y lógica propia.");
        out.println("</pre>");

        out.println("<h2>Cómo extrae Struts 1.x la acción</h2>");
        out.println("<pre>");
        out.println("// ActionServlet recibe /listar.do");
        out.println("String servletPath = req.getServletPath(); // \"/listar.do\"");
        out.println("String accion = extraerAccion(servletPath); // \"listar\"");
        out.println("// Busca en struts-config.xml el &lt;action path=\"/listar\"&gt;");
        out.println("");
        out.println("static String extraerAccion(String servletPath) {");
        out.println("    int punto = servletPath.lastIndexOf('.');");
        out.println("    int barra = servletPath.lastIndexOf('/');");
        out.println("    if (punto &lt; 0) return servletPath;");
        out.println("    return servletPath.substring(barra + 1, punto);");
        out.println("}");
        out.println("</pre>");

        out.println("<p><a href='/'>← Inicio</a> &nbsp;|&nbsp; <a href='/patrones'>Referencia</a></p>");
        Html.pie(out);
    }

    static String extraerAccion(String servletPath) {
        if (servletPath == null || servletPath.isEmpty()) return "";
        int punto = servletPath.lastIndexOf('.');
        int barra = servletPath.lastIndexOf('/');
        if (punto < 0) return servletPath.substring(barra + 1);
        return servletPath.substring(barra + 1, punto);
    }
}
