package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Carrito de compra guardado en sesión.
 * Demuestra cómo almacenar y modificar objetos complejos (List) en sesión.
 *
 * GET /carrito                          → muestra el carrito actual
 * GET /carrito?accion=agregar&item=X    → añade item (PRG)
 * GET /carrito?accion=vaciar            → vacía el carrito (PRG)
 */
public class CarritoServlet extends HttpServlet {

    static final String ATTR = "carrito";

    static final List<String> CATALOGO = Arrays.asList(
        "Teclado mecánico — 89,99 €",
        "Ratón inalámbrico — 34,50 €",
        "Monitor 4K — 399,00 €",
        "Auriculares USB — 59,95 €",
        "Webcam HD — 79,00 €"
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = req.getSession(true);

        String accion = req.getParameter("accion");

        if ("agregar".equals(accion)) {
            String item = req.getParameter("item");
            if (item != null && !item.trim().isEmpty()) {
                agregar(sesion, item.trim());
            }
            resp.sendRedirect("/carrito");
            return;
        }
        if ("vaciar".equals(accion)) {
            sesion.removeAttribute(ATTR);
            resp.sendRedirect("/carrito");
            return;
        }

        List<String> carrito = obtenerCarrito(sesion);
        PrintWriter out = resp.getWriter();
        Html.cabecera(out, "Carrito de compra");
        Html.nav(out, "carrito");

        out.println("<h1>Carrito de compra en sesión</h1>");
        out.println("<p class='sub'>Los artículos se guardan en <code>session.setAttribute(\"carrito\", lista)</code> "
                + "y persisten entre peticiones mientras la sesión esté activa.</p>");

        // ── Carrito actual ────────────────────────────────────────────────────
        out.println("<h2>Tu carrito (" + carrito.size() + " artículo"
                + (carrito.size() == 1 ? "" : "s") + ")</h2>");
        if (carrito.isEmpty()) {
            out.println("<p class='nota'>El carrito está vacío.</p>");
        } else {
            out.println("<table>");
            out.println("<tr><th>#</th><th>Artículo</th></tr>");
            for (int i = 0; i < carrito.size(); i++) {
                out.println("<tr><td>" + (i + 1) + "</td><td>" + Html.esc(carrito.get(i)) + "</td></tr>");
            }
            out.println("</table>");
            out.println("<p><a href='/carrito?accion=vaciar' class='ko'>Vaciar carrito</a></p>");
        }

        // ── Catálogo ──────────────────────────────────────────────────────────
        out.println("<h2>Catálogo — añadir al carrito</h2>");
        out.println("<table>");
        out.println("<tr><th>Producto</th><th>Acción</th></tr>");
        for (String item : CATALOGO) {
            out.println("<tr><td>" + Html.esc(item) + "</td>");
            out.println("<td><a href='/carrito?accion=agregar&item="
                    + java.net.URLEncoder.encode(item, "UTF-8")
                    + "'>+ Añadir</a></td></tr>");
        }
        out.println("</table>");

        // ── Código ────────────────────────────────────────────────────────────
        out.println("<h2>Código clave</h2>");
        out.println("<pre>");
        out.println("// Obtener o crear la lista del carrito:");
        out.println("List&lt;String&gt; carrito = (List&lt;String&gt;) sesion.getAttribute(\"carrito\");");
        out.println("if (carrito == null) {");
        out.println("    carrito = new ArrayList&lt;&gt;();");
        out.println("    sesion.setAttribute(\"carrito\", carrito);");
        out.println("}");
        out.println("");
        out.println("// Añadir artículo:");
        out.println("carrito.add(item);   // La sesión ya referencia la misma lista");
        out.println("");
        out.println("// Vaciar:");
        out.println("sesion.removeAttribute(\"carrito\");");
        out.println("</pre>");

        out.println("<p class='nota'>Session ID: <code>" + Html.esc(sesion.getId()) + "</code></p>");
        Html.pie(out);
    }

    @SuppressWarnings("unchecked")
    static List<String> obtenerCarrito(HttpSession sesion) {
        List<String> carrito = (List<String>) sesion.getAttribute(ATTR);
        if (carrito == null) {
            carrito = new ArrayList<>();
            sesion.setAttribute(ATTR, carrito);
        }
        return carrito;
    }

    static void agregar(HttpSession sesion, String item) {
        obtenerCarrito(sesion).add(item);
    }

    static int tamano(HttpSession sesion) {
        return obtenerCarrito(sesion).size();
    }
}
