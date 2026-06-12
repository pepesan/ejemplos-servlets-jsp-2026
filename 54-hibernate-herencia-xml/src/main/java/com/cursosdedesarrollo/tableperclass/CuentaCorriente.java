package com.cursosdedesarrollo.tableperclass;

/**
 * TABLE_PER_CLASS — tabla "cuentas_corriente".
 * Contiene: id, saldo (heredados de Cuenta) + descubierto_max (propio).
 */
public class CuentaCorriente extends Cuenta {

    private double descubiertoMax;

    public CuentaCorriente() {}
    public CuentaCorriente(double saldo, double descubiertoMax) {
        super(saldo);
        this.descubiertoMax = descubiertoMax;
    }

    public double getDescubiertoMax()          { return descubiertoMax; }
    public void   setDescubiertoMax(double d)  { this.descubiertoMax = d; }
}
