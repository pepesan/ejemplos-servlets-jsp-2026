package com.cursosdedesarrollo.joined;

/**
 * JOINED — raíz de la jerarquía. Sin anotaciones JPA.
 * Tabla "vehiculos". Las subclases tienen su propia tabla con FK al id de esta.
 * La estrategia se declara en Vehiculo.hbm.xml con &lt;joined-subclass&gt;.
 */
public abstract class Vehiculo {

    private Long id;
    private String marca;

    public Vehiculo() {}
    public Vehiculo(String marca) { this.marca = marca; }

    public Long   getId()             { return id; }
    public void   setId(Long id)      { this.id = id; }
    public String getMarca()          { return marca; }
    public void   setMarca(String m)  { this.marca = m; }
}
