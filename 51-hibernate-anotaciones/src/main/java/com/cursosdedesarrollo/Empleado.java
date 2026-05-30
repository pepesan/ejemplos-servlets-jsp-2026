package com.cursosdedesarrollo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO mapeado con anotaciones JPA.
 * No hay ningún fichero hbm.xml: toda la configuración está aquí.
 *
 * Comparación con 50-hibernate (XML puro):
 *   XML:         <class name="...Empleado" table="empleados">
 *   Anotaciones: @Entity + @Table(name="empleados")
 *
 *   XML:         <id name="id"><generator class="native"/></id>
 *   Anotaciones: @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 *
 *   XML:         <property name="nombre" not-null="true" length="100"/>
 *   Anotaciones: @Column(nullable = false, length = 100)
 */
@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 80)
    private String departamento;

    @Column
    private double salario;

    /** Campo no persistido: solo existe en memoria. */
    @Transient
    private String etiqueta;

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

    public String getDepartamento()                       { return departamento; }
    public void   setDepartamento(String departamento)    { this.departamento = departamento; }

    public double getSalario()                  { return salario; }
    public void   setSalario(double salario)    { this.salario = salario; }

    public String getEtiqueta()                 { return etiqueta; }
    public void   setEtiqueta(String etiqueta)  { this.etiqueta = etiqueta; }

    @Override
    public String toString() {
        return "Empleado{id=" + id + ", nombre='" + nombre + "', dpto='" + departamento + "', salario=" + salario + "}";
    }
}
