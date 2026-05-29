package com.cursosdedesarrollo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Demuestra cómo construir distintos tipos de respuesta HTTP:
 * códigos de estado, Content-Type, cabeceras de respuesta, cookies y sesión.
 *
 * URL: GET /respuesta?tipo=<valor>
 *
 * Valores de tipo:
 *   html        → 200 text/html
 *   json        → 200 application/json
 *   texto       → 200 text/plain
 *   xml         → 200 application/xml
 *   creado      → 201 Created con cabecera Location
 *   redirigir   → 302 redirect a /request
 *   no-encontrado → 404 con cuerpo personalizado
 *   error       → 500 con mensaje de error
 *   cookie      → 200 + establece una cookie
 *   sesion      → 200 + guarda un valor en sesión HTTP
 */
public class TiposRespuestaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String tipo = req.getParameter("tipo");
        if (tipo == null) tipo = "";

        switch (tipo) {

            case "json":
                respuestaJson(resp);
                break;

            case "texto":
                respuestaTexto(resp);
                break;

            case "xml":
                respuestaXml(resp);
                break;

            case "creado":
                respuestaCreado(req, resp);
                break;

            case "redirigir":
                // 302 Found: el navegador sigue la Location automáticamente
                resp.sendRedirect("/request");
                break;

            case "no-encontrado":
                respuestaNoEncontrado(resp);
                break;

            case "error":
                respuestaError(resp);
                break;

            case "cookie":
                respuestaCookie(resp);
                break;

            case "sesion":
                respuestaSesion(req, resp);
                break;

            default:
                respuestaHtml(resp);
                break;
        }
    }

    // ── Handlers ─────────────────────────────────────────────────────────────

    private void respuestaHtml(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);           // 200
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.cabecera(out, "Respuesta HTML");
        out.println("<h1>Respuesta HTML — 200 OK</h1>");
        out.println("<p>Content-Type: <code>text/html;charset=UTF-8</code></p>");
        out.println("<p>Status: <code>200 OK</code></p>");
        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    private void respuestaJson(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);           // 200
        resp.setContentType("application/json;charset=UTF-8");
        // Para JSON no se usa PrintWriter HTML; escribimos directamente
        resp.getWriter().write(
            "{\n" +
            "  \"modulo\": \"13-servlet-request-response\",\n" +
            "  \"tipo\": \"json\",\n" +
            "  \"status\": 200,\n" +
            "  \"mensaje\": \"Respuesta JSON sin librería externa\"\n" +
            "}"
        );
    }

    private void respuestaTexto(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().write(
            "Respuesta de tipo text/plain\n" +
            "Status: 200 OK\n" +
            "Sin etiquetas HTML — el navegador muestra el texto tal cual."
        );
    }

    private void respuestaXml(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/xml;charset=UTF-8");
        resp.getWriter().write(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<respuesta>\n" +
            "  <modulo>13-servlet-request-response</modulo>\n" +
            "  <tipo>xml</tipo>\n" +
            "  <status>200</status>\n" +
            "</respuesta>"
        );
    }

    private void respuestaCreado(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);      // 201
        resp.setContentType("application/json;charset=UTF-8");
        // Location indica dónde se puede consultar el recurso creado
        resp.setHeader("Location", req.getRequestURL().toString().replace("respuesta", "request"));
        resp.getWriter().write(
            "{\n" +
            "  \"status\": 201,\n" +
            "  \"mensaje\": \"Recurso creado correctamente\",\n" +
            "  \"id\": 42\n" +
            "}"
        );
    }

    private void respuestaNoEncontrado(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);    // 404
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.cabecera(out, "404 Not Found");
        out.println("<h1 style='color:#f38ba8'>404 — No encontrado</h1>");
        out.println("<p>El recurso solicitado no existe en el servidor.</p>");
        out.println("<p><small>Status devuelto: <code>404 Not Found</code></small></p>");
        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    private void respuestaError(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(
            "{\n" +
            "  \"status\": 500,\n" +
            "  \"error\": \"Internal Server Error\",\n" +
            "  \"mensaje\": \"Error simulado para demostración\"\n" +
            "}"
        );
    }

    private void respuestaCookie(HttpServletResponse resp) throws IOException {
        // Crear cookie con 60 segundos de vida
        Cookie cookie = new Cookie("demo-cookie", "valor-desde-servlet");
        cookie.setMaxAge(60);
        cookie.setPath("/");
        resp.addCookie(cookie);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.cabecera(out, "Cookie establecida");
        out.println("<h1>Cookie establecida</h1>");
        out.println("<p>El servidor ha enviado la cabecera:</p>");
        out.println("<pre>Set-Cookie: demo-cookie=valor-desde-servlet; Max-Age=60; Path=/</pre>");
        out.println("<p>Ahora visita <a href='/request'>/request</a> y verás la cookie en la sección 5.</p>");
        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    private void respuestaSesion(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // getSession(true) crea la sesión si no existe
        req.getSession(true).setAttribute("usuario", "Ana");
        Integer visitas = (Integer) req.getSession().getAttribute("visitas");
        req.getSession().setAttribute("visitas", visitas == null ? 1 : visitas + 1);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.cabecera(out, "Sesión guardada");
        out.println("<h1>Atributo guardado en sesión</h1>");
        out.println("<p>Se ha almacenado <code>usuario=Ana</code> en la sesión HTTP.</p>");
        out.println("<p>El contenedor gestiona la sesión mediante una cookie <code>JSESSIONID</code> "
                + "o mediante reescritura de URL.</p>");
        out.println("<p>Visita <a href='/request'>/request</a> y verás el atributo en la sección 6.</p>");
        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }

    // ── Utilidad pública para tests ──────────────────────────────────────────

    public static String construirJson(String modulo, int status, String mensaje) {
        return "{\n" +
               "  \"modulo\": \"" + modulo + "\",\n" +
               "  \"tipo\": \"json\",\n" +
               "  \"status\": " + status + ",\n" +
               "  \"mensaje\": \"" + mensaje + "\"\n" +
               "}";
    }
}
