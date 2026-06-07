package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuardarAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        ContactoForm contactoForm = (ContactoForm) form;
        ActionErrors errors = validar(contactoForm);

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("formulario");
        }

        Contacto c = construirContacto(contactoForm);
        ContactoRepositorio.guardar(c);

        // Patrón PRG: redirect evita reenvío duplicado al pulsar F5.
        return mapping.findForward("lista");
    }

    private ActionErrors validar(ContactoForm f) {
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
