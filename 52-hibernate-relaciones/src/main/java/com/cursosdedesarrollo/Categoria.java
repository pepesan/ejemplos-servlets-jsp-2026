package com.cursosdedesarrollo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OneToMany UNIDIRECCIONAL — lado dueño ("uno").
 *
 * @JoinColumn genera una FK categoria_id en la tabla articulos sin necesitar
 * un @ManyToOne en Articulo. Sin @JoinColumn, Hibernate crearía una tabla
 * de unión intermedia (categorias_articulos), lo que es menos eficiente.
 *
 *   articulos.categoria_id → categorias.id
 *
 * Comparación con bidireccional:
 *   Unidireccional:  solo Categoria conoce a Articulo
 *   Bidireccional:   Empleado también conoce a Departamento (@ManyToOne)
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "categoria_id")  // FK en tabla articulos, sin @ManyToOne en Articulo
    private List<Articulo> articulos = new ArrayList<Articulo>();

    public Categoria() {}
    public Categoria(String nombre) { this.nombre = nombre; }

    public Long          getId()                         { return id; }
    public void          setId(Long id)                  { this.id = id; }
    public String        getNombre()                     { return nombre; }
    public void          setNombre(String n)             { this.nombre = n; }
    public List<Articulo> getArticulos()                 { return articulos; }
    public void          setArticulos(List<Articulo> a)  { this.articulos = a; }
}
