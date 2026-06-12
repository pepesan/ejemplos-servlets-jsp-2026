package com.cursosdedesarrollo.muchosamuchos;

import java.util.ArrayList;
import java.util.List;

/**
 * ManyToMany UNIDIRECCIONAL — lado dueño.
 *
 * Solo Producto conoce a Etiqueta. La tabla intermedia productos_etiquetas
 * se genera por el mapeo HBM de Producto.
 * Etiqueta no tiene ninguna referencia a Producto.
 */
public class Producto {

    private Long id;
    private String nombre;
    private List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();

    public Producto() {}
    public Producto(String nombre) { this.nombre = nombre; }

    public Long           getId()                        { return id; }
    public void           setId(Long id)                 { this.id = id; }
    public String         getNombre()                    { return nombre; }
    public void           setNombre(String n)            { this.nombre = n; }
    public List<Etiqueta> getEtiquetas()                 { return etiquetas; }
    public void           setEtiquetas(List<Etiqueta> e) { this.etiquetas = e; }
}
