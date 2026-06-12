package com.cursosdedesarrollo.mappedsuperclass;

import javax.persistence.*;
import java.util.Date;

/**
 * @MappedSuperclass — NO es una entidad JPA.
 *
 * Sus campos se mapean en la tabla de cada subclase concreta,
 * pero NO existe tabla "entidades_auditables" en la base de datos.
 *
 * Diferencia clave con las estrategias de herencia:
 *   - @Inheritance → jerarquía de entidades; "from EntidadAuditable" funciona en HQL.
 *   - @MappedSuperclass → solo herencia de mapeo; no se puede consultar la superclase.
 *
 * Caso de uso típico: añadir campos de auditoría (creadoEn, modificadoEn)
 * a múltiples entidades sin duplicar código.
 *
 * NOTA sobre @PrePersist / @PreUpdate: JPA lifecycle callbacks solo los
 * dispara EntityManager.persist() / .merge(). Con Session.save() de
 * Hibernate 3.x NO se invocan. Por eso inicializamos creadoEn en el
 * constructor, que es el equivalente más simple y siempre funciona.
 */
@MappedSuperclass
public abstract class EntidadAuditable {

    @Column(name = "creado_en", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creadoEn;

    @Column(name = "modificado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificadoEn;

    protected EntidadAuditable() {
        this.creadoEn = new Date();
    }

    public Date getCreadoEn()               { return creadoEn; }
    public Date getModificadoEn()           { return modificadoEn; }
    public void setModificadoEn(Date d)     { this.modificadoEn = d; }
}
