package com.cursosdedesarrollo;

import javax.persistence.*;

/**
 * SINGLE_TABLE: una sola tabla "personas" con columna discriminadora "tipo".
 * Ventaja: sin JOINs, consultas rápidas.
 * Inconveniente: columnas de subclases permiten NULL (no se pueden añadir NOT NULL).
 */
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    public Persona() {}
    public Persona(String nombre) { this.nombre = nombre; }

    public Long   getId()                  { return id; }
    public void   setId(Long id)           { this.id = id; }
    public String getNombre()              { return nombre; }
    public void   setNombre(String n)      { this.nombre = n; }
}
