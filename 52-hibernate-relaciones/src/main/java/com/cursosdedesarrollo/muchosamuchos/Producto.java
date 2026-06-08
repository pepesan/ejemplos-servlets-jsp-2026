package com.cursosdedesarrollo.muchosamuchos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ManyToMany UNIDIRECCIONAL — lado dueño.
 *
 * Solo Producto conoce a Etiqueta. La tabla intermedia productos_etiquetas
 * se genera automáticamente por Hibernate.
 * Etiqueta no tiene ninguna referencia a Producto.
 *
 * Comparación con bidireccional (Pelicula ↔ Actor):
 *   Unidireccional:  solo Producto → Etiqueta
 *   Bidireccional:   Pelicula → Actor  Y  Actor → Pelicula
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @ManyToMany
    @JoinTable(
        name = "productos_etiquetas",
        joinColumns        = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
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
