package com.cursosdedesarrollo.joined;

/**
 * JOINED — subclase. Tabla "coches" con FK → vehiculos.id.
 * Declarado en Vehiculo.hbm.xml como &lt;joined-subclass&gt;.
 */
public class Coche extends Vehiculo {

    private int puertas;

    public Coche() {}
    public Coche(String marca, int puertas) { super(marca); this.puertas = puertas; }

    public int  getPuertas()        { return puertas; }
    public void setPuertas(int p)   { this.puertas = p; }
}
