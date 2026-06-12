package com.cursosdedesarrollo;

import com.cursosdedesarrollo.tableperclass.Cuenta;
import com.cursosdedesarrollo.tableperclass.CuentaAhorro;
import com.cursosdedesarrollo.tableperclass.CuentaCorriente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * TABLE_PER_CLASS: sin tabla base; cada subclase tiene su tabla completa.
 *
 * DDL resultante:
 *   CREATE TABLE cuentas_corriente (id BIGINT PK, saldo DOUBLE, descubierto_max DOUBLE NOT NULL)
 *   CREATE TABLE cuentas_ahorro    (id BIGINT PK, saldo DOUBLE, tasa_interes DOUBLE NOT NULL)
 *
 * No existe tabla "cuentas". Los campos de Cuenta (id, saldo) se repiten en ambas tablas.
 * Consulta polimórfica: SELECT ... FROM cuentas_corriente UNION ALL SELECT ... FROM cuentas_ahorro.
 *
 * IMPORTANTE: se usa GenerationType.TABLE para los IDs (no IDENTITY) porque
 * dos secuencias IDENTITY independientes podrían generar el mismo valor,
 * rompiendo la unicidad cuando se hace UNION ALL.
 */
public class HerenciaTablePerClassTest {

    @Test
    public void cadaSubclaseTieneSuPropiaTabla() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        CuentaCorriente cc = new CuentaCorriente(1000.0, 500.0);
        CuentaAhorro    ca = new CuentaAhorro(2000.0, 1.5);
        s.save(cc);
        s.save(ca);

        t.commit();
        Long ccId = cc.getId();
        Long caId = ca.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        CuentaCorriente ccLeida = (CuentaCorriente) s2.get(CuentaCorriente.class, ccId);
        CuentaAhorro    caLeida = (CuentaAhorro)    s2.get(CuentaAhorro.class,    caId);

        assertEquals(1000.0, ccLeida.getSaldo(), 0.01);
        assertEquals(500.0,  ccLeida.getDescubiertoMax(), 0.01);
        assertEquals(2000.0, caLeida.getSaldo(), 0.01);
        assertEquals(1.5,    caLeida.getTasaInteres(), 0.001);
        s2.close();
    }

    @Test
    public void consultaPolimorficaUsaUnionAll() {
        // "from Cuenta" Hibernate traduce a:
        //   SELECT ... FROM cuentas_corriente UNION ALL SELECT ... FROM cuentas_ahorro
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        CuentaCorriente cc = new CuentaCorriente(500.0,  200.0);
        CuentaAhorro    ca = new CuentaAhorro   (1500.0, 2.0);
        s.save(cc); s.save(ca);
        t.commit();
        Long ccId = cc.getId(), caId = ca.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        // Recuperar por la superclase devuelve la subclase correcta (via UNION ALL)
        assertTrue(s2.get(Cuenta.class, ccId) instanceof CuentaCorriente);
        assertTrue(s2.get(Cuenta.class, caId) instanceof CuentaAhorro);
        s2.close();
    }

    @Test
    public void instanceofTrabajaConPolimorfismo() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        CuentaCorriente cc = new CuentaCorriente(100.0, 50.0);
        CuentaAhorro    ca = new CuentaAhorro(200.0, 1.0);
        s.save(cc); s.save(ca);
        t.commit();
        Long ccId = cc.getId(), caId = ca.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        assertTrue(s2.get(Cuenta.class, ccId) instanceof CuentaCorriente);
        assertTrue(s2.get(Cuenta.class, caId) instanceof CuentaAhorro);
        s2.close();
    }
}
