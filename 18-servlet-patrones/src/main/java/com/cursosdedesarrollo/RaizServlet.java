package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Patrón especial — Cadena vacía: url-pattern = ""
 *
 * La spec (§12.2) reserva "" para mapear exactamente la raíz del contexto:
 * la URL http://host:port/<contextPath>/ y solo esa.
 *
 * Diferencia con el patrón por defecto "/":
 *   ""  → coincidencia exacta de la raíz; máxima prioridad (tipo 1)
 *   "/"  → servlet por defecto; mínima prioridad (tipo 4, catch-all)
 *
 * Con contexto desplegado en "/":
 *   http://localhost:8018/  → RaizServlet  (patrón "")
 *   http://localhost:8018/x → DefaultServlet (patrón "/")
 */
public class RaizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Patrón raíz – cadena vacía");
        Html.nav(out);

        out.println("<h1><span class='badge b1'>especial</span> Patrón raíz — <code>\"\"</code> (cadena vacía)</h1>");
        out.println("<p class='sub'>El gran desconocido: se activa <em>únicamente</em> para la URL raíz del contexto.</p>");

        out.println("<div class='nota'>");
        out.println("Si ves esta página, el patrón <code>\"\"</code> ha funcionado: " +
                "la petición a <code>/</code> vino aquí (RaizServlet) y no al DefaultServlet.</div>");

        out.println("<h2>Valores de la petición</h2>");
        Html.tablaInfo(out, req);

        out.println("<h2>Diferencia entre <code>\"\"</code> y <code>/</code></h2>");
        out.println("<table>");
        out.println("<tr><th>Patrón</th><th>Tipo</th><th>Prioridad</th><th>Captura</th></tr>");
        out.println("<tr>");
        out.println("<td><code>\"\"</code></td>");
        out.println("<td>Coincidencia exacta</td>");
        out.println("<td><span class='badge b1'>1ª</span></td>");
        out.println("<td>Solo <code>http://host:port/contextPath/</code> (la raíz exacta)</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td><code>/</code></td>");
        out.println("<td>Servlet por defecto</td>");
        out.println("<td><span class='badge b4'>4ª</span></td>");
        out.println("<td>Todo lo que no coincida con ningún otro patrón</td>");
        out.println("</tr>");
        out.println("</table>");

        out.println("<h2>Comportamiento por URL</h2>");
        out.println("<table>");
        out.println("<tr><th>URL</th><th>Servlet con <code>\"\"</code> registrado</th></tr>");
        out.println("<tr><td><a href='/'><code>http://localhost:8018/</code></a></td>" +
                "<td class='ok'>RaizServlet (esta página) — exacto gana</td></tr>");
        out.println("<tr><td><a href='/otro'><code>http://localhost:8018/otro</code></a></td>" +
                "<td>DefaultServlet — <code>\"\"</code> no coincide</td></tr>");
        out.println("<tr><td><a href='/exacto'><code>http://localhost:8018/exacto</code></a></td>" +
                "<td>ExactoServlet — su propio patrón exacto gana</td></tr>");
        out.println("</table>");

        out.println("<h2>Declaración en web.xml</h2>");
        out.println("<pre>");
        out.println("&lt;servlet-mapping&gt;");
        out.println("    &lt;servlet-name&gt;raiz&lt;/servlet-name&gt;");
        out.println("    &lt;url-pattern&gt;&lt;/url-pattern&gt;  &lt;!-- cadena vacía --&gt;");
        out.println("&lt;/servlet-mapping&gt;");
        out.println("</pre>");
        out.println("<p>Con anotación: <code>@WebServlet(\"\")</code></p>");

        out.println("<h2>¿Cuándo usarlo?</h2>");
        out.println("<ul>");
        out.println("<li>Cuando quieres una lógica específica para la raíz (p.ej. redirect a <code>/inicio</code>) " +
                "sin que esa lógica interfiera con el resto de URLs no mapeadas.</li>");
        out.println("<li>En combinación con el DefaultServlet para separar responsabilidades.</li>");
        out.println("</ul>");

        out.println("<p><a href='/'>← Inicio</a> &nbsp;|&nbsp; <a href='/patrones'>Referencia</a></p>");
        Html.pie(out);
    }
}
