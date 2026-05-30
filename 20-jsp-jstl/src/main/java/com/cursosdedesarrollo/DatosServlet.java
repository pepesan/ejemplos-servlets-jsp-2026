package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Único servlet del módulo 20.
 *
 * Carga datos en el request (frutas, productos, ahora, precio, pct)
 * y hace forward a la JSP indicada por el parámetro ?vista=.
 *
 *   GET /datos          → jstl-core.jsp
 *   GET /datos?vista=fmt → jstl-fmt.jsp
 */
public class DatosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ── Datos comunes a todas las vistas ─────────────────────────────────
        req.setAttribute("nombre",    "Mundo");
        req.setAttribute("frutas",    crearFrutas());
        req.setAttribute("productos", crearProductos());
        req.setAttribute("ahora",     new Date());
        req.setAttribute("precio",    9_876.543);
        req.setAttribute("pct",       0.1875);

        // Guarda el usuario en sesión para que el.jsp pueda leerlo con EL
        req.getSession(true).setAttribute("usuario", "Alumno");

        // ── Forward a la vista solicitada ─────────────────────────────────────
        String vista = req.getParameter("vista");
        String jspDestino = "fmt".equals(vista) ? "/jstl-fmt.jsp" : "/jstl-core.jsp";

        req.getRequestDispatcher(jspDestino).forward(req, resp);
    }

    /** Lista simple de cadenas para demostrar c:forEach básico. */
    static List<String> crearFrutas() {
        return Arrays.asList("Manzana", "Naranja", "Plátano", "Mango", "Kiwi");
    }

    /**
     * Lista de mapas que simulan objetos de dominio.
     * Cada mapa tiene: nombre (String), precio (double), disponible (boolean).
     */
    static List<Map<String, Object>> crearProductos() {
        return Arrays.asList(
            producto("Teclado mecánico", 89.99,  true),
            producto("Ratón inalámbrico", 34.50, true),
            producto("Monitor 4K",       399.00, false),
            producto("Auriculares USB",   59.95, true),
            producto("Webcam HD",         79.00, false)
        );
    }

    private static Map<String, Object> producto(String nombre, double precio, boolean disponible) {
        Map<String, Object> m = new HashMap<>();
        m.put("nombre",     nombre);
        m.put("precio",     precio);
        m.put("disponible", disponible);
        return m;
    }
}
