package com.cursosdedesarrollo.joined;

import javax.persistence.*;

/**
 * JOINED — subclase con tabla propia.
 * Tabla "motos": columna id (FK → vehiculos.id) + cilindrada NOT NULL.
 */
@Entity
@Table(name = "motos")
public class Moto extends Vehiculo {

    @Column(nullable = false)
    private int cilindrada;

    public Moto() {}
    public Moto(String marca, int cilindrada) { super(marca); this.cilindrada = cilindrada; }

    public int  getCilindrada()       { return cilindrada; }
    public void setCilindrada(int c)  { this.cilindrada = c; }
}
