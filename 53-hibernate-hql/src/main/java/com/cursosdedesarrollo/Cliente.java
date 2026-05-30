package com.cursosdedesarrollo;

import javax.persistence.*;

/** Subclase con discriminador "CLI". Columna "empresa" en la tabla "personas". */
@Entity
@DiscriminatorValue("CLI")
public class Cliente extends Persona {

    @Column(length = 120)
    private String empresa;

    public Cliente() {}
    public Cliente(String nombre, String empresa) { super(nombre); this.empresa = empresa; }

    public String getEmpresa()             { return empresa; }
    public void   setEmpresa(String e)     { this.empresa = e; }
}
