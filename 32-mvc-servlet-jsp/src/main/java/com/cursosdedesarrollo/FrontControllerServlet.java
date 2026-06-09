package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Único punto de entrada HTTP. Decide qué método del Controller invocar
 * según la ruta y el verbo HTTP, y luego delega el forward/redirect.
 *
 * Flujo:
 *   Petición → FrontControllerServlet.service()
 *                  → AlumnoController.metodo(req, resp)
 *                       → req.setAttribute(...)   (datos para la vista)
 *                       → return "lista.jsp"       (nombre de la vista)
 *                  → forward  a WEB-INF/vistas/lista.jsp
 *                  ← respuesta HTML generada por JSTL/EL
 */
@WebServlet("/app/*")
public class FrontControllerServlet extends HttpServlet {

    private AlumnoController controller;

    private Map<String, Accion> getAcciones;
    private Map<String, Accion> postAcciones;

    @Override
    public void init() {
        controller = new AlumnoController();

        // ── GET: acciones de sólo lectura ─────────────────────────────────
        getAcciones = new HashMap<>();
        getAcciones.put("/alumnos",        controller::listar);
        getAcciones.put("/alumnos/ver",    controller::ver);
        getAcciones.put("/alumnos/nuevo",  controller::formularioNuevo);
        getAcciones.put("/alumnos/editar", controller::formularioEditar);

        // ── POST: acciones de escritura ───────────────────────────────────
        postAcciones = new HashMap<>();
        postAcciones.put("/alumnos/guardar",  controller::guardar);
        postAcciones.put("/alumnos/eliminar", controller::eliminar);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();   // p.ej. "/alumnos", "/alumnos/ver"

        // Raíz → redirigir a la lista
        if (path == null || path.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/app/alumnos");
            return;
        }

        boolean esPost = "POST".equalsIgnoreCase(req.getMethod());
        Accion accion = (esPost ? postAcciones : getAcciones).get(path);

        if (accion == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no registrada: " + path);
            return;
        }

        try {
            String resultado = accion.ejecutar(req, resp);

            if (resultado == null) return;   // el controller ya escribió la respuesta

            if (resultado.startsWith("redirect:")) {
                // PRG: el controller devuelve "redirect:/app/alumnos"
                resp.sendRedirect(req.getContextPath() + resultado.substring("redirect:".length()));
            } else {
                // Forward a la vista JSP
                req.getRequestDispatcher("/WEB-INF/vistas/" + resultado).forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException("Error procesando " + path, e);
        }
    }
}
