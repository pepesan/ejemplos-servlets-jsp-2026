package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaludoAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        SaludoForm saludoForm = (SaludoForm) form;
        String errorKey = validarNombre(saludoForm.getNombre());

        if (errorKey != null) {
            if ("POST".equals(request.getMethod())) {
                ActionErrors errors = new ActionErrors();
                errors.add("nombre", new ActionMessage(errorKey));
                saveErrors(request, errors);
            }
            return mapping.findForward("entrada");
        }

        request.setAttribute("saludo",
                "¡Hola, " + saludoForm.getNombre().trim() + "! Bienvenido a Struts 1.x.");
        return mapping.findForward("exito");
    }

    static String validarNombre(String v) {
        if (v == null || v.trim().isEmpty()) return "error.nombre.requerido";
        return null;
    }
}
