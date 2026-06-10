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
 * Único punto de entrada HTTP (patrón Front Controller).
 * Intercepta todas las peticiones bajo {@code /app/*} y delega en
 * {@link EmpleadoController} según la ruta y el verbo HTTP.
 *
 * <h3>Flujo de una petición</h3>
 * <pre>
 *   GET /app/empleados
 *     → service()  →  getPathInfo() = "/empleados"
 *     → getAcciones.get("/empleados")  →  controller::listar
 *     → EmpleadoController.listar(req, resp)
 *          req.setAttribute("empleados", lista)   ← datos al request scope
 *          return "lista.jsp"
 *     → forward a WEB-INF/vistas/lista.jsp
 *          ${empleados}  /  &lt;c:forEach&gt;  /  &lt;fmt:formatNumber&gt;
 * </pre>
 *
 * <h3>Tabla de rutas</h3>
 * <pre>
 *   GET  /app/empleados          →  EmpleadoController.listar()
 *   GET  /app/empleados/ver      →  EmpleadoController.ver()
 *   GET  /app/empleados/nuevo    →  EmpleadoController.formularioNuevo()
 *   GET  /app/empleados/editar   →  EmpleadoController.formularioEditar()
 *   POST /app/empleados/guardar  →  EmpleadoController.guardar()
 *   POST /app/empleados/eliminar →  EmpleadoController.eliminar()
 * </pre>
 */
@WebServlet("/app/*")
public class FrontControllerServlet extends HttpServlet {

    private EmpleadoController controller;

    private Map<String, Accion> getAcciones;
    private Map<String, Accion> postAcciones;

    @Override
    public void init() {
        controller = new EmpleadoController();

        // ── GET: acciones de sólo lectura ─────────────────────────────────
        getAcciones = new HashMap<>();
        getAcciones.put("/empleados",        controller::listar);
        getAcciones.put("/empleados/ver",    controller::ver);
        getAcciones.put("/empleados/nuevo",  controller::formularioNuevo);
        getAcciones.put("/empleados/editar", controller::formularioEditar);

        // ── POST: acciones de escritura ───────────────────────────────────
        postAcciones = new HashMap<>();
        postAcciones.put("/empleados/guardar",  controller::guardar);
        postAcciones.put("/empleados/eliminar", controller::eliminar);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // getPathInfo() devuelve la parte del path DESPUÉS del patrón /app
        // Ej: GET /app/empleados/ver  →  "/empleados/ver"
        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/app/empleados");
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

            if (resultado == null) return;  // el controller ya escribió la respuesta

            if (resultado.startsWith("redirect:")) {
                resp.sendRedirect(req.getContextPath() + resultado.substring("redirect:".length()));
            } else {
                req.getRequestDispatcher("/WEB-INF/vistas/" + resultado).forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException("Error procesando " + path, e);
        }
    }
}
