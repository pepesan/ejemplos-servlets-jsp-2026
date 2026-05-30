package com.cursosdedesarrollo;

import javax.persistence.*;

/**
 * JOINED: tabla "vehiculos" + tabla por subclase ("coches", "motos").
 * Ventaja: esquema normalizado, NOT NULL en columnas propias de subclase.
 * Inconveniente: JOINs en cada consulta polimórfica.
 */
@Entity
@Table(name = "vehiculos")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String marca;

    public Vehiculo() {}
    public Vehiculo(String marca) { this.marca = marca; }

    public Long   getId()              { return id; }
    public void   setId(Long id)       { this.id = id; }
    public String getMarca()           { return marca; }
    public void   setMarca(String m)   { this.marca = m; }
}
