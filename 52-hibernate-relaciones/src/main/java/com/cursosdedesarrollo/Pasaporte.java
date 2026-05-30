package com.cursosdedesarrollo;

import javax.persistence.*;

/**
 * OneToOne UNIDIRECCIONAL — lado pasivo.
 * Esta clase no sabe nada de Persona. La relación solo existe en Persona.
 */
@Entity
@Table(name = "pasaportes")
public class Pasaporte {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String numero;

    public Pasaporte() {}
    public Pasaporte(String numero) { this.numero = numero; }

    public Long   getId()                 { return id; }
    public void   setId(Long id)          { this.id = id; }
    public String getNumero()             { return numero; }
    public void   setNumero(String n)     { this.numero = n; }
}
