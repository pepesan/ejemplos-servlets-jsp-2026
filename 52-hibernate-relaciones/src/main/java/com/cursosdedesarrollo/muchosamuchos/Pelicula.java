package com.cursosdedesarrollo.muchosamuchos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ManyToMany BIDIRECCIONAL — lado dueño.
 *
 * Pelicula tiene la @JoinTable (tabla actores_peliculas).
 * Actor tiene @ManyToMany(mappedBy="actores") como lado inverso.
 *
 * Para mantener coherencia en memoria: al añadir un Actor a una Pelicula
 * también hay que añadir la Pelicula al Actor.
 */
@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @ManyToMany
    @JoinTable(
        name = "actores_peliculas",
        joinColumns        = @JoinColumn(name = "pelicula_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actores = new ArrayList<Actor>();

    public Pelicula() {}
    public Pelicula(String titulo) { this.titulo = titulo; }

    /** Helper: añade el actor a esta película y la película al actor. */
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
