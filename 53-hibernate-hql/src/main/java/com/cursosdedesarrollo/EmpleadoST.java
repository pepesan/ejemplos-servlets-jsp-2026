package com.cursosdedesarrollo;

import javax.persistence.*;

/** Subclase con discriminador "EMP". Columna "salario" en la tabla "personas". */
@Entity
@DiscriminatorValue("EMP")
public class EmpleadoST extends Persona {

    @Column
    private double salario;

    public EmpleadoST() {}
    public EmpleadoST(String nombre, double salario) { super(nombre); this.salario = salario; }

    public double getSalario()             { return salario; }
    public void   setSalario(double s)     { this.salario = s; }
}
