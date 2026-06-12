package com.cursosdedesarrollo.muchosamuchos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ManyToMany BIDIRECCIONAL — lado inverso.
 *
 * mappedBy="actores" significa: "la tabla de unión está declarada en el
 * campo 'actores' de Pelicula, no aquí".
 * Actor puede navegar sus películas gracias a la relación inversa.
 */
@Entity
@Table(name = "actores")
public class Actor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    /** Lado inverso: no genera tabla. La tabla actores_peliculas la gestiona Pelicula. */
    @ManyToMany(mappedBy = "actores")
    private List<Pelicula> peliculas = new ArrayList<Pelicula>();

    public Actor() {}
    public Actor(String nombre) { this.nombre = nombre; }

    public Long           getId()                        { return id; }
    public void           setId(Long id)                 { this.id = id; }
    public String         getNombre()                    { return nombre; }
    public void           setNombre(String n)            { this.nombre = n; }
    public List<Pelicula> getPeliculas()                 { return peliculas; }
    public void           setPeliculas(List<Pelicula> p) { this.peliculas = p; }
}
