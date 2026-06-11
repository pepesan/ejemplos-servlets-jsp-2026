package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistroAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {
        // Si llegamos aquí, validate="true" en struts-config.xml garantiza
        // que RegistroForm.validate() (heredado de ValidatorForm) ya pasó sin errores.
        // No hay ninguna validación manual en esta clase.
        return mapping.findForward("exito");
    }
}
