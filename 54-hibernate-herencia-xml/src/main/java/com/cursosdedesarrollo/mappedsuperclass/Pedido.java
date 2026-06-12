package com.cursosdedesarrollo.mappedsuperclass;

/**
 * Entidad concreta. Los campos de auditoría (creadoEn, modificadoEn) están
 * mapeados directamente en Pedido.hbm.xml porque HBM no tiene @MappedSuperclass.
 */
public class Pedido extends EntidadAuditable {

    private Long id;
    private double total;

    public Pedido() {}
    public Pedido(double total) { this.total = total; }

    public Long   getId()             { return id; }
    public void   setId(Long id)      { this.id = id; }
    public double getTotal()          { return total; }
    public void   setTotal(double t)  { this.total = t; }
}
