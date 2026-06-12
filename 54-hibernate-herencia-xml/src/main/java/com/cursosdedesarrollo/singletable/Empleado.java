package com.cursosdedesarrollo.singletable;

/**
 * SINGLE_TABLE — subclase. Discriminator value "EMP" declarado en Persona.hbm.xml.
 */
public class Empleado extends Persona {

    private double salario;

    public Empleado() {}
    public Empleado(String nombre, double salario) { super(nombre); this.salario = salario; }

    public double getSalario()          { return salario; }
    public void   setSalario(double s)  { this.salario = s; }
}
