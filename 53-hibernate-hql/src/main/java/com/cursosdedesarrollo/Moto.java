package com.cursosdedesarrollo;

import javax.persistence.*;

/** JOINED: tabla propia "motos" con FK id → vehiculos.id y columna "cilindrada". */
@Entity
@Table(name = "motos")
public class Moto extends Vehiculo {

    @Column
    private int cilindrada;

    public Moto() {}
    public Moto(String marca, int cilindrada) { super(marca); this.cilindrada = cilindrada; }

    public int  getCilindrada()        { return cilindrada; }
    public void setCilindrada(int c)   { this.cilindrada = c; }
}
