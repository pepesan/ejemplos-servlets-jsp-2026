package com.cursosdedesarrollo;

import javax.persistence.*;

/**
 * TABLE_PER_CLASS: cada subclase tiene su propia tabla completa.
 * No hay tabla "pagos". Las tablas "pagos_tarjeta" y "pagos_efectivo"
 * repiten las columnas de la superclase.
 * Ventaja: sin JOINs, columnas propias NOT NULL.
 * Inconveniente: consultas polimórficas usan UNION ALL.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false)
    private double importe;

    public Pago() {}
    public Pago(double importe) { this.importe = importe; }

    public Long   getId()               { return id; }
    public void   setId(Long id)        { this.id = id; }
    public double getImporte()          { return importe; }
    public void   setImporte(double i)  { this.importe = i; }
}
