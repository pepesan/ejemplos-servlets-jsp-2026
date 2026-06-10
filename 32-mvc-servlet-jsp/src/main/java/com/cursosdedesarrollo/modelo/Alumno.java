package com.cursosdedesarrollo.modelo;

import java.util.Date;

/**
 * Modelo (M en MVC) que representa a un alumno.
 *
 * Los JSP acceden a sus propiedades mediante Expression Language (EL).
 * EL sigue la convención JavaBean: llama a getX() para cada propiedad
 * y a isX() para las booleanas. Por ejemplo:
 *
 *   ${alumno.nombre}    →  getNombre()
 *   ${alumno.nota}      →  getNota()
 *   ${alumno.activo}    →  isActivo()
 *   ${alumno.aprobado}  →  isAprobado()   ← propiedad calculada, sin campo
 *   ${alumno.fechaAlta} →  getFechaAlta()
 *
 * Para que EL funcione la clase debe cumplir la convención JavaBean:
 * constructor sin argumentos + getters/setters públicos.
 */
public class Alumno {

    private int     id;
    private String  nombre;
    private String  email;
    private double  nota;      // 0.0 – 10.0
    private boolean activo;
    private Date    fechaAlta;

    /** Constructor sin argumentos requerido por la convención JavaBean. */
    public Alumno() {
        this.fechaAlta = new Date();
    }

    public Alumno(String nombre, String email, double nota, boolean activo) {
        this();
        this.nombre = nombre;
        this.email  = email;
        this.nota   = nota;
        this.activo = activo;
    }

    /**
     * Propiedad calculada: no tiene campo propio, se deriva de nota.
     * Accesible en EL como ${alumno.aprobado}.
     */
    public boolean isAprobado() {
        return nota >= 5.0;
    }

    // ── Getters y setters ──────────────────────────────────────────────

    public int getId()                { return id; }
    public void setId(int id)         { this.id = id; }

    public String getNombre()              { return nombre; }
    public void setNombre(String nombre)   { this.nombre = nombre; }

    public String getEmail()               { return email; }
    public void setEmail(String email)     { this.email = email; }

    public double getNota()                { return nota; }
    public void setNota(double nota)       { this.nota = nota; }

    public boolean isActivo()              { return activo; }
    public void setActivo(boolean activo)  { this.activo = activo; }

    public Date getFechaAlta()             { return fechaAlta; }
    public void setFechaAlta(Date f)       { this.fechaAlta = f; }
}
