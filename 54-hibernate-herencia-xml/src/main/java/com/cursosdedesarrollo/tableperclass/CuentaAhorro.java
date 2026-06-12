package com.cursosdedesarrollo.tableperclass;

/**
 * TABLE_PER_CLASS — tabla "cuentas_ahorro".
 * Contiene: id, saldo (heredados de Cuenta) + tasa_interes (propio).
 */
public class CuentaAhorro extends Cuenta {

    private double tasaInteres;

    public CuentaAhorro() {}
    public CuentaAhorro(double saldo, double tasaInteres) {
        super(saldo);
        this.tasaInteres = tasaInteres;
    }

    public double getTasaInteres()          { return tasaInteres; }
    public void   setTasaInteres(double t)  { this.tasaInteres = t; }
}
