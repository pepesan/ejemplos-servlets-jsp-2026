package com.cursosdedesarrollo.unoauno;

/**
 * OneToOne UNIDIRECCIONAL — lado dueño.
 *
 * Solo Persona conoce a Pasaporte. Pasaporte no tiene ninguna referencia a Persona.
 * La FK pasaporte_id vive en la tabla personas.
 *
 * El mapeo está en Persona.hbm.xml:
 *   many-to-one name="pasaporte" column="pasaporte_id" unique="true" cascade="all"
 */
public class Persona {

    private Long id;
    private String nombre;
    private Pasaporte pasaporte;

    public Persona() {}
    public Persona(String nombre) { this.nombre = nombre; }

    public Long       getId()                       { return id; }
    public void       setId(Long id)                { this.id = id; }
    public String     getNombre()                   { return nombre; }
    public void       setNombre(String n)           { this.nombre = n; }
    public Pasaporte  getPasaporte()                { return pasaporte; }
    public void       setPasaporte(Pasaporte p)     { this.pasaporte = p; }
}
