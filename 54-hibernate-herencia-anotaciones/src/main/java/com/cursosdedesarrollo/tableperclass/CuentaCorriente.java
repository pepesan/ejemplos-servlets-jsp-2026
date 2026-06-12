package com.cursosdedesarrollo.tableperclass;

import javax.persistence.*;

/**
 * TABLE_PER_CLASS — tabla "cuentas_corriente".
 * Contiene: id, saldo (de Cuenta) + descubierto_max (propio).
 */
@Entity
@Table(name = "cuentas_corriente")
public class CuentaCorriente extends Cuenta {

    @Column(name = "descubierto_max", nullable = false)
    private double descubiertoMax;

    public CuentaCorriente() {}
    public CuentaCorriente(double saldo, double descubiertoMax) {
        super(saldo);
        this.descubiertoMax = descubiertoMax;
    }

    public double getDescubiertoMax()          { return descubiertoMax; }
    public void   setDescubiertoMax(double d)  { this.descubiertoMax = d; }
}
