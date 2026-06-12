package com.cursosdedesarrollo.singletable;

/**
 * SINGLE_TABLE — subclase. Discriminator value "CLI" declarado en Persona.hbm.xml.
 */
public class Cliente extends Persona {

    private String empresa;

    public Cliente() {}
    public Cliente(String nombre, String empresa) { super(nombre); this.empresa = empresa; }

    public String getEmpresa()          { return empresa; }
    public void   setEmpresa(String e)  { this.empresa = e; }
}
