package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Demuestra el uso de doGet() y doPost() en un mismo servlet:
 *
 *   GET  /formulario → muestra el formulario HTML
 *   POST /formulario → procesa los datos y devuelve un resumen
 *
 * El formulario recoge nombre, email y mensaje.
 * La validación se hace en el servidor (no en el cliente) para que
 * el alumno vea cómo leer y verificar parámetros desde doPost().
 */
public class FormularioServlet extends HttpServlet {

    // -------------------------------------------------------------------------
    // GET: mostrar el formulario vacío (o con errores si se pasaron por query)
    // -------------------------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String error = req.getParameter("error");

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        escribirCabecera(out, "Formulario de contacto");

        out.println("<h1>Formulario de contacto</h1>");
        out.println("<p class='subtitle'>Ejemplo de <code>doGet</code> (mostrar) + <code>doPost</code> (procesar)</p>");

        if ("vacio".equals(error)) {
            out.println("<p class='error'>Todos los campos son obligatorios.</p>");
        }

        out.println("<form method='post' action='/formulario'>");
        out.println("  <label>Nombre");
        out.println("    <input type='text' name='nombre' placeholder='Tu nombre' required>");
        out.println("  </label>");
        out.println("  <label>Email");
        out.println("    <input type='email' name='email' placeholder='tu@email.com' required>");
        out.println("  </label>");
        out.println("  <label>Mensaje");
        out.println("    <textarea name='mensaje' rows='4' placeholder='Escribe tu mensaje...' required></textarea>");
        out.println("  </label>");
        out.println("  <button type='submit'>Enviar</button>");
        out.println("</form>");

        out.println("<p><a href='/'>← Volver al ciclo de vida</a></p>");

        escribirPie(out);
    }

    // -------------------------------------------------------------------------
    // POST: leer parámetros, validar y mostrar resumen
    // -------------------------------------------------------------------------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // 1. Asegurar la codificación ANTES de leer los parámetros
        req.setCharacterEncoding("UTF-8");

        // 2. Leer parámetros del cuerpo de la petición
        String nombre  = req.getParameter("nombre");
        String email   = req.getParameter("email");
        String mensaje = req.getParameter("mensaje");

        // 3. Validación básica en el servidor
        if (estaVacio(nombre) || estaVacio(email) || estaVacio(mensaje)) {
            // Redirect-After-Post (PRG): redirigir al GET para evitar el
            // reenvío del formulario al recargar la página con F5
            resp.sendRedirect("/formulario?error=vacio");
            return;
        }

        // 4. Preparar los datos validados (trim + capitalize básico)
        Map<String, String> datos = new LinkedHashMap<>();
        datos.put("Nombre",  nombre.trim());
        datos.put("Email",   email.trim().toLowerCase());
        datos.put("Mensaje", mensaje.trim());

        // 5. Respuesta: resumen de lo recibido
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        escribirCabecera(out, "Datos recibidos");

        out.println("<h1>Datos recibidos por POST</h1>");
        out.println("<p class='subtitle'>Método HTTP: <strong>" + req.getMethod() + "</strong> | "
                + "Content-Type: <strong>" + req.getContentType() + "</strong></p>");

        out.println("<table>");
        out.println("  <tr><th>Parámetro</th><th>Valor</th></tr>");
        for (Map.Entry<String, String> e : datos.entrySet()) {
            out.println("  <tr><td>" + e.getKey() + "</td><td>" + escaparHtml(e.getValue()) + "</td></tr>");
        }
        out.println("</table>");

        out.println("<div class='info'>");
        out.println("  <p><strong>¿Cómo llegan los datos?</strong></p>");
        out.println("  <p>En una petición POST los parámetros viajan en el <em>cuerpo</em> de la petición,");
        out.println("  no en la URL. El navegador los codifica como <code>application/x-www-form-urlencoded</code>:</p>");
        out.println("  <code>nombre=" + escaparHtml(nombre.trim())
                + "&amp;email=" + escaparHtml(email.trim())
                + "&amp;mensaje=...</code>");
        out.println("</div>");

        out.println("<p><a href='/formulario'>← Enviar otro mensaje</a> &nbsp; <a href='/'>Inicio</a></p>");

        escribirPie(out);
    }

    // -------------------------------------------------------------------------
    // Helpers de presentación
    // -------------------------------------------------------------------------

    private void escribirCabecera(PrintWriter out, String titulo) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'><head><meta charset='UTF-8'>");
        out.println("<title>" + titulo + "</title>");
        out.println("<style>");
        out.println("  body { font-family: sans-serif; max-width: 640px; margin: 2em auto;");
        out.println("         background: #1e1e2e; color: #cdd6f4; }");
        out.println("  h1   { color: #89b4fa; }");
        out.println("  .subtitle { color: #a6adc8; font-size: .9em; margin-top: -.5em; }");
        out.println("  label { display: block; margin: .8em 0; }");
        out.println("  input, textarea { display: block; width: 100%; margin-top: .3em;");
        out.println("    padding: .5em; border: 1px solid #45475a; border-radius: 4px;");
        out.println("    background: #313244; color: #cdd6f4; font-size: 1em; }");
        out.println("  button { margin-top: 1em; padding: .6em 1.4em; background: #89b4fa;");
        out.println("           color: #1e1e2e; border: none; border-radius: 4px; cursor: pointer;");
        out.println("           font-size: 1em; font-weight: bold; }");
        out.println("  table { border-collapse: collapse; width: 100%; margin: 1em 0; }");
        out.println("  td, th { border: 1px solid #45475a; padding: .5em 1em; text-align: left; }");
        out.println("  th { background: #313244; color: #cba6f7; }");
        out.println("  .error { background: #f38ba8; color: #1e1e2e; padding: .6em 1em;");
        out.println("           border-radius: 4px; font-weight: bold; }");
        out.println("  .info { background: #313244; border-left: 4px solid #a6e3a1;");
        out.println("          padding: .8em 1.2em; margin: 1em 0; border-radius: 4px; font-size:.9em; }");
        out.println("  a { color: #89dceb; }");
        out.println("</style></head><body>");
    }

    private void escribirPie(PrintWriter out) {
        out.println("</body></html>");
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    // Evita XSS básico al reflejar datos del usuario en HTML
    static String escaparHtml(String texto) {
        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
