package com.cursosdedesarrollo.mappedsuperclass;

import java.util.Date;

/**
 * Clase base de auditoría — POJO puro, sin mapeo HBM propio.
 *
 * En el módulo de anotaciones esta clase lleva @MappedSuperclass, que indica
 * a Hibernate que sus campos se deben incluir en la tabla de cada subclase.
 *
 * En el mapeo XML no existe un equivalente directo a @MappedSuperclass.
 * La solución es repetir manualmente los campos creadoEn/modificadoEn en
 * el fichero hbm.xml de cada subclase concreta (Factura.hbm.xml, Pedido.hbm.xml).
 * Esta repetición es el precio a pagar por no tener anotaciones.
 *
 * Desde el punto de vista de Java, la herencia funciona igual: Factura y Pedido
 * extienden esta clase y heredan los campos y el constructor que inicializa creadoEn.
 */
public abstract class EntidadAuditable {

    private Date creadoEn;
    private Date modificadoEn;

    protected EntidadAuditable() {
        this.creadoEn = new Date();
    }

    public Date getCreadoEn()               { return creadoEn; }
    public void setCreadoEn(Date d)         { this.creadoEn = d; }
    public Date getModificadoEn()           { return modificadoEn; }
    public void setModificadoEn(Date d)     { this.modificadoEn = d; }
}
