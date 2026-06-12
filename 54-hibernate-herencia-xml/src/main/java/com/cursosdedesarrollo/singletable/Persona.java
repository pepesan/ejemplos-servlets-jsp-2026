package com.cursosdedesarrollo.singletable;

/**
 * SINGLE_TABLE — raíz de la jerarquía. Sin anotaciones JPA.
 * La estrategia, la tabla y la columna discriminadora se declaran en Persona.hbm.xml.
 */
public abstract class Persona {

    private Long id;
    private String nombre;

    public Persona() {}
    public Persona(String nombre) { this.nombre = nombre; }

    public Long   getId()              { return id; }
    public void   setId(Long id)       { this.id = id; }
    public String getNombre()          { return nombre; }
    public void   setNombre(String n)  { this.nombre = n; }
}
