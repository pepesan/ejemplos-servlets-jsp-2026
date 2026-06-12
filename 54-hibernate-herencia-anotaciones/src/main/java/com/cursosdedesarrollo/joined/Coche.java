package com.cursosdedesarrollo.joined;

import javax.persistence.*;

/**
 * JOINED — subclase con tabla propia.
 * Tabla "coches": columna id (FK → vehiculos.id) + puertas NOT NULL.
 */
@Entity
@Table(name = "coches")
public class Coche extends Vehiculo {

    @Column(nullable = false)
    private int puertas;

    public Coche() {}
    public Coche(String marca, int puertas) { super(marca); this.puertas = puertas; }

    public int  getPuertas()        { return puertas; }
    public void setPuertas(int p)   { this.puertas = p; }
}
