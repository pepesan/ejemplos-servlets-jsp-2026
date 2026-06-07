package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditarAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        ContactoForm contactoForm = (ContactoForm) form;

        int id;
        try {
            id = Integer.parseInt(contactoForm.getId());
        } catch (NumberFormatException e) {
            return mapping.findForward("lista");
        }

        Contacto c = ContactoRepositorio.buscarPorId(id);
        if (c == null) {
            return mapping.findForward("lista");
        }

        // Cargamos los datos del repositorio en el form bean para que la vista los muestre.
        contactoForm.setId(String.valueOf(c.getId()));
        contactoForm.setNombre(c.getNombre());
        contactoForm.setEmail(c.getEmail());
        contactoForm.setTelefono(c.getTelefono());

        return mapping.findForward("formulario");
    }
}
