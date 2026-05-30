package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListarEmpleadosAction extends Action {

    private final EmpleadoDao dao;

    public ListarEmpleadosAction()           { this.dao = new EmpleadoDao(); }
    ListarEmpleadosAction(EmpleadoDao dao)   { this.dao = dao; }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("empleados", dao.listarTodos());
        return mapping.findForward("lista");
    }
}
