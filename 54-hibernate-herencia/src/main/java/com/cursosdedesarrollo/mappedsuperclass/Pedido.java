package com.cursosdedesarrollo.mappedsuperclass;

import javax.persistence.*;

/**
 * Entidad concreta que hereda los campos de auditoría de EntidadAuditable.
 * Tabla "pedidos": id, total, creado_en, modificado_en.
 */
@Entity
@Table(name = "pedidos")
public class Pedido extends EntidadAuditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double total;

    public Pedido() {}
    public Pedido(double total) { this.total = total; }

    public Long   getId()             { return id; }
    public void   setId(Long id)      { this.id = id; }
    public double getTotal()          { return total; }
    public void   setTotal(double t)  { this.total = t; }
}
