package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * Demuestra la lectura de parámetros enviados por query string (GET).
 *
 * Prueba:
 *   GET /get                                   → sin parámetros
 *   GET /get?nombre=Ana&ciudad=Madrid           → parámetros simples
 *   GET /get?color=rojo&color=verde&color=azul  → parámetro multivalor
 */
public class ParamsGetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Parámetros GET (query string)");
        out.println("<h1>Parámetros GET — query string</h1>");
        out.println("<p class='sub'>Los parámetros GET viajan en la URL, tras el signo <code>?</code>.</p>");

        // ── Línea de petición ─────────────────────────────────────────────────
        out.println("<h2>URL recibida</h2>");
        out.println("<pre>" + Html.esc(req.getRequestURL()
                + (req.getQueryString() != null ? "?" + req.getQueryString() : "")) + "</pre>");

        // ── Parámetros ────────────────────────────────────────────────────────
        out.println("<h2>Parámetros recibidos</h2>");

        boolean hayParams = req.getParameterNames().hasMoreElements();
        if (!hayParams) {
            out.println("<p class='hint'>Sin parámetros. Prueba los enlaces de abajo.</p>");
        } else {
            out.println("<table>");
            out.println("<tr><th>Nombre</th><th>Valor(es)</th><th>getParameter()</th></tr>");
            for (String nombre : Collections.list(req.getParameterNames())) {
                String[] valores = req.getParameterValues(nombre);
                out.println("<tr>");
                out.println("<td><code>" + Html.esc(nombre) + "</code></td>");
                out.println("<td>" + Html.esc(formatearValores(valores)) + "</td>");
                out.println("<td>" + Html.esc(req.getParameter(nombre))
                        + (valores.length > 1 ? " <em>(solo primer valor)</em>" : "") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        // ── Explicación de la API ─────────────────────────────────────────────
        out.println("<h2>API de parámetros</h2>");
        out.println("<table>");
        out.println("<tr><th>Método</th><th>Devuelve</th></tr>");
        out.println("<tr><td><code>getParameter(nombre)</code></td>"
                + "<td>primer valor (o null si no existe)</td></tr>");
        out.println("<tr><td><code>getParameterValues(nombre)</code></td>"
                + "<td>todos los valores del parámetro</td></tr>");
        out.println("<tr><td><code>getParameterNames()</code></td>"
                + "<td>enumeración con todos los nombres</td></tr>");
        out.println("<tr><td><code>getParameterMap()</code></td>"
                + "<td>Map&lt;String,String[]&gt; con todos</td></tr>");
        out.println("<tr><td><code>getQueryString()</code></td>"
                + "<td>la cadena cruda sin decodificar</td></tr>");
        out.println("</table>");

        // ── Links de prueba ───────────────────────────────────────────────────
        out.println("<h2>Pruébalo</h2>");
        out.println("<p>");
        out.println("<a href='get'>Sin parámetros</a> &nbsp;|&nbsp; ");
        out.println("<a href='get?nombre=Ana&ciudad=Madrid'>nombre + ciudad</a> &nbsp;|&nbsp; ");
        out.println("<a href='get?color=rojo&color=verde&color=azul'>multivalor (color×3)</a> &nbsp;|&nbsp; ");
        out.println("<a href='get?texto=hola+mundo&especial=%3Chola%3E'>URL encoding</a>");
        out.println("</p>");

        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    /** Une todos los valores de un parámetro multivalor con coma y espacio. */
    static String formatearValores(String[] valores) {
        if (valores == null || valores.length == 0) return "";
        if (valores.length == 1) return valores[0];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valores.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(valores[i]);
        }
        return sb.toString();
    }
}
