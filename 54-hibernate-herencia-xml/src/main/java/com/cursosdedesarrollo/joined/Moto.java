package com.cursosdedesarrollo.joined;

/**
 * JOINED — subclase. Tabla "motos" con FK → vehiculos.id.
 * Declarado en Vehiculo.hbm.xml como &lt;joined-subclass&gt;.
 */
public class Moto extends Vehiculo {

    private int cilindrada;

    public Moto() {}
    public Moto(String marca, int cilindrada) { super(marca); this.cilindrada = cilindrada; }

    public int  getCilindrada()       { return cilindrada; }
    public void setCilindrada(int c)  { this.cilindrada = c; }
}
