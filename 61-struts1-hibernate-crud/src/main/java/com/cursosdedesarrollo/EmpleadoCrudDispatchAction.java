package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CRUD completo de Empleado usando DispatchAction + Hibernate.
 *
 *   GET  /empleados.do?method=listar                  → listar()
 *   GET  /empleados.do?method=listar&busqueda=texto   → listar() con filtro
 *   GET  /empleados.do?method=mostrar&id=X            → mostrar()
 *   GET  /empleados.do?method=nuevo                   → nuevo()
 *   GET  /empleados.do?method=editar&id=X             → editar()
 *   POST /empleados.do?method=guardar                 → guardar()
 *   POST /empleados.do?method=eliminar&id=X           → eliminar()
 */
public class EmpleadoCrudDispatchAction extends DispatchAction {

    private final EmpleadoDao dao;

    public EmpleadoCrudDispatchAction()         { this.dao = new EmpleadoDao(); }
    EmpleadoCrudDispatchAction(EmpleadoDao dao) { this.dao = dao; }

    // ── listar ───────────────────────────────────────────────────────────────

    public ActionForward listar(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {

        EmpleadoForm ef = (EmpleadoForm) form;
        String busqueda = ef.getBusqueda();

        if (busqueda != null && !busqueda.trim().isEmpty()) {
            request.setAttribute("empleados", dao.buscar(busqueda.trim()));
            request.setAttribute("busqueda", busqueda.trim());
        } else {
            request.setAttribute("empleados", dao.listarTodos());
        }
        return mapping.findForward("lista");
    }

    // ── mostrar ──────────────────────────────────────────────────────────────

    public ActionForward mostrar(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        EmpleadoForm ef = (EmpleadoForm) form;
        Long id = parseLong(ef.getId());
        if (id == null) return mapping.findForward("exito");

        Empleado e = dao.buscarPorId(id);
        if (e == null) return mapping.findForward("exito");

        request.setAttribute("empleado", e);
        return mapping.findForward("detalle");
    }

    // ── nuevo ────────────────────────────────────────────────────────────────

    public ActionForward nuevo(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) {

        // Struts ya llamó a reset() → form vacío.
        return mapping.findForward("formulario");
    }

    // ── editar ───────────────────────────────────────────────────────────────

    public ActionForward editar(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {

        EmpleadoForm ef = (EmpleadoForm) form;
        Long id = parseLong(ef.getId());
        if (id == null) return mapping.findForward("exito");

        Empleado e = dao.buscarPorId(id);
        if (e == null) return mapping.findForward("exito");

        ef.setId(String.valueOf(e.getId()));
        ef.setNombre(e.getNombre());
        ef.setDepartamento(e.getDepartamento());
        ef.setSalario(String.valueOf(e.getSalario()));
        return mapping.findForward("formulario");
    }

    // ── guardar (alta o modificación) ────────────────────────────────────────

    public ActionForward guardar(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        EmpleadoForm ef = (EmpleadoForm) form;
        ActionErrors errors = ef.validate(mapping, request);

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("formulario");
        }

        Long id = parseLong(ef.getId());
        double salario = Double.parseDouble(ef.getSalario().trim());

        if (id != null && id > 0) {
            Empleado e = dao.buscarPorId(id);
            if (e != null) {
                e.setNombre(ef.getNombre().trim());
                e.setDepartamento(ef.getDepartamento().trim());
                e.setSalario(salario);
                dao.actualizar(e);
            }
        } else {
            dao.guardar(new Empleado(ef.getNombre().trim(), ef.getDepartamento().trim(), salario));
        }

        return mapping.findForward("exito");  // redirect → listar (PRG)
    }

    // ── eliminar ─────────────────────────────────────────────────────────────

    public ActionForward eliminar(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response) {

        Long id = parseLong(request.getParameter("id"));
        if (id != null) dao.eliminar(id);
        return mapping.findForward("exito");  // redirect → listar (PRG)
    }

    // ─────────────────────────────────────────────────────────────────────────

    private static Long parseLong(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        try { return Long.parseLong(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }
}
