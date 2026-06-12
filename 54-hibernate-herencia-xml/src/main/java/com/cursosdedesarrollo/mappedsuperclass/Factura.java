package com.cursosdedesarrollo.mappedsuperclass;

/**
 * Entidad concreta. Los campos de auditoría (creadoEn, modificadoEn) están
 * mapeados directamente en Factura.hbm.xml porque HBM no tiene @MappedSuperclass.
 */
public class Factura extends EntidadAuditable {

    private Long id;
    private String numero;
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
