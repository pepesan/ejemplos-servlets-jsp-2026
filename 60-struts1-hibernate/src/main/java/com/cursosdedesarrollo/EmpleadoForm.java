package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class EmpleadoForm extends ActionForm {

    private String nombre       = "";
    private String departamento = "";
    private String salario      = "";

    public String getNombre()                          { return nombre; }
    public void   setNombre(String nombre)             { this.nombre = nombre; }
    public String getDepartamento()                    { return departamento; }
    public void   setDepartamento(String departamento) { this.departamento = departamento; }
    public String getSalario()                         { return salario; }
    public void   setSalario(String salario)           { this.salario = salario; }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (nombre == null || nombre.trim().isEmpty())
            errors.add("nombre", new ActionMessage("error.nombre.requerido"));

        if (departamento == null || departamento.trim().isEmpty())
            errors.add("departamento", new ActionMessage("error.departamento.requerido"));

        if (salario == null || salario.trim().isEmpty()) {
            errors.add("salario", new ActionMessage("error.salario.requerido"));
        } else {
            try {
                double s = Double.parseDouble(salario.trim());
                if (s < 0) errors.add("salario", new ActionMessage("error.salario.positivo"));
            } catch (NumberFormatException e) {
                errors.add("salario", new ActionMessage("error.salario.numerico"));
            }
        }

        return errors;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        nombre       = "";
        departamento = "";
        salario      = "";
    }
}
