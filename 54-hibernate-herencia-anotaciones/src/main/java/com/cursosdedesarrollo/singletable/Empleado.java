package com.cursosdedesarrollo.singletable;

import javax.persistence.*;

/**
 * SINGLE_TABLE — subclase "EMP".
 * Añade columna "salario" en la tabla personas (nullable para otros tipos).
 */
@Entity
@DiscriminatorValue("EMP")
public class Empleado extends Persona {

    @Column
    private double salario;

    public Empleado() {}
    public Empleado(String nombre, double salario) { super(nombre); this.salario = salario; }

    public double getSalario()          { return salario; }
    public void   setSalario(double s)  { this.salario = s; }
}
