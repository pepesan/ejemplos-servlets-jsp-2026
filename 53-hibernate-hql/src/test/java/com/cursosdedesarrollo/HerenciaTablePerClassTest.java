package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * TABLE_PER_CLASS: no hay tabla padre. Cada subclase tiene su tabla completa.
 *
 * SQL generado:
 *   CREATE TABLE pagos_tarjeta  (id BIGINT PK, importe DOUBLE, numero_tarjeta VARCHAR)
 *   CREATE TABLE pagos_efectivo (id BIGINT PK, importe DOUBLE, moneda VARCHAR)
 *
 * Consulta polimórfica: "from Pago" hace UNION ALL de pagos_tarjeta + pagos_efectivo.
 * Requiere GenerationType.TABLE (no IDENTITY) porque no hay tabla padre que genere IDs.
 */
public class HerenciaTablePerClassTest {

    @Test
    public void guardarSubclasesEnTablasSeparadas() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        s.save(new PagoTarjeta(99.99,  "4111-1111-1111-1111"));
        s.save(new PagoTarjeta(149.50, "5500-0000-0000-0004"));
        s.save(new PagoEfectivo(50.00, "EUR"));

        t.commit();
        s.close();

        // Polimórfico: UNION ALL de pagos_tarjeta y pagos_efectivo
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Pago> todos = s2.createQuery("from Pago").list();
        assertEquals(3, todos.size());
        s2.close();
    }

    @Test
    public void consultarSoloUnaSubclase() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(new PagoEfectivo(20.00, "USD"));
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<PagoEfectivo> efectivos = s2.createQuery("from PagoEfectivo").list();
        assertFalse(efectivos.isEmpty());
        for (PagoEfectivo p : efectivos) {
            assertNotNull(p.getMoneda());
        }
        s2.close();
    }
}
