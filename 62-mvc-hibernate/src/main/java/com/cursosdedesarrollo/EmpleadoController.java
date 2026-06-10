package com.cursosdedesarrollo;

import com.cursosdedesarrollo.modelo.Empleado;
import com.cursosdedesarrollo.modelo.EmpleadoDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de empleados (C en MVC).
 *
 * <p>Cada método:</p>
 * <ol>
 *   <li>Lee parámetros HTTP ({@code req.getParameter(...)}).</li>
 *   <li>Invoca al DAO (capa de datos Hibernate) para leer o modificar la BD.</li>
 *   <li><strong>Pone los resultados en el request con {@code setAttribute}</strong>
 *       para que la vista los lea con EL ({@code ${nombre}}).</li>
 *   <li>Devuelve el nombre del JSP o {@code "redirect:..."};
 *       {@link FrontControllerServlet} ejecuta el forward/redirect.</li>
 * </ol>
 *
 * <h3>Contrato setAttribute → EL por vista</h3>
 * <pre>
 *   lista.jsp
 *     setAttribute("empleados", List&lt;Empleado&gt;) → ${empleados}  en &lt;c:forEach&gt;
 *     setAttribute("total",     int)              → ${total}
 *     setAttribute("buscar",    String)           → ${buscar}     pre-rellena búsqueda
 *
 *   detalle.jsp
 *     setAttribute("empleado", Empleado)          → ${empleado.nombre}, ${empleado.salario}…
 *
 *   formulario.jsp
 *     setAttribute("empleado",  Empleado)         → value="${empleado.nombre}"… (pre-relleno)
 *     setAttribute("esNuevo",   boolean)          → &lt;c:if test="${esNuevo}"&gt;
 *     setAttribute("errores",   Map&lt;String,String&gt;) → ${errores.nombre}
 * </pre>
 */
public class EmpleadoController {

    private final EmpleadoDao dao = new EmpleadoDao();

    // ── GET /app/empleados ─────────────────────────────────────────────────

    public String listar(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String buscar = req.getParameter("buscar");

        List<Empleado> empleados;
        if (buscar != null && !buscar.trim().isEmpty()) {
            // Búsqueda por nombre o departamento (HQL con lower())
            empleados = dao.buscar(buscar.trim());
            req.setAttribute("buscar", buscar.trim());  // mantiene el texto en el campo
        } else {
            empleados = dao.listarTodos();
        }

        // ── Datos que necesita lista.jsp ──────────────────────────────────
        req.setAttribute("empleados", empleados);        // List<Empleado>
        req.setAttribute("total",     empleados.size()); // int
        // ──────────────────────────────────────────────────────────────────

        return "lista.jsp";
    }

    // ── GET /app/empleados/ver?id=N ────────────────────────────────────────

    public String ver(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String idParam = req.getParameter("id");
        if (idParam == null) return "redirect:/app/empleados";

        Empleado empleado = dao.buscarPorId(Long.parseLong(idParam));
        if (empleado == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Empleado no encontrado");
            return null;
        }

        // ── Datos que necesita detalle.jsp ────────────────────────────────
        req.setAttribute("empleado", empleado);          // Empleado
        // ──────────────────────────────────────────────────────────────────

        return "detalle.jsp";
    }

    // ── GET /app/empleados/nuevo ───────────────────────────────────────────

    public String formularioNuevo(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        // ── Datos que necesita formulario.jsp (alta) ──────────────────────
        req.setAttribute("empleado",  new Empleado());   // Empleado vacío
        req.setAttribute("esNuevo",   true);             // boolean
        // ──────────────────────────────────────────────────────────────────

        return "formulario.jsp";
    }

    // ── GET /app/empleados/editar?id=N ─────────────────────────────────────

    public String formularioEditar(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Empleado empleado = dao.buscarPorId(Long.parseLong(req.getParameter("id")));
        if (empleado == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        // ── Datos que necesita formulario.jsp (edición) ───────────────────
        req.setAttribute("empleado",  empleado);         // Empleado con datos actuales
        req.setAttribute("esNuevo",   false);            // boolean
        // ──────────────────────────────────────────────────────────────────

        return "formulario.jsp";
    }

    // ── POST /app/empleados/guardar ────────────────────────────────────────

    public String guardar(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String  idParam       = req.getParameter("id");
        String  nombre        = req.getParameter("nombre");
        String  departamento  = req.getParameter("departamento");
        String  salarioStr    = req.getParameter("salario");
        boolean activo        = "on".equals(req.getParameter("activo"));
        boolean esNuevo       = idParam == null || idParam.isEmpty();

        // ── Validación ───────────────────────────────────────────────────
        Map<String, String> errores = new LinkedHashMap<>();

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio");
        }
        double salario = 0;
        if (salarioStr == null || salarioStr.trim().isEmpty()) {
            errores.put("salario", "El salario es obligatorio");
        } else {
            try {
                salario = Double.parseDouble(salarioStr.replace(',', '.'));
                if (salario <= 0) errores.put("salario", "El salario debe ser mayor que 0");
            } catch (NumberFormatException e) {
                errores.put("salario", "El salario debe ser un número");
            }
        }

        if (!errores.isEmpty()) {
            // Reconstruir objeto con los datos introducidos para no perderlos
            Empleado empleado = new Empleado();
            if (!esNuevo) {
                Empleado existente = dao.buscarPorId(Long.parseLong(idParam));
                if (existente != null) empleado = existente;
                empleado.setId(Long.parseLong(idParam));
            }
            empleado.setNombre(nombre);
            empleado.setDepartamento(departamento);
            empleado.setSalario(salario);
            empleado.setActivo(activo);

            // ── Datos que necesita formulario.jsp (con errores) ───────────
            req.setAttribute("empleado",  empleado);     // datos introducidos
            req.setAttribute("errores",   errores);      // Map<String,String>
            req.setAttribute("esNuevo",   esNuevo);
            // ─────────────────────────────────────────────────────────────

            return "formulario.jsp";  // forward (no redirect): conserva el request
        }

        // ── Persistencia en Hibernate ─────────────────────────────────────
        if (esNuevo) {
            dao.guardar(new Empleado(nombre.trim(), departamento, salario, activo));
        } else {
            Empleado empleado = dao.buscarPorId(Long.parseLong(idParam));
            empleado.setNombre(nombre.trim());
            empleado.setDepartamento(departamento);
            empleado.setSalario(salario);
            empleado.setActivo(activo);
            dao.actualizar(empleado);
        }

        return "redirect:/app/empleados";  // PRG: evita reenvío del POST al recargar
    }

    // ── POST /app/empleados/eliminar ───────────────────────────────────────

    public String eliminar(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        dao.eliminar(Long.parseLong(req.getParameter("id")));
        return "redirect:/app/empleados";
    }
}
