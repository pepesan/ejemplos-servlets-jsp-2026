package com.cursosdedesarrollo.unoauno;

import javax.persistence.*;

/**
 * OneToOne UNIDIRECCIONAL — lado dueño.
 *
 * Solo Persona conoce a Pasaporte. Pasaporte no tiene ninguna referencia a Persona.
 * La FK pasaporte_id vive en la tabla personas.
 *
 *   personas.pasaporte_id → pasaportes.id
 *
 * No hay forma de navegar Pasaporte → Persona sin una consulta HQL explícita.
 */
@Entity
@Table(name = "personas")
public class Persona {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pasaporte_id")
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
