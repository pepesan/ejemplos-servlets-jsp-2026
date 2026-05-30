package com.cursosdedesarrollo;

/**
 * POJO sin ninguna anotación. El mapeo a la tabla "empleados" vive
 * exclusivamente en Empleado.hbm.xml (enfoque XML clásico de Hibernate 3.x).
 */
public class Empleado {

    private Long   id;
    private String nombre;
    private String departamento;
    private double salario;

    public Empleado() {}

    public Empleado(String nombre, String departamento, double salario) {
        this.nombre       = nombre;
        this.departamento = departamento;
        this.salario      = salario;
    }

    public Long   getId()                       { return id; }
    public void   setId(Long id)                { this.id = id; }

    public String getNombre()                   { return nombre; }
    public void   setNombre(String nombre)      { this.nombre = nombre; }

    public String getDepartamento()                        { return departamento; }
    public void   setDepartamento(String departamento)     { this.departamento = departamento; }

    public double getSalario()                  { return salario; }
    public void   setSalario(double salario)    { this.salario = salario; }

    @Override
    public String toString() {
        return "Empleado{id=" + id + ", nombre='" + nombre + "', dpto='" + departamento + "', salario=" + salario + "}";
    }
}
