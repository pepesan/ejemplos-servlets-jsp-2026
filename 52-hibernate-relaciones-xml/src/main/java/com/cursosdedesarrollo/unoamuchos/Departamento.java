package com.cursosdedesarrollo.unoamuchos;

import java.util.ArrayList;
import java.util.List;

/**
 * OneToMany BIDIRECCIONAL — lado inverso ("uno").
 *
 * inverse="true" en Departamento.hbm.xml: la FK la gestiona Empleado.
 * Departamento no genera ninguna columna extra.
 *
 * Para mantener coherencia en memoria: al añadir un Empleado a la lista
 * también hay que hacer empleado.setDepartamento(this).
 */
public class Departamento {

    private Long id;
    private String nombre;
    private List<Empleado> empleados = new ArrayList<Empleado>();

    public Departamento() {}
    public Departamento(String nombre) { this.nombre = nombre; }

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
