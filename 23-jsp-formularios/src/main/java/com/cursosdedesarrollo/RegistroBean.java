package com.cursosdedesarrollo;

/** JavaBean que representa los datos del formulario de registro. */
public class RegistroBean {

    private String nombre   = "";
    private String email    = "";
    private String password = "";
    private String edad     = "";

    public RegistroBean() {}

    public RegistroBean(String nombre, String email, String password, String edad) {
        this.nombre   = nombre   != null ? nombre   : "";
        this.email    = email    != null ? email    : "";
        this.password = password != null ? password : "";
        this.edad     = edad     != null ? edad     : "";
    }

    public String getNombre()   { return nombre; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getEdad()     { return edad; }

    public void setNombre(String v)   { this.nombre   = v != null ? v : ""; }
    public void setEmail(String v)    { this.email    = v != null ? v : ""; }
    public void setPassword(String v) { this.password = v != null ? v : ""; }
    public void setEdad(String v)     { this.edad     = v != null ? v : ""; }
}
