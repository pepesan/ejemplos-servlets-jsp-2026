package com.cursosdedesarrollo.tableperclass;

/**
 * TABLE_PER_CLASS — raíz abstracta. No existe tabla "cuentas".
 * Las subclases tienen tablas propias con todos los campos repetidos.
 * La estrategia se declara en Cuenta.hbm.xml con &lt;union-subclass&gt;.
 *
 * Generator: sequence (en vez de identity) porque dos tablas independientes
 * con IDENTITY producirían IDs duplicados al hacer UNION ALL polimórfico.
 * Una secuencia compartida garantiza unicidad global entre tablas.
 */
public abstract class Cuenta {

    private Long id;
    private double saldo;

    public Cuenta() {}
    public Cuenta(double saldo) { this.saldo = saldo; }

    public Long   getId()              { return id; }
    public void   setId(Long id)       { this.id = id; }
    public double getSaldo()           { return saldo; }
    public void   setSaldo(double s)   { this.saldo = s; }
}
