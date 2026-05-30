package com.cursosdedesarrollo;

import javax.persistence.*;

/** JOINED: tabla propia "coches" con FK id → vehiculos.id y columna "puertas". */
@Entity
@Table(name = "coches")
public class Coche extends Vehiculo {

    @Column
    private int puertas;

    public Coche() {}
    public Coche(String marca, int puertas) { super(marca); this.puertas = puertas; }

    public int  getPuertas()           { return puertas; }
    public void setPuertas(int p)      { this.puertas = p; }
}
