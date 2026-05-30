package com.cursosdedesarrollo;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SaludoForm extends ActionForm {

    private String nombre = "";

    public String getNombre()               { return nombre; }
    public void   setNombre(String nombre)  { this.nombre = nombre; }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        nombre = "";
    }
}
