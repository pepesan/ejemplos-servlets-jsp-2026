package com.cursosdedesarrollo;

import javax.persistence.*;

/** TABLE_PER_CLASS: tabla "pagos_efectivo" con importe + moneda. */
@Entity
@Table(name = "pagos_efectivo")
public class PagoEfectivo extends Pago {

    @Column(length = 10)
    private String moneda;

    public PagoEfectivo() {}
    public PagoEfectivo(double importe, String moneda) { super(importe); this.moneda = moneda; }

    public String getMoneda()            { return moneda; }
    public void   setMoneda(String m)    { this.moneda = m; }
}
