package com.cursosdedesarrollo;

public class Contacto {

    public static final String AMIGO    = "AMIGO";
    public static final String TRABAJO  = "TRABAJO";
    public static final String FAMILIA  = "FAMILIA";

    private int    id;
    private String nombre;
    private String email;
    private String telefono;
    private String categoria;

    public Contacto() {}

    public Contacto(String nombre, String email, String telefono, String categoria) {
        this.nombre    = nombre;
        this.email     = email;
        this.telefono  = telefono;
        this.categoria = categoria;
    }

    public int    getId()                   { return id; }
    public void   setId(int id)             { this.id = id; }

    public String getNombre()               { return nombre; }
    public void   setNombre(String nombre)  { this.nombre = nombre; }

    public String getEmail()                { return email; }
    public void   setEmail(String email)    { this.email = email; }

    public String getTelefono()                  { return telefono; }
    public void   setTelefono(String telefono)   { this.telefono = telefono; }

    public String getCategoria()                 { return categoria; }
    public void   setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return "Contacto{id=" + id + ", nombre='" + nombre + "', categoria='" + categoria + "'}";
    }
}
