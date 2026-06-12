package com.cursosdedesarrollo.joined;

import javax.persistence.*;

/**
 * JOINED — raíz de la jerarquía.
 *
 * DDL generado (tabla base + tabla por subclase):
 *   CREATE TABLE vehiculos (id BIGINT PK, marca VARCHAR NOT NULL)
 *   CREATE TABLE coches    (id BIGINT PK → vehiculos.id, puertas INT NOT NULL)
 *   CREATE TABLE motos     (id BIGINT PK → vehiculos.id, cilindrada INT NOT NULL)
 *
 * Ventaja:  esquema normalizado; columnas de subclase pueden ser NOT NULL.
 * Desventaja: cada consulta polimórfica hace JOINs contra todas las subclases.
 */
@Entity
@Table(name = "vehiculos")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehiculo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String marca;

    public Vehiculo() {}
    public Vehiculo(String marca) { this.marca = marca; }

    public Long   getId()             { return id; }
    public void   setId(Long id)      { this.id = id; }
    public String getMarca()          { return marca; }
    public void   setMarca(String m)  { this.marca = m; }
}
