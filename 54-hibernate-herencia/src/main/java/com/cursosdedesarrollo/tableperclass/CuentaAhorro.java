package com.cursosdedesarrollo.tableperclass;

import javax.persistence.*;

/**
 * TABLE_PER_CLASS — tabla "cuentas_ahorro".
 * Contiene: id, saldo (de Cuenta) + tasa_interes (propio).
 */
@Entity
@Table(name = "cuentas_ahorro")
public class CuentaAhorro extends Cuenta {

    @Column(name = "tasa_interes", nullable = false)
    private double tasaInteres;

    public CuentaAhorro() {}
    public CuentaAhorro(double saldo, double tasaInteres) {
        super(saldo);
        this.tasaInteres = tasaInteres;
    }

    public double getTasaInteres()          { return tasaInteres; }
    public void   setTasaInteres(double t)  { this.tasaInteres = t; }
}
