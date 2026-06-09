package com.cursosdedesarrollo.modelo;

import java.util.Date;

public class Alumno {

    private int    id;
    private String nombre;
    private String email;
    private double nota;      // 0.0 – 10.0
    private boolean activo;
    private Date   fechaAlta;

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
