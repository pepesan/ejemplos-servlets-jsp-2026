package com.cursosdedesarrollo.mappedsuperclass;

import javax.persistence.*;

/**
 * Entidad concreta que hereda los campos de auditoría de EntidadAuditable.
 * Tabla "facturas": id, numero, cliente, creado_en, modificado_en.
 */
@Entity
@Table(name = "facturas")
public class Factura extends EntidadAuditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, length = 100)
    private String cliente;

    public Factura() {}
    public Factura(String numero, String cliente) {
        this.numero  = numero;
        this.cliente = cliente;
    }

    public Long   getId()               { return id; }
    public void   setId(Long id)        { this.id = id; }
    public String getNumero()           { return numero; }
    public void   setNumero(String n)   { this.numero = n; }
    public String getCliente()          { return cliente; }
    public void   setCliente(String c)  { this.cliente = c; }
}
