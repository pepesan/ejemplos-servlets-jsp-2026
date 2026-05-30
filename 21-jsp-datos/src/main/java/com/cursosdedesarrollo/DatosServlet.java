package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Único servlet del módulo 21.
 * Lee el parámetro ?vista= y hace forward a la JSP correspondiente
 * después de depositar los datos en el request.
 *
 *   GET /datos?vista=individual  → individual.jsp  (tipos primitivos y String)
 *   GET /datos?vista=lista       → lista.jsp        (List<String>, List<Object>)
 *   GET /datos?vista=bean        → bean.jsp          (JavaBeans propios)
 *   GET /datos                   → redirige a individual (vista por defecto)
 */
public class DatosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String vista = req.getParameter("vista");
        if (vista == null || vista.isEmpty()) {
            vista = "individual";
        }

        switch (vista) {
            case "individual":
                cargarIndividual(req);
                req.getRequestDispatcher("/individual.jsp").forward(req, resp);
                break;
            case "lista":
                cargarLista(req);
                req.getRequestDispatcher("/lista.jsp").forward(req, resp);
                break;
            case "bean":
                cargarBean(req);
                req.getRequestDispatcher("/bean.jsp").forward(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vista desconocida: " + vista);
        }
    }

    // ── Cargadores de datos ──────────────────────────────────────────────────

    static void cargarIndividual(HttpServletRequest req) {
        req.setAttribute("texto",    "Hola desde el servlet");
        req.setAttribute("entero",   42);
        req.setAttribute("decimal",  3.14159);
        req.setAttribute("activo",   true);
        req.setAttribute("ahora",    new Date());
        req.setAttribute("nulo",     null);
    }

    static void cargarLista(HttpServletRequest req) {
        req.setAttribute("colores",   listaColores());
        req.setAttribute("nums",      listaNums());
        req.setAttribute("mezclada",  listaMezclada());
    }

    static void cargarBean(HttpServletRequest req) {
        req.setAttribute("producto",  productoEjemplo());
        req.setAttribute("productos", listaProductos());
        req.setAttribute("alumno",    alumnoEjemplo());
        req.setAttribute("alumnos",   listaAlumnos());
    }

    // ── Factorías (package-private para tests) ───────────────────────────────

    static List<String> listaColores() {
        return Arrays.asList("Rojo", "Verde", "Azul", "Amarillo", "Naranja");
    }

    static List<Integer> listaNums() {
        return Arrays.asList(10, 20, 30, 40, 50);
    }

    static List<Object> listaMezclada() {
        return Arrays.asList("texto", 99, 3.14, true, new Date());
    }

    static Producto productoEjemplo() {
        return new Producto(1, "Teclado mecánico", 89.99, "Electrónica", true);
    }

    static List<Producto> listaProductos() {
        return Arrays.asList(
            new Producto(1, "Teclado mecánico",  89.99,  "Electrónica", true),
            new Producto(2, "Ratón inalámbrico", 34.50,  "Electrónica", true),
            new Producto(3, "Monitor 4K",        399.00, "Electrónica", false),
            new Producto(4, "Silla ergonómica",  249.00, "Mobiliario",  true),
            new Producto(5, "Webcam HD",          79.95, "Electrónica", false)
        );
    }

    static Alumno alumnoEjemplo() {
        return new Alumno("Ana", "García López", 8.5, true);
    }

    static List<Alumno> listaAlumnos() {
        return Arrays.asList(
            new Alumno("Ana",    "García López",  8.5,  true),
            new Alumno("Luis",   "Martínez Ruiz", 4.8,  true),
            new Alumno("Marta",  "Sánchez Díaz",  7.2,  true),
            new Alumno("Carlos", "López Pérez",   9.1,  false),
            new Alumno("Elena",  "Romero Gil",    3.5,  true)
        );
    }
}
