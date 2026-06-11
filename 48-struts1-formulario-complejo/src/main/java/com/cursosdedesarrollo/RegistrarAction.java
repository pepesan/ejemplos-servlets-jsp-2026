package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrarAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // La validación ya la hizo ActionForm.validate() antes de llegar aquí.
        // Si llegamos, el formulario es válido.
        RegistroForm f = (RegistroForm) form;
        request.setAttribute("registro", f);
        return mapping.findForward("exito");
    }
}
