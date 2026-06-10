package com.cursosdedesarrollo;

import com.cursosdedesarrollo.modelo.Alumno;
import com.cursosdedesarrollo.modelo.AlumnoRepositorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de alumnos (C en MVC).
 *
 * <p>Cada método sigue siempre la misma estructura:</p>
 * <ol>
 *   <li>Lee parámetros HTTP del request ({@code req.getParameter(...)}).</li>
 *   <li>Invoca al repositorio (modelo) para obtener o modificar datos.</li>
 *   <li><strong>Pone los datos en el request con {@code setAttribute}</strong>
 *       para que la vista los lea con EL ({@code ${nombre}}).</li>
 *   <li>Devuelve el nombre del JSP o {@code "redirect:..."};
 *       es {@link FrontControllerServlet} quien ejecuta el forward/redirect.</li>
 * </ol>
 *
 * <h3>Tabla de métodos</h3>
 * <pre>
 *   Método              Ruta (registrada en FrontControllerServlet)
 *   ──────────────────────────────────────────────────────────────────────
 *   listar()            GET  /app/alumnos           → lista.jsp
 *   ver()               GET  /app/alumnos/ver        → detalle.jsp
 *   formularioNuevo()   GET  /app/alumnos/nuevo      → formulario.jsp
 *   formularioEditar()  GET  /app/alumnos/editar     → formulario.jsp
 *   guardar()           POST /app/alumnos/guardar    → formulario.jsp (error)
 *                                                    → redirect (ok)
 *   eliminar()          POST /app/alumnos/eliminar   → redirect
 * </pre>
 *
 * <h3>Contrato setAttribute → EL por vista</h3>
 * <pre>
 *   lista.jsp
 *     setAttribute("alumnos",   List&lt;Alumno&gt;) → ${alumnos}  en &lt;c:forEach&gt;
 *     setAttribute("total",     int)           → ${total}
 *     setAttribute("aprobados", long)          → ${aprobados}
 *     setAttribute("suspensos", long)          → ${suspensos}
 *     setAttribute("notaMedia", double)        → ${notaMedia} en &lt;fmt:formatNumber&gt;
 *
 *   detalle.jsp
 *     setAttribute("alumno", Alumno)           → ${alumno.nombre}, ${alumno.nota}…
 *
 *   formulario.jsp
 *     setAttribute("alumno",  Alumno)          → value="${alumno.nombre}"… (pre-relleno)
 *     setAttribute("esNuevo", boolean)         → &lt;c:if test="${esNuevo}"&gt;
 *     setAttribute("errores", Map&lt;String,String&gt;) → ${errores.nombre} (mensajes)
 * </pre>
 */
public class AlumnoController {

    private final AlumnoRepositorio repositorio = AlumnoRepositorio.instancia();

    // ── GET /app/alumnos ──────────────────────────────────────────────────

    public String listar(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        List<Alumno> alumnos = repositorio.listar();

        long   aprobados = 0;
        double suma      = 0;
        for (Alumno a : alumnos) {
            if (a.isAprobado()) aprobados++;
            suma += a.getNota();
        }
        double notaMedia = alumnos.isEmpty() ? 0 : suma / alumnos.size();

        // ── Datos que necesita lista.jsp ──────────────────────────────────
        req.setAttribute("alumnos",   alumnos);          // List<Alumno>
        req.setAttribute("total",     alumnos.size());   // int
        req.setAttribute("aprobados", aprobados);        // long
        req.setAttribute("suspensos", alumnos.size() - aprobados); // long
        req.setAttribute("notaMedia", notaMedia);        // double
        // ──────────────────────────────────────────────────────────────────

        return "lista.jsp";
    }

    // ── GET /app/alumnos/ver?id=N ─────────────────────────────────────────

    public String ver(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String idParam = req.getParameter("id");
        if (idParam == null) return "redirect:/app/alumnos";

        Alumno alumno = repositorio.buscarPorId(Integer.parseInt(idParam));
        if (alumno == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Alumno no encontrado");
            return null;
        }

        // ── Datos que necesita detalle.jsp ────────────────────────────────
        req.setAttribute("alumno", alumno);              // Alumno
        // ──────────────────────────────────────────────────────────────────

        return "detalle.jsp";
    }

    // ── GET /app/alumnos/nuevo ────────────────────────────────────────────

    public String formularioNuevo(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        // ── Datos que necesita formulario.jsp (alta) ──────────────────────
        req.setAttribute("alumno",  new Alumno());       // Alumno vacío
        req.setAttribute("esNuevo", true);               // boolean
        // ──────────────────────────────────────────────────────────────────

        return "formulario.jsp";
    }

    // ── GET /app/alumnos/editar?id=N ──────────────────────────────────────

    public String formularioEditar(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Alumno alumno = repositorio.buscarPorId(Integer.parseInt(req.getParameter("id")));
        if (alumno == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        // ── Datos que necesita formulario.jsp (edición) ───────────────────
        req.setAttribute("alumno",  alumno);             // Alumno con datos actuales
        req.setAttribute("esNuevo", false);              // boolean
        // ──────────────────────────────────────────────────────────────────

        return "formulario.jsp";
    }

    // ── POST /app/alumnos/guardar ─────────────────────────────────────────

    public String guardar(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String  idParam = req.getParameter("id");
        String  nombre  = req.getParameter("nombre");
        String  email   = req.getParameter("email");
        String  notaStr = req.getParameter("nota");
        boolean activo  = "on".equals(req.getParameter("activo"));
        boolean esNuevo = idParam == null || idParam.isEmpty();

        // ── Validación ───────────────────────────────────────────────────
        Map<String, String> errores = new LinkedHashMap<>();

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio");
        }
        double nota = 0;
        if (notaStr == null || notaStr.trim().isEmpty()) {
            errores.put("nota", "La nota es obligatoria");
        } else {
            try {
                nota = Double.parseDouble(notaStr.replace(',', '.'));
                if (nota < 0 || nota > 10) errores.put("nota", "La nota debe estar entre 0 y 10");
            } catch (NumberFormatException e) {
                errores.put("nota", "La nota debe ser un número");
            }
        }

        if (!errores.isEmpty()) {
            // Reconstruir objeto para no perder lo que el usuario había escrito
            Alumno alumno = esNuevo ? new Alumno()
                    : repositorio.buscarPorId(Integer.parseInt(idParam));
            if (alumno == null) alumno = new Alumno();
            if (!esNuevo) alumno.setId(Integer.parseInt(idParam));
            alumno.setNombre(nombre);
            alumno.setEmail(email);
            alumno.setNota(nota);
            alumno.setActivo(activo);

            // ── Datos que necesita formulario.jsp (con errores) ───────────
            req.setAttribute("alumno",  alumno);         // Alumno con los datos introducidos
            req.setAttribute("errores", errores);        // Map<String,String> campo→mensaje
            req.setAttribute("esNuevo", esNuevo);        // boolean
            // ─────────────────────────────────────────────────────────────

            return "formulario.jsp";   // forward, no redirect: conserva el request
        }

        // ── Persistencia ─────────────────────────────────────────────────
        if (esNuevo) {
            repositorio.crear(new Alumno(nombre.trim(), email, nota, activo));
        } else {
            Alumno alumno = repositorio.buscarPorId(Integer.parseInt(idParam));
            alumno.setNombre(nombre.trim());
            alumno.setEmail(email);
            alumno.setNota(nota);
            alumno.setActivo(activo);
            repositorio.actualizar(alumno);
        }

        return "redirect:/app/alumnos";   // PRG: evita reenvío del POST al recargar
    }

    // ── POST /app/alumnos/eliminar ────────────────────────────────────────

    public String eliminar(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        repositorio.eliminar(Integer.parseInt(req.getParameter("id")));
        return "redirect:/app/alumnos";
    }
}
