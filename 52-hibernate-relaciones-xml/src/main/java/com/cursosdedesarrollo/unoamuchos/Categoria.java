package com.cursosdedesarrollo.unoamuchos;

import java.util.ArrayList;
import java.util.List;

/**
 * OneToMany UNIDIRECCIONAL — lado dueño ("uno").
 *
 * El mapeo HBM genera una FK categoria_id en la tabla articulos.
 * Articulo no tiene campo Categoria: relación unidireccional.
 *
 *   Comparación con bidireccional:
 *   Unidireccional:  solo Categoria conoce a Articulo
 *   Bidireccional:   Empleado también conoce a Departamento
 */
public class Categoria {

    private Long id;
    private String nombre;
    private List<Articulo> articulos = new ArrayList<Articulo>();

    public Categoria() {}
    public Categoria(String nombre) { this.nombre = nombre; }

    public Long           getId()                         { return id; }
    public void           setId(Long id)                  { this.id = id; }
    public String         getNombre()                     { return nombre; }
    public void           setNombre(String n)             { this.nombre = n; }
    public List<Articulo> getArticulos()                  { return articulos; }
    public void           setArticulos(List<Articulo> a)  { this.articulos = a; }
}
