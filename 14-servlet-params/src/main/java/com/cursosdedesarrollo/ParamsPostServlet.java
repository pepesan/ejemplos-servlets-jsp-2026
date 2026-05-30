package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Demuestra el paso de parámetros mediante un formulario HTML (método POST).
 *
 * GET  /post → muestra el formulario
 * POST /post → procesa los datos del formulario y los muestra
 *
 * Los parámetros POST viajan en el cuerpo de la petición con
 * Content-Type: application/x-www-form-urlencoded.
 * La API de Servlet los lee exactamente igual que los de GET.
 */
public class ParamsPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Formulario POST");
        out.println("<h1>Parámetros POST — formulario HTML</h1>");
        out.println("<p class='sub'>Los datos viajan en el cuerpo de la petición, no en la URL.</p>");

        mostrarFormulario(out, null, null, null, null);

        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String nombre  = req.getParameter("nombre");
        String email   = req.getParameter("email");
        String mensaje = req.getParameter("mensaje");
        String idioma  = req.getParameter("idioma");

        Html.cabecera(out, "Resultado POST");
        out.println("<h1>Datos recibidos por POST</h1>");
        out.println("<p class='sub'>Todos los campos leídos con <code>req.getParameter()</code>.</p>");

        // ── Resumen de lo recibido ────────────────────────────────────────────
        boolean formValido = esValido(nombre) && esValido(email);

        if (formValido) {
            out.println("<p class='ok'>&#10003; Formulario recibido correctamente.</p>");
        } else {
            out.println("<p class='error'>&#9888; Nombre y correo son obligatorios.</p>");
        }

        out.println("<h2>Parámetros recibidos</h2>");
        out.println("<table>");
        out.println("<tr><th>Campo</th><th>Valor</th><th>¿Válido?</th></tr>");
        fila(out, "nombre",  nombre,  esValido(nombre));
        fila(out, "email",   email,   esValido(email));
        fila(out, "mensaje", mensaje, true);
        fila(out, "idioma",  idioma,  true);
        out.println("</table>");

        // ── Detalles de la petición ───────────────────────────────────────────
        out.println("<h2>Cabeceras de la petición POST</h2>");
        out.println("<table>");
        out.println("<tr><th>Cabecera</th><th>Valor</th></tr>");
        out.println("<tr><td><code>Method</code></td><td>" + Html.esc(req.getMethod()) + "</td></tr>");
        out.println("<tr><td><code>Content-Type</code></td><td>" + Html.esc(req.getContentType()) + "</td></tr>");
        out.println("<tr><td><code>Content-Length</code></td><td>" + req.getContentLength() + " bytes</td></tr>");
        out.println("</table>");

        out.println("<p class='hint'>Abre las DevTools (F12 → Network) y envía el formulario de nuevo para ver "
                + "el cuerpo de la petición POST en la pestaña <em>Payload</em>.</p>");

        // ── Volver al formulario ──────────────────────────────────────────────
        out.println("<h2>Modificar datos</h2>");
        mostrarFormulario(out, nombre, email, mensaje, idioma);

        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    private static void mostrarFormulario(PrintWriter out,
                                          String nombre, String email,
                                          String mensaje, String idioma) {
        out.println("<form method='post' action='post'>");

        out.println("<label for='nombre'>Nombre *</label>");
        out.println("<input type='text' id='nombre' name='nombre' "
                + "value='" + Html.esc(nombre) + "' placeholder='Tu nombre' required>");

        out.println("<label for='email'>Correo electrónico *</label>");
        out.println("<input type='email' id='email' name='email' "
                + "value='" + Html.esc(email) + "' placeholder='tu@ejemplo.com' required>");

        out.println("<label for='idioma'>Idioma favorito</label>");
        out.println("<select id='idioma' name='idioma'>");
        for (String op : new String[]{"Java", "Python", "JavaScript", "Kotlin", "Go"}) {
            String sel = op.equals(idioma) ? " selected" : "";
            out.println("<option value='" + op + "'" + sel + ">" + op + "</option>");
        }
        out.println("</select>");

        out.println("<label for='mensaje'>Mensaje</label>");
        out.println("<textarea id='mensaje' name='mensaje' rows='4' "
                + "placeholder='Escribe algo...'>" + Html.esc(mensaje) + "</textarea>");

        out.println("<button type='submit'>Enviar por POST</button>");
        out.println("</form>");
    }

    private static void fila(PrintWriter out, String campo, String valor, boolean valido) {
        String icon = valido ? "<span class='ok'>&#10003;</span>" : "<span class='error'>&#9888;</span>";
        out.println("<tr><td><code>" + Html.esc(campo) + "</code></td>"
                + "<td>" + Html.esc(valor) + "</td>"
                + "<td>" + icon + "</td></tr>");
    }

    /** Devuelve true si el valor no es nulo ni está en blanco. */
    static boolean esValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
}
