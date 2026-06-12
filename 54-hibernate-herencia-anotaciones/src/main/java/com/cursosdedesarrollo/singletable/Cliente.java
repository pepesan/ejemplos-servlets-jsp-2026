package com.cursosdedesarrollo.singletable;

import javax.persistence.*;

/**
 * SINGLE_TABLE — subclase "CLI".
 * Añade columna "empresa" en la tabla personas (nullable para otros tipos).
 */
@Entity
@DiscriminatorValue("CLI")
public class Cliente extends Persona {

    @Column(length = 120)
    private String empresa;

    public Cliente() {}
    public Cliente(String nombre, String empresa) { super(nombre); this.empresa = empresa; }

    public String getEmpresa()          { return empresa; }
    public void   setEmpresa(String e)  { this.empresa = e; }
}
