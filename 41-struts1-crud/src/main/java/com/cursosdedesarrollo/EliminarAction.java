package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EliminarAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                ContactoRepositorio.eliminar(Integer.parseInt(idStr.trim()));
            } catch (NumberFormatException ignored) {
            }
        }

        // PRG: siempre redirige a la lista para evitar reenvíos al recargar.
        return mapping.findForward("lista");
    }
}
