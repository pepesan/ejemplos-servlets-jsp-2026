package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tabla de referencia completa de patrones de URL.
 * Coincidencia exacta en /patrones.
 */
public class ResumenServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Referencia – Patrones de URL");
        Html.nav(out);

        out.println("<h1>&#128218; Referencia: Patrones de URL (Servlet Spec §12.2)</h1>");

        // ── Tipos y prioridad ─────────────────────────────────────────────────
        out.println("<h2>Tipos de patrón y prioridad de resolución</h2>");
        out.println("<table>");
        out.println("<tr><th>Prioridad</th><th>Tipo</th><th>Sintaxis</th><th>Ejemplo</th>" +
                "<th>getServletPath()</th><th>getPathInfo()</th><th>Demo</th></tr>");
        out.println("<tr>");
        out.println("<td><span class='badge b1'>1º</span></td>");
        out.println("<td>Coincidencia exacta</td>");
        out.println("<td><code>/ruta</code></td>");
        out.println("<td><code>/exacto</code></td>");
        out.println("<td><code>/exacto</code></td>");
        out.println("<td><span class='ko'>null</span></td>");
        out.println("<td><a href='/exacto'>→</a></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td><span class='badge b2'>2º</span></td>");
        out.println("<td>Prefijo wildcard (más largo primero)</td>");
        out.println("<td><code>/prefijo/*</code></td>");
        out.println("<td><code>/catalogo/*</code></td>");
        out.println("<td><code>/catalogo</code></td>");
        out.println("<td><code>/42</code>, <code>/42/editar</code>…</td>");
        out.println("<td><a href='/catalogo/'>→</a></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td><span class='badge b3'>3º</span></td>");
        out.println("<td>Extensión</td>");
        out.println("<td><code>*.ext</code></td>");
        out.println("<td><code>*.do</code></td>");
        out.println("<td><code>/listar.do</code> (path completo)</td>");
        out.println("<td><span class='ko'>null</span></td>");
        out.println("<td><a href='/listar.do'>→</a></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td><span class='badge b4'>4º</span></td>");
        out.println("<td>Por defecto</td>");
        out.println("<td><code>/</code></td>");
        out.println("<td>cualquier cosa no mapeada</td>");
        out.println("<td><code>\"\"</code> (cadena vacía)</td>");
        out.println("<td><span class='ko'>null</span> o la URI</td>");
        out.println("<td><a href='/no-mapeado'>→</a></td>");
        out.println("</tr>");
        out.println("</table>");

        out.println("<h2>Patrones adicionales</h2>");
        out.println("<table>");
        out.println("<tr><th>Tipo</th><th>Patrón</th><th>Comportamiento</th><th>Demo</th></tr>");
        out.println("<tr><td>Raíz exacta</td><td><code>\"\"</code> (cadena vacía)</td>" +
                "<td>Solo la URL raíz del contexto; prioridad exacta (1ª). " +
                "Diferente de <code>/</code> (default, 4ª).</td>" +
                "<td><a href='/'>→</a></td></tr>");
        out.println("<tr><td>Wildcard total</td><td><code>/*</code></td>" +
                "<td>Captura todo (prioridad 2ª). Pierde ante exactos y prefijos más largos. " +
                "Gana sobre extensiones (<code>*.do</code>) y sobre el default (<code>/</code>).</td>" +
                "<td><a href='/comodin-total'>Explicación →</a></td></tr>");
        out.println("<tr><td>Varios patrones</td><td><code>urlPatterns={…}</code></td>" +
                "<td>Un servlet responde a varias URLs independientes. " +
                "Con <code>@WebServlet</code> o múltiples <code>&lt;servlet-mapping&gt;</code>.</td>" +
                "<td><a href='/buscar'>→</a> <a href='/search'>→</a></td></tr>");
        out.println("<tr><td>Registro programático</td><td><code>ctx.addServlet()</code></td>" +
                "<td>Registra URL patterns en tiempo de arranque desde un <code>ServletContextListener</code>. " +
                "Sin <code>@WebServlet</code> ni <code>web.xml</code>.</td>" +
                "<td><a href='/registrado'>→</a></td></tr>");
        out.println("</table>");

        // ── Algoritmo de resolución ───────────────────────────────────────────
        out.println("<h2>Algoritmo de resolución paso a paso</h2>");
        out.println("<pre>");
        out.println("Dado: URI = /catalogo/42/editar");
        out.println("");
        out.println("Paso 1 — Exacto: ¿existe url-pattern='/catalogo/42/editar'?  NO");
        out.println("Paso 2 — Prefijo: busca el prefijo más largo que sea prefijo de la URI");
        out.println("         Candidatos registrados: /catalogo/*");
        out.println("         /catalogo/* coincide → GANA → CatalogoServlet");
        out.println("         servletPath='/catalogo'  pathInfo='/42/editar'");
        out.println("");
        out.println("Dado: URI = /guardar.do");
        out.println("");
        out.println("Paso 1 — Exacto: ¿existe '/guardar.do'?                       NO");
        out.println("Paso 2 — Prefijo: ¿algún /xxx/* coincide?                     NO");
        out.println("Paso 3 — Extensión: ¿existe '*.do'?                           SÍ → ExtensionServlet");
        out.println("         servletPath='/guardar.do'  pathInfo=null");
        out.println("");
        out.println("Dado: URI = /imagen.png");
        out.println("");
        out.println("Paso 1 — Exacto:    NO");
        out.println("Paso 2 — Prefijo:   NO");
        out.println("Paso 3 — Extensión: ¿existe '*.png'?  NO");
        out.println("Paso 4 — Default /  → DefaultServlet");
        out.println("         servletPath=''  pathInfo=null  (comportamiento estándar)");
        out.println("</pre>");

        // ── Gotchas ───────────────────────────────────────────────────────────
        out.println("<h2>Casos especiales y errores comunes</h2>");
        out.println("<table>");
        out.println("<tr><th>Caso</th><th>Detalle</th></tr>");
        out.println("<tr><td><code>/*</code> captura demasiado</td>" +
                "<td>Un servlet con <code>/*</code> tiene prioridad sobre el servlet por defecto " +
                "(<code>/</code>) e intercepta recursos estáticos, JSP y cualquier otro prefijo. " +
                "Úsalo solo para proxies o filtros totales.</td></tr>");
        out.println("<tr><td><code>/*.do</code> no es válido</td>" +
                "<td>La spec no permite combinar prefijo y extensión en un mismo patrón. " +
                "Usa un filtro para acotar la extensión a una sub-ruta.</td></tr>");
        out.println("<tr><td><code>/exacto/</code> ≠ <code>/exacto</code></td>" +
                "<td>La barra final es parte del patrón. " +
                "<code>/exacto/</code> y <code>/exacto</code> son exactos diferentes.</td></tr>");
        out.println("<tr><td>Extensión + prefijo</td>" +
                "<td><code>/catalogo/listar.do</code> va a CatalogoServlet (prefijo gana sobre extensión " +
                "si el prefijo coincide).</td></tr>");
        out.println("<tr><td><code>/</code> reemplaza recursos estáticos</td>" +
                "<td>Al declarar un servlet con <code>/</code> se desplaza al DefaultServlet de Tomcat. " +
                "Ficheros como <code>index.html</code> ya no se sirven automáticamente.</td></tr>");
        out.println("</table>");

        // ── Anotación vs web.xml ──────────────────────────────────────────────
        out.println("<h2>Declaración: web.xml vs @WebServlet</h2>");
        out.println("<pre>");
        out.println("// web.xml:");
        out.println("&lt;servlet-mapping&gt;");
        out.println("    &lt;servlet-name&gt;catalogo&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;/catalogo/*&lt;/url-pattern&gt;");
        out.println("&lt;/servlet-mapping&gt;");
        out.println("");
        out.println("// Anotación (varios patrones posibles):");
        out.println("@WebServlet(urlPatterns = {\"/catalogo/*\", \"/productos/*\"})");
        out.println("public class CatalogoServlet extends HttpServlet { ... }");
        out.println("");
        out.println("// Exacto con anotación:");
        out.println("@WebServlet(\"/exacto\")   // equivale a urlPatterns={\"/exacto\"}");
        out.println("");
        out.println("// Extensión con anotación:");
        out.println("@WebServlet(\"*.do\")");
        out.println("");
        out.println("// Por defecto con anotación:");
        out.println("@WebServlet(\"/\")");
        out.println("</pre>");

        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }
}
