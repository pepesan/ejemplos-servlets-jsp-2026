package com.cursosdedesarrollo.unoamuchos;

/**
 * OneToMany UNIDIRECCIONAL — lado pasivo ("muchos").
 *
 * Esta clase no tiene ningún campo Categoria. Categoria tiene la FK
 * via su mapeo hbm.xml, pero Articulo no sabe a qué Categoria pertenece.
 */
public class Articulo {

    private Long id;
    private String titulo;

    public Articulo() {}
    public Articulo(String titulo) { this.titulo = titulo; }

    public Long   getId()                  { return id; }
    public void   setId(Long id)           { this.id = id; }
    public String getTitulo()              { return titulo; }
    public void   setTitulo(String t)      { this.titulo = t; }
}
