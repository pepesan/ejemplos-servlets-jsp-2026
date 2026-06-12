package com.cursosdedesarrollo.unoauno;

/**
 * OneToOne UNIDIRECCIONAL — lado pasivo.
 * Esta clase no sabe nada de Persona. La relación solo existe en Persona.hbm.xml.
 */
public class Pasaporte {

    private Long id;
    private String numero;

    public Pasaporte() {}
    public Pasaporte(String numero) { this.numero = numero; }

    public Long   getId()                 { return id; }
    public void   setId(Long id)          { this.id = id; }
    public String getNumero()             { return numero; }
    public void   setNumero(String n)     { this.numero = n; }
}
