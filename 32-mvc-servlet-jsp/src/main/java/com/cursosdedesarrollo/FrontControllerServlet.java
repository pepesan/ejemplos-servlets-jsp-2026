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
 * Único punto de entrada HTTP de la aplicación (patrón Front Controller).
 * Intercepta TODAS las peticiones bajo {@code /app/*} y decide qué método
 * del controller invocar según la ruta y el verbo HTTP.
 *
 * <h3>Flujo de una petición GET /app/alumnos</h3>
 * <pre>
 *   1. Tomcat  →  service(req, resp)
 *   2. getPathInfo()  →  "/alumnos"
 *   3. getAcciones.get("/alumnos")  →  controller::listar  (referencia a método)
 *   4. controller.listar(req, resp)
 *        req.setAttribute("alumnos", lista)   ← datos al request scope
 *        return "lista.jsp"
 *   5. forward a WEB-INF/vistas/lista.jsp
 *        ${alumnos}   ← EL lee el atributo del request
 * </pre>
 *
 * <h3>¿Por qué service() en vez de doGet() + doPost()?</h3>
 * Se sobreescribe {@code service()} para poder elegir el mapa de acciones
 * (GET o POST) antes de despachar. Si usásemos doGet/doPost tendríamos
 * código duplicado o necesitaríamos un método auxiliar.
 *
 * <h3>Tabla de rutas registradas en init()</h3>
 * <pre>
 *   GET  /app/alumnos          →  AlumnoController.listar()
 *   GET  /app/alumnos/ver      →  AlumnoController.ver()
 *   GET  /app/alumnos/nuevo    →  AlumnoController.formularioNuevo()
 *   GET  /app/alumnos/editar   →  AlumnoController.formularioEditar()
 *   POST /app/alumnos/guardar  →  AlumnoController.guardar()
 *   POST /app/alumnos/eliminar →  AlumnoController.eliminar()
 * </pre>
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

        // getPathInfo() devuelve la parte del path DESPUÉS del patrón del servlet (/app).
        // Ejemplos:  GET /app/alumnos     → "/alumnos"
        //            GET /app/alumnos/ver → "/alumnos/ver"
        //            GET /app             → null   (sin barra final)
        //            GET /app/            → "/"
        String path = req.getPathInfo();

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
