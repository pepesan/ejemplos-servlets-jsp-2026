package com.cursosdedesarrollo;

import com.cursosdedesarrollo.mappedsuperclass.Factura;
import com.cursosdedesarrollo.mappedsuperclass.Pedido;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @MappedSuperclass: herencia de MAPEO, no de entidad.
 *
 * EntidadAuditable NO es una entidad JPA:
 *   - No existe tabla "entidades_auditables".
 *   - No se puede hacer "from EntidadAuditable" en HQL.
 *   - Sus campos (creado_en, modificado_en) se copian a cada tabla subclase.
 *
 * DDL resultante:
 *   CREATE TABLE pedidos  (id BIGINT PK, total DOUBLE, creado_en TIMESTAMP, modificado_en TIMESTAMP)
 *   CREATE TABLE facturas (id BIGINT PK, numero VARCHAR, cliente VARCHAR, creado_en TIMESTAMP, modificado_en TIMESTAMP)
 *
 * Uso típico: campos de auditoría, soft-delete, versionado.
 */
public class HerenciaMappedSuperclassTest {

    @Test
    public void camposDeAuditoriaSeRellenanAlCrear() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Pedido p = new Pedido(149.99);
        s.save(p);
        t.commit();
        Long id = p.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Pedido leido = (Pedido) s2.get(Pedido.class, id);
        assertNotNull("El constructor de EntidadAuditable inicializa creadoEn", leido.getCreadoEn());
        assertNull("modificadoEn es null hasta el primer update",              leido.getModificadoEn());
        s2.close();
    }

    @Test
    public void facturaHeredaCamposDeAuditoria() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Factura f = new Factura("FAC-2024-001", "Acme Corp");
        s.save(f);
        t.commit();
        Long id = f.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Factura leida = (Factura) s2.get(Factura.class, id);
        assertNotNull(leida.getCreadoEn());
        assertEquals("FAC-2024-001", leida.getNumero());
        assertEquals("Acme Corp",    leida.getCliente());
        s2.close();
    }

    @Test
    public void noSePuedeConsultarLaSuperclase() {
        // "from EntidadAuditable" lanzaría HibernateException porque no es @Entity.
        // Este test verifica que Pedido y Factura se consultan INDEPENDIENTEMENTE.
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(new Pedido(50.0));
        s.save(new Factura("FAC-001", "Beta SL"));
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        long pedidos  = (Long) s2.createQuery("select count(p) from Pedido p").uniqueResult();
        long facturas = (Long) s2.createQuery("select count(f) from Factura f").uniqueResult();
        assertTrue(pedidos  >= 1);
        assertTrue(facturas >= 1);
        s2.close();
    }
}
