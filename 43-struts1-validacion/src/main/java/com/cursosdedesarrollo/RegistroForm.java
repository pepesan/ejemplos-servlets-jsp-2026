package com.cursosdedesarrollo;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * ValidatorForm (en lugar de ActionForm) delega validate() al Struts Validator.
 * Las reglas de validación viven en validation.xml, no en el código Java.
 */
public class RegistroForm extends ValidatorForm {

    private String nombre = "";
    private String email  = "";
    private String edad   = "";

    public String getNombre()             { return nombre; }
    public String getEmail()              { return email; }
    public String getEdad()               { return edad; }

    public void setNombre(String nombre)  { this.nombre = nombre; }
    public void setEmail(String email)    { this.email  = email; }
    public void setEdad(String edad)      { this.edad   = edad; }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        nombre = "";
        email  = "";
        edad   = "";
    }
}
