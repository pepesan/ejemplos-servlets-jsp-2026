package com.cursosdedesarrollo.muchosamuchos;

/**
 * ManyToMany UNIDIRECCIONAL — lado pasivo.
 * Esta clase no tiene ninguna referencia a Producto.
 * No se puede navegar Etiqueta → Productos sin HQL.
 */
public class Etiqueta {

    private Long id;
    private String nombre;

    public Etiqueta() {}
    public Etiqueta(String nombre) { this.nombre = nombre; }

    public Long   getId()                { return id; }
    public void   setId(Long id)         { this.id = id; }
    public String getNombre()            { return nombre; }
    public void   setNombre(String n)    { this.nombre = n; }
}
