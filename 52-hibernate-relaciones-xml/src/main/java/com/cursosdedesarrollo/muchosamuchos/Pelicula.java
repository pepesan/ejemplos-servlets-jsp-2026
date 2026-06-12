package com.cursosdedesarrollo.muchosamuchos;

import java.util.ArrayList;
import java.util.List;

/**
 * ManyToMany BIDIRECCIONAL — lado dueño.
 *
 * Pelicula tiene la tabla de unión actores_peliculas declarada en Pelicula.hbm.xml.
 * Actor tiene inverse="true": no inserta en la tabla, solo navega.
 *
 * Para mantener coherencia en memoria: al añadir un Actor a una Pelicula
 * también hay que añadir la Pelicula al Actor.
 */
public class Pelicula {

    private Long id;
    private String titulo;
    private List<Actor> actores = new ArrayList<Actor>();

    public Pelicula() {}
    public Pelicula(String titulo) { this.titulo = titulo; }

    public void addActor(Actor a) {
        actores.add(a);
        a.getPeliculas().add(this);
    }

    public Long        getId()                    { return id; }
    public void        setId(Long id)             { this.id = id; }
    public String      getTitulo()                { return titulo; }
    public void        setTitulo(String t)        { this.titulo = t; }
    public List<Actor> getActores()               { return actores; }
    public void        setActores(List<Actor> a)  { this.actores = a; }
}
