package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistroAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {
        // Con DynaValidatorForm no hay clase Java con getters: los valores
        // se leen con .get("nombreDeCampo"), que devuelve Object.
        // El tipo real es el declarado en <form-property type="...">.
        DynaValidatorForm dynaForm = (DynaValidatorForm) form;

        String nombre = (String) dynaForm.get("nombre");
        String email  = (String) dynaForm.get("email");
        String edad   = (String) dynaForm.get("edad");

        request.setAttribute("nombre", nombre);
        request.setAttribute("email",  email);
        request.setAttribute("edad",   edad);

        return mapping.findForward("exito");
    }
}
