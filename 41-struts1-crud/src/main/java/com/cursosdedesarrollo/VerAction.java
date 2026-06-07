package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
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

        return mapping.findForward("lista");
    }
}
