package com.cursosdedesarrollo;

/**
 * JavaBean que representa un alumno.
 * EL accede a propiedades compuestas en cadena:
 *   ${alumno.nombreCompleto}  →  alumno.getNombreCompleto()
 *   ${alumno.aprobado}        →  alumno.isAprobado()
 */
public class Alumno {

    private String nombre;
    private String apellidos;
    private double nota;
    private boolean activo;

    public Alumno() {}

    public Alumno(String nombre, String apellidos, double nota, boolean activo) {
        this.nombre    = nombre;
        this.apellidos = apellidos;
        this.nota      = nota;
        this.activo    = activo;
    }

    public String  getNombre()    { return nombre; }
    public String  getApellidos() { return apellidos; }
    public double  getNota()      { return nota; }
    public boolean isActivo()     { return activo; }

    /** Propiedad calculada — EL la invoca igual que cualquier getter. */
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    /** Propiedad calculada: aprobado si nota >= 5. */
    public boolean isAprobado() {
        return nota >= 5.0;
    }

    public void setNombre(String nombre)       { this.nombre    = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setNota(double nota)           { this.nota      = nota; }
    public void setActivo(boolean activo)      { this.activo    = activo; }

    @Override
    public String toString() {
        return "Alumno{nombre='" + getNombreCompleto() + "', nota=" + nota + "}";
    }
}
