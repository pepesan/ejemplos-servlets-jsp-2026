package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrearEmpleadoAction extends Action {

    private final EmpleadoDao dao;

    public CrearEmpleadoAction()          { this.dao = new EmpleadoDao(); }
    CrearEmpleadoAction(EmpleadoDao dao)  { this.dao = dao; }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        if ("GET".equals(request.getMethod())) {
            return mapping.findForward("formulario");
        }

        EmpleadoForm ef = (EmpleadoForm) form;
        ActionErrors errors = ef.validate(mapping, request);

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("formulario");
        }

        dao.guardar(new Empleado(
                ef.getNombre().trim(),
                ef.getDepartamento().trim(),
                Double.parseDouble(ef.getSalario().trim())
        ));

        return new ActionForward(request.getContextPath() + "/listar.do", true);
    }
}
