package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Patrón 4 — Servlet por defecto: url-pattern = "/"
 *
 * Captura cualquier URL que no coincida con un patrón más específico.
 * Reemplaza al servlet interno de Tomcat que sirve ficheros estáticos.
 * Como consecuencia, /index.html, /css/app.css, etc. también llegan aquí.
 *
 * Casos de uso reales:
 *   - Front Controller en MVC (veremos esto en el módulo 30)
 *   - Servir ficheros estáticos + lógica propia (Spring MVC lo hace así)
 *   - Capturar URLs con formato REST cuando no hay framework
 */
public class DefaultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        if (esRaiz(req.getRequestURI())) {
            mostrarBienvenida(out);
        } else {
            mostrarCapturado(req, out);
        }
    }

    private void mostrarBienvenida(PrintWriter out) {
        Html.cabecera(out, "18 – Patrones de URL en Servlets");
        Html.nav(out);

        out.println("<h1>&#127760; Patrones de URL en Servlets</h1>");
        out.println("<p class='sub'>La especificación Servlet define cuatro tipos de patrón " +
                "y un orden de prioridad estricto para resolverlos.</p>");

        out.println("<h2>Los cuatro tipos de patrón</h2>");
        out.println("<table>");
        out.println("<tr><th>#</th><th>Tipo</th><th>Sintaxis</th><th>Ejemplo</th><th>Demo</th></tr>");
        out.println("<tr><td><span class='badge b1'>1</span></td><td><strong>Coincidencia exacta</strong></td>" +
                "<td><code>/ruta</code></td><td><code>/exacto</code></td>" +
                "<td><a href='/exacto'>Probar</a></td></tr>");
        out.println("<tr><td><span class='badge b2'>2</span></td><td><strong>Prefijo wildcard</strong></td>" +
                "<td><code>/prefijo/*</code></td><td><code>/catalogo/*</code></td>" +
                "<td><a href='/catalogo/'>Probar</a></td></tr>");
        out.println("<tr><td><span class='badge b3'>3</span></td><td><strong>Extensión</strong></td>" +
                "<td><code>*.ext</code></td><td><code>*.do</code></td>" +
                "<td><a href='/listar.do'>listar.do</a> · <a href='/pedido/guardar.do'>pedido/guardar.do</a></td></tr>");
        out.println("<tr><td><span class='badge b4'>4</span></td><td><strong>Por defecto (esta página)</strong></td>" +
                "<td><code>/</code></td><td>cualquier URL no mapeada</td>" +
                "<td><a href='/no-existe/algo'>Probar</a></td></tr>");
        out.println("</table>");

        out.println("<h2>Regla de prioridad (Servlet Spec §12.2)</h2>");
        out.println("<pre>");
        out.println("Para una petición GET /catalogo/42:");
        out.println("");
        out.println("  1. ¿Hay un servlet con url-pattern='/catalogo/42'?  → No");
        out.println("  2. ¿Hay un prefijo wildcard que coincida?");
        out.println("       /catalogo/* → Sí  (más largo que /*)");
        out.println("       → Gana CatalogoServlet");
        out.println("");
        out.println("Para una petición GET /guardar.do:");
        out.println("");
        out.println("  1. Exacto: /guardar.do registrado?           → No");
        out.println("  2. Prefijo: algún /xxx/* coincide?           → No");
        out.println("  3. Extensión: *.do registrado?               → Sí");
        out.println("       → Gana ExtensionServlet");
        out.println("");
        out.println("Para una petición GET /imagen.png:");
        out.println("");
        out.println("  1. Exacto: /imagen.png registrado?           → No");
        out.println("  2. Prefijo: algún /xxx/* coincide?           → No");
        out.println("  3. Extensión: *.png registrado?              → No");
        out.println("  4. Servlet por defecto (/)                   → Sí");
        out.println("       → Gana DefaultServlet (esta clase)");
        out.println("</pre>");

        out.println("<p><a href='/patrones'>&#128218; Ver tabla de referencia completa →</a></p>");
        Html.pie(out);
    }

    private void mostrarCapturado(HttpServletRequest req, PrintWriter out) {
        Html.cabecera(out, "Capturado por DefaultServlet");
        Html.nav(out);

        out.println("<h1><span class='badge b4'>4</span> Servlet por defecto — <code>/</code></h1>");
        out.println("<p class='sub'>Esta URL no coincide con ningún patrón más específico. " +
                "El servlet por defecto es el último recurso.</p>");

        out.println("<div class='nota'>");
        out.println("URL capturada: <code>" + Html.esc(req.getRequestURI()) + "</code>");
        out.println("</div>");

        out.println("<h2>Valores de la petición</h2>");
        Html.tablaInfo(out, req);

        out.println("<h2>¿Por qué llega aquí?</h2>");
        out.println("<ol>");
        out.println("<li>¿Coincidencia exacta? <span class='ko'>No</span></li>");
        out.println("<li>¿Prefijo wildcard más largo? <span class='ko'>No</span></li>");
        out.println("<li>¿Extensión? <span class='ko'>No</span></li>");
        out.println("<li>Servlet por defecto (<code>/</code>): <span class='ok'>Sí → aquí</span></li>");
        out.println("</ol>");

        out.println("<p class='nota'>Nota: con el patrón <code>/</code> este servlet reemplaza al servlet " +
                "interno de Tomcat que sirve ficheros estáticos. Si necesitas servir CSS, JS o imágenes " +
                "deberías delegar al <code>DefaultServlet</code> original de Tomcat o manejarlos explícitamente.</p>");

        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    static boolean esRaiz(String requestUri) {
        return "/".equals(requestUri) || "".equals(requestUri);
    }
}
