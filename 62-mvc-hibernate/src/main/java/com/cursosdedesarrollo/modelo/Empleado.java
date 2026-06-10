package com.cursosdedesarrollo.modelo;

/**
 * Entidad (M en MVC) mapeada a la tabla {@code empleados} por Hibernate.
 *
 * Debe cumplir la convención JavaBean para que tanto Hibernate
 * como EL puedan acceder a sus propiedades:
 *   - constructor sin argumentos
 *   - getters/setters públicos
 *
 * Acceso desde JSP con Expression Language:
 *   ${e.nombre}       →  getNombre()
 *   ${e.salario}      →  getSalario()      en &lt;fmt:formatNumber&gt;
 *   ${e.activo}       →  isActivo()        en &lt;c:if test="${e.activo}"&gt;
 *
 * El mapeo Java ↔ tabla está en Empleado.hbm.xml.
 */
public class Empleado {

    private Long    id;
    private String  nombre;
    private String  departamento;
    private double  salario;
    private boolean activo;

    /** Constructor sin argumentos requerido por Hibernate y JavaBean. */
    public Empleado() {}

    public Empleado(String nombre, String departamento, double salario, boolean activo) {
        this.nombre       = nombre;
        this.departamento = departamento;
        this.salario      = salario;
        this.activo       = activo;
    }

    // ── Getters y setters ──────────────────────────────────────────────────

    public Long   getId()                              { return id; }
    public void   setId(Long id)                       { this.id = id; }

    public String getNombre()                          { return nombre; }
    public void   setNombre(String nombre)             { this.nombre = nombre; }

    public String getDepartamento()                    { return departamento; }
    public void   setDepartamento(String d)            { this.departamento = d; }

    public double getSalario()                         { return salario; }
    public void   setSalario(double salario)           { this.salario = salario; }

    public boolean isActivo()                          { return activo; }
    public void    setActivo(boolean activo)           { this.activo = activo; }
}
