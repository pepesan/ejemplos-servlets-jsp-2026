package com.cursosdedesarrollo.unoamuchos;

/**
 * OneToMany BIDIRECCIONAL — lado "muchos" (dueño de la FK).
 *
 * Tiene el campo departamento. La FK departamento_id vive en la tabla empleados.
 * Departamento tiene la colección con inverse="true" como lado inverso.
 */
public class Empleado {

    private Long id;
    private String nombre;
    private double salario;
    private Departamento departamento;

    public Empleado() {}
    public Empleado(String nombre, double salario) { this.nombre = nombre; this.salario = salario; }

    public Long         getId()                         { return id; }
    public void         setId(Long id)                  { this.id = id; }
    public String       getNombre()                     { return nombre; }
    public void         setNombre(String n)             { this.nombre = n; }
    public double       getSalario()                    { return salario; }
    public void         setSalario(double s)            { this.salario = s; }
    public Departamento getDepartamento()               { return departamento; }
    public void         setDepartamento(Departamento d) { this.departamento = d; }
}
