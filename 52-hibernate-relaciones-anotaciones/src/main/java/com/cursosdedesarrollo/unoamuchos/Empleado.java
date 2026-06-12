package com.cursosdedesarrollo.unoamuchos;

import javax.persistence.*;

/**
 * OneToMany BIDIRECCIONAL — lado "muchos" (dueño de la FK).
 *
 * Tiene @ManyToOne → Departamento. La FK departamento_id vive aquí.
 * Departamento tiene @OneToMany(mappedBy="departamento") como lado inverso.
 *
 * Comparación con unidireccional (Articulo):
 *   Articulo no sabe a qué Categoria pertenece.
 *   Empleado SÍ sabe a qué Departamento pertenece (campo departamento).
 */
@Entity
@Table(name = "empleados")
public class Empleado {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column
    private double salario;

    /** Lado dueño: la FK departamento_id vive en la tabla empleados. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
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
