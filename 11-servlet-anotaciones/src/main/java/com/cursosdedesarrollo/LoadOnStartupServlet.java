package com.cursosdedesarrollo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Demuestra loadOnStartup: el contenedor instancia e inicializa este servlet
 * durante el arranque del servidor, no en la primera petición.
 *
 * loadOnStartup = 1  → se inicializa en primer lugar (valor positivo = orden)
 * loadOnStartup = -1 → comportamiento perezoso (valor negativo o no declarado)
 *
 * Comparar con LazyServlet (sin loadOnStartup) para ver la diferencia
 * entre los tiempos de init() y los tiempos de primera petición.
 */
@WebServlet(urlPatterns = "/precargado", loadOnStartup = 1)
public class LoadOnStartupServlet extends HttpServlet {

    private long     initMs;
    private long     primeraMs = -1;
    private int      numeroPeticion;

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

        Html.cabecera(out, "loadOnStartup – Carga eagerly");
        Html.nav(out);

        out.println("<h1>loadOnStartup — Inicialización eagerly</h1>");
        out.println("<p class='sub'>Con <code>loadOnStartup &gt; 0</code> el contenedor llama a " +
                "<code>init()</code> al arrancar, antes de que llegue ninguna petición.</p>");

        // ── Tiempos ────────────────────────────────────────────────────────
        out.println("<h2>Tiempos registrados</h2>");
        out.println("<table>");
        out.println("<tr><th>Evento</th><th>Marca de tiempo</th><th>Diferencia vs init()</th></tr>");
        out.println("<tr><td><code>init()</code> ejecutado</td>" +
                "<td><code>" + fmt(initMs) + "</code></td><td>—</td></tr>");
        out.println("<tr><td>Primera petición recibida</td>" +
                "<td><code>" + fmt(primeraMs) + "</code></td>" +
                "<td class='ok'>+" + diferencia(initMs, primeraMs) + "</td></tr>");
        out.println("<tr><td>Esta petición (#" + numeroPeticion + ")</td>" +
                "<td><code>" + fmt(ahoraMs) + "</code></td>" +
                "<td>+" + diferencia(initMs, ahoraMs) + "</td></tr>");
        out.println("</table>");
        out.println("<p class='nota'>Si el tiempo entre <code>init()</code> y la primera petición " +
                "es de varios segundos, confirma que la inicialización ocurrió en el arranque " +
                "del servidor, no al llegar la primera petición.</p>");

        // ── Código ────────────────────────────────────────────────────────
        out.println("<h2>Código</h2>");
        out.println("<pre>");
        out.println("@WebServlet(urlPatterns = \"/precargado\", loadOnStartup = 1)");
        out.println("public class LoadOnStartupServlet extends HttpServlet {");
        out.println("");
        out.println("    private long initMs;");
        out.println("");
        out.println("    @Override");
        out.println("    public void init() {");
        out.println("        initMs = System.currentTimeMillis(); // llamado al arrancar");
        out.println("        // aquí: abrir pool de BBDD, cargar caché, leer fichero de config...");
        out.println("    }");
        out.println("}");
        out.println("</pre>");

        // ── Equivalente web.xml ───────────────────────────────────────────
        out.println("<h2>Equivalente en web.xml</h2>");
        out.println("<pre>");
        out.println("&lt;servlet&gt;");
        out.println("    &lt;servlet-name&gt;precargado&lt;/servlet-name&gt;");
        out.println("    &lt;servlet-class&gt;...LoadOnStartupServlet&lt;/servlet-class&gt;");
        out.println("    &lt;load-on-startup&gt;1&lt;/load-on-startup&gt;");
        out.println("&lt;/servlet&gt;");
        out.println("</pre>");

        // ── Orden de carga ────────────────────────────────────────────────
        out.println("<h2>Orden de carga con loadOnStartup</h2>");
        out.println("<table>");
        out.println("<tr><th>Valor</th><th>Comportamiento</th></tr>");
        out.println("<tr><td><code>loadOnStartup = -1</code> (o no declarado)</td>" +
                "<td>Inicialización <strong>perezosa</strong>: en la primera petición. " +
                "Es el valor por defecto. Ver <a href='/perezoso'>/perezoso</a>.</td></tr>");
        out.println("<tr><td><code>loadOnStartup = 0</code></td>" +
                "<td>Eagerly, pero sin orden garantizado entre servlets con valor 0.</td></tr>");
        out.println("<tr><td><code>loadOnStartup = 1, 2, 3…</code></td>" +
                "<td>Eagerly, en orden ascendente: primero el 1, luego el 2, etc.</td></tr>");
        out.println("<tr><td>Mismo valor en varios servlets</td>" +
                "<td>El orden entre ellos no está especificado por la spec (depende del contenedor).</td></tr>");
        out.println("</table>");

        out.println("<h2>Cuándo usar loadOnStartup</h2>");
        out.println("<ul>");
        out.println("<li>Conexiones a bases de datos o caché que tardan en abrirse.</li>");
        out.println("<li>Lectura de ficheros de configuración grandes.</li>");
        out.println("<li>Registrar recursos compartidos en el <code>ServletContext</code>.</li>");
        out.println("<li>Verificar dependencias al arranque y fallar rápido si faltan.</li>");
        out.println("</ul>");

        out.println("<p><a href='/perezoso'>Ver demo comparativo: LazyServlet →</a></p>");
        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    static String fmt(long ms) {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(ms));
    }

    static String diferencia(long desde, long hasta) {
        long ms = hasta - desde;
        if (ms < 0)    return "n/d";
        if (ms < 1000) return ms + " ms";
        return (ms / 1000) + " s " + (ms % 1000) + " ms";
    }
}
