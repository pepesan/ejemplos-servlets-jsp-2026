package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmpleadoAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {
        // Si llegamos aquí, BeanValidationForm.validate() ya ejecutó las anotaciones
        // de EmpleadoForm y no encontró ninguna violación.
        return mapping.findForward("exito");
    }
}
