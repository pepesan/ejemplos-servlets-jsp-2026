package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EliminarEmpleadoAction extends Action {

    private final EmpleadoDao dao;

    public EliminarEmpleadoAction()          { this.dao = new EmpleadoDao(); }
    EliminarEmpleadoAction(EmpleadoDao dao)  { this.dao = dao; }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                dao.eliminar(Long.parseLong(idStr.trim()));
            } catch (NumberFormatException ignored) {}
        }
        return new ActionForward(request.getContextPath() + "/listar.do", true);
    }
}
