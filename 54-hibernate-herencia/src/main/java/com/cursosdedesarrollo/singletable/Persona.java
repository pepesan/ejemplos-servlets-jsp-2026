package com.cursosdedesarrollo.singletable;

import javax.persistence.*;

/**
 * SINGLE_TABLE — raíz de la jerarquía.
 *
 * DDL generado (una sola tabla con todas las columnas):
 *   CREATE TABLE personas (
 *     id    BIGINT PRIMARY KEY,
 *     tipo  VARCHAR NOT NULL,   ← columna discriminadora
 *     nombre VARCHAR NOT NULL,
 *     salario DOUBLE,           ← de Empleado  (nullable: otros tipos no la usan)
 *     empresa VARCHAR           ← de Cliente   (nullable: otros tipos no la usan)
 *   )
 *
 * Ventaja:  sin JOINs, consultas polimórficas muy rápidas.
 * Desventaja: columnas de subclases no pueden ser NOT NULL (viola 3FN).
 */
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING, length = 3)
public abstract class Persona {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    public Persona() {}
    public Persona(String nombre) { this.nombre = nombre; }

    public Long   getId()              { return id; }
    public void   setId(Long id)       { this.id = id; }
    public String getNombre()          { return nombre; }
    public void   setNombre(String n)  { this.nombre = n; }
}
