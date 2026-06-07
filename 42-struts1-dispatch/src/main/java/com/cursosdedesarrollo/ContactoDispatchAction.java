package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Un único DispatchAction gestiona todas las operaciones CRUD.
 * El parámetro "method" de la URL determina qué método se invoca:
 *
 *   GET  /contactos.do?method=listar        → listar()
 *   GET  /contactos.do?method=ver&id=X      → ver()
 *   GET  /contactos.do?method=nuevo         → nuevo()
 *   GET  /contactos.do?method=editar&id=X   → editar()
 *   POST /contactos.do?method=guardar       → guardar()
 *   POST /contactos.do?method=eliminar&id=X → eliminar()
 *
 * Contrasta con el módulo 41 donde cada operación tiene su propia clase Action.
 */
public class ContactoDispatchAction extends DispatchAction {

    public ActionForward listar(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("contactos", ContactoRepositorio.listar());
        return mapping.findForward("lista");
    }

    public ActionForward ver(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request, HttpServletResponse response) {

        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                Contacto c = ContactoRepositorio.buscarPorId(Integer.parseInt(idStr.trim()));
                if (c != null) {
                    request.setAttribute("contacto", c);
                    return mapping.findForward("detalle");
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return mapping.findForward("exito");
    }

    public ActionForward nuevo(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) {

        // Struts ya llamó a ContactoForm.reset() → formulario vacío.
        return mapping.findForward("formulario");
    }

    public ActionForward editar(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {

        ContactoForm contactoForm = (ContactoForm) form;
        int id;
        try {
            id = Integer.parseInt(contactoForm.getId());
        } catch (NumberFormatException e) {
            return mapping.findForward("exito");
        }

        Contacto c = ContactoRepositorio.buscarPorId(id);
        if (c == null) {
            return mapping.findForward("exito");
        }

        contactoForm.setId(String.valueOf(c.getId()));
        contactoForm.setNombre(c.getNombre());
        contactoForm.setEmail(c.getEmail());
        contactoForm.setTelefono(c.getTelefono());
        return mapping.findForward("formulario");
    }

    public ActionForward guardar(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        ContactoForm contactoForm = (ContactoForm) form;
        ActionErrors errors = validar(contactoForm);

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("formulario");
        }

        ContactoRepositorio.guardar(construirContacto(contactoForm));
        return mapping.findForward("exito");
    }

    public ActionForward eliminar(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response) {

        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                ContactoRepositorio.eliminar(Integer.parseInt(idStr.trim()));
            } catch (NumberFormatException ignored) {
            }
        }
        return mapping.findForward("exito");
    }

    // package-visible para tests
    ActionErrors validar(ContactoForm f) {
        ActionErrors errors = new ActionErrors();
        if (f.getNombre() == null || f.getNombre().trim().isEmpty()) {
            errors.add("nombre", new ActionMessage("error.nombre.requerido"));
        }
        if (f.getEmail() == null || f.getEmail().trim().isEmpty()) {
            errors.add("email", new ActionMessage("error.email.requerido"));
        }
        return errors;
    }

    private Contacto construirContacto(ContactoForm f) {
        String idStr = f.getId();
        int id = (idStr == null || idStr.trim().isEmpty()) ? 0 : Integer.parseInt(idStr.trim());
        Contacto c = (id > 0) ? ContactoRepositorio.buscarPorId(id) : null;
        if (c == null) {
            c = new Contacto();
        }
        c.setNombre(f.getNombre().trim());
        c.setEmail(f.getEmail().trim());
        c.setTelefono(f.getTelefono() == null ? "" : f.getTelefono().trim());
        return c;
    }
}
