package com.cursosdedesarrollo.unoamuchos;

import javax.persistence.*;

/**
 * OneToMany UNIDIRECCIONAL — lado pasivo ("muchos").
 *
 * Esta clase no tiene ningún campo @ManyToOne. Categoria tiene la FK
 * en su tabla via @JoinColumn, pero Articulo no sabe a qué Categoria
 * pertenece. No se puede navegar Articulo → Categoria.
 */
@Entity
@Table(name = "articulos")
public class Articulo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    public Articulo() {}
    public Articulo(String titulo) { this.titulo = titulo; }

    public Long   getId()                  { return id; }
    public void   setId(Long id)           { this.id = id; }
    public String getTitulo()              { return titulo; }
    public void   setTitulo(String t)      { this.titulo = t; }
}
