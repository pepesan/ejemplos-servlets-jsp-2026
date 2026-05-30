package com.cursosdedesarrollo;

import javax.persistence.*;

/** TABLE_PER_CLASS: tabla "pagos_tarjeta" con importe + numero_tarjeta. */
@Entity
@Table(name = "pagos_tarjeta")
public class PagoTarjeta extends Pago {

    @Column(name = "numero_tarjeta", length = 20)
    private String numeroTarjeta;

    public PagoTarjeta() {}
    public PagoTarjeta(double importe, String numeroTarjeta) {
        super(importe); this.numeroTarjeta = numeroTarjeta;
    }

    public String getNumeroTarjeta()              { return numeroTarjeta; }
    public void   setNumeroTarjeta(String n)      { this.numeroTarjeta = n; }
}
