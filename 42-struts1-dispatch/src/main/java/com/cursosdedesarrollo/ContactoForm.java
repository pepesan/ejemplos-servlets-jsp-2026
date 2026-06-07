package com.cursosdedesarrollo;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class ContactoForm extends ActionForm {

    private String id       = "";
    private String nombre   = "";
    private String email    = "";
    private String telefono = "";

    public String getId()       { return id; }
    public String getNombre()   { return nombre; }
    public String getEmail()    { return email; }
    public String getTelefono() { return telefono; }

    public void setId(String id)             { this.id       = id; }
    public void setNombre(String nombre)     { this.nombre   = nombre; }
    public void setEmail(String email)       { this.email    = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        id       = "";
        nombre   = "";
        email    = "";
        telefono = "";
    }
}
