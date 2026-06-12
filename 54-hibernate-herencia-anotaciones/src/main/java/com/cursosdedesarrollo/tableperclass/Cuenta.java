package com.cursosdedesarrollo.tableperclass;

import javax.persistence.*;

/**
 * TABLE_PER_CLASS — raíz abstracta. No existe tabla "cuentas".
 *
 * Cada subclase tiene su tabla propia con TODAS las columnas:
 *   cuentas_corriente (id, saldo, descubierto_max)
 *   cuentas_ahorro    (id, saldo, tasa_interes)
 *
 * Ventaja:  sin JOINs; columnas de subclase pueden ser NOT NULL.
 * Desventaja: columnas de la superclase se repiten en cada tabla;
 *             consultas polimórficas usan UNION ALL.
 *
 * IDENTITY no funciona con TABLE_PER_CLASS porque un UNION ALL
 * sobre varias tablas con secuencias separadas produciría IDs duplicados.
 * Se usa GenerationType.TABLE (tabla auxiliar de secuencias en BD).
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false)
    private double saldo;

    public Cuenta() {}
    public Cuenta(double saldo) { this.saldo = saldo; }

    public Long   getId()              { return id; }
    public void   setId(Long id)       { this.id = id; }
    public double getSaldo()           { return saldo; }
    public void   setSaldo(double s)   { this.saldo = s; }
}
