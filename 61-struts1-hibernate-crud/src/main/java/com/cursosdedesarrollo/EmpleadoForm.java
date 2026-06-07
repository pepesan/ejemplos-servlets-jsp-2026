package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class EmpleadoForm extends ActionForm {

    private String id           = "";   // vacío = alta; número = edición
    private String nombre       = "";
    private String departamento = "";
    private String salario      = "";
    private String busqueda     = "";   // campo de búsqueda en lista.jsp

    public String getId()                           { return id; }
    public String getNombre()                       { return nombre; }
    public String getDepartamento()                 { return departamento; }
    public String getSalario()                      { return salario; }
    public String getBusqueda()                     { return busqueda; }

    public void setId(String id)                    { this.id           = id; }
    public void setNombre(String nombre)            { this.nombre       = nombre; }
    public void setDepartamento(String d)           { this.departamento = d; }
    public void setSalario(String salario)          { this.salario      = salario; }
    public void setBusqueda(String busqueda)        { this.busqueda     = busqueda; }

    /** Valida sólo los campos del formulario de alta/edición, no el de búsqueda. */
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
                if (Double.parseDouble(salario.trim()) < 0)
                    errors.add("salario", new ActionMessage("error.salario.positivo"));
            } catch (NumberFormatException e) {
                errors.add("salario", new ActionMessage("error.salario.numerico"));
            }
        }

        return errors;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        id           = "";
        nombre       = "";
        departamento = "";
        salario      = "";
        busqueda     = "";
    }
}
