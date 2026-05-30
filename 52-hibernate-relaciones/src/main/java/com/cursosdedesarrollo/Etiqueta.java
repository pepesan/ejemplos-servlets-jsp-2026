package com.cursosdedesarrollo;

import javax.persistence.*;

/**
 * ManyToMany UNIDIRECCIONAL — lado pasivo.
 * Esta clase no tiene ninguna referencia a Producto.
 * No se puede navegar Etiqueta → Productos sin HQL.
 */
@Entity
@Table(name = "etiquetas")
public class Etiqueta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String nombre;

    public Etiqueta() {}
    public Etiqueta(String nombre) { this.nombre = nombre; }

    public Long   getId()                { return id; }
    public void   setId(Long id)         { this.id = id; }
    public String getNombre()            { return nombre; }
    public void   setNombre(String n)    { this.nombre = n; }
}
