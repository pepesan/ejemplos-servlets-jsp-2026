package com.cursosdedesarrollo.unoamuchos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OneToMany BIDIRECCIONAL — lado inverso ("uno").
 *
 * mappedBy="departamento" indica que la FK está en Empleado.departamento,
 * no aquí. Departamento no genera ninguna columna extra.
 *
 * Para mantener coherencia en memoria: al añadir un Empleado a la lista
 * también hay que hacer empleado.setDepartamento(this).
 */
@Entity
@Table(name = "departamentos")
public class Departamento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombre;

    /** Lado inverso: no genera FK. La FK está en la tabla empleados. */
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empleado> empleados = new ArrayList<Empleado>();

    public Departamento() {}
    public Departamento(String nombre) { this.nombre = nombre; }

    /** Helper: asigna el departamento al empleado y lo añade a la colección. */
    public void addEmpleado(Empleado e) {
        empleados.add(e);
        e.setDepartamento(this);
    }

    public Long           getId()                          { return id; }
    public void           setId(Long id)                   { this.id = id; }
    public String         getNombre()                      { return nombre; }
    public void           setNombre(String n)              { this.nombre = n; }
    public List<Empleado> getEmpleados()                   { return empleados; }
    public void           setEmpleados(List<Empleado> e)   { this.empleados = e; }
}
