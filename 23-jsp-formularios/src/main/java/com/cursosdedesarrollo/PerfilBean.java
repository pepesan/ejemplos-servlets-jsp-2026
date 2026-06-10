package com.cursosdedesarrollo;

/** JavaBean que representa el perfil de un usuario existente. */
public class PerfilBean {

    private int     id;
    private String  nombre   = "";
    private String  email    = "";
    private String  telefono = "";
    private String  ciudad   = "";
    private String  rol      = "usuario";
    private boolean activo   = true;

    public PerfilBean() {}

    public PerfilBean(int id, String nombre, String email, String telefono,
                      String ciudad, String rol, boolean activo) {
        this.id       = id;
        this.nombre   = nombre   != null ? nombre   : "";
        this.email    = email    != null ? email    : "";
        this.telefono = telefono != null ? telefono : "";
        this.ciudad   = ciudad   != null ? ciudad   : "";
        this.rol      = rol      != null ? rol      : "usuario";
        this.activo   = activo;
    }

    public int     getId()       { return id; }
    public String  getNombre()   { return nombre; }
    public String  getEmail()    { return email; }
    public String  getTelefono() { return telefono; }
    public String  getCiudad()   { return ciudad; }
    public String  getRol()      { return rol; }
    public boolean isActivo()    { return activo; }

    public void setId(int v)          { this.id       = v; }
    public void setNombre(String v)   { this.nombre   = v != null ? v : ""; }
    public void setEmail(String v)    { this.email    = v != null ? v : ""; }
    public void setTelefono(String v) { this.telefono = v != null ? v : ""; }
    public void setCiudad(String v)   { this.ciudad   = v != null ? v : ""; }
    public void setRol(String v)      { this.rol      = v != null ? v : "usuario"; }
    public void setActivo(boolean v)  { this.activo   = v; }
}
