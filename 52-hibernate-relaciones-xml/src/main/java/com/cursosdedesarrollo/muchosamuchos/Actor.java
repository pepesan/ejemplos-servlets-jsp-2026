package com.cursosdedesarrollo.muchosamuchos;

import java.util.ArrayList;
import java.util.List;

/**
 * ManyToMany BIDIRECCIONAL — lado inverso.
 *
 * inverse="true" en Actor.hbm.xml: la tabla actores_peliculas la gestiona Pelicula.
 * Actor puede navegar sus películas gracias a la relación inversa.
 */
public class Actor {

    private Long id;
    private String nombre;
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
