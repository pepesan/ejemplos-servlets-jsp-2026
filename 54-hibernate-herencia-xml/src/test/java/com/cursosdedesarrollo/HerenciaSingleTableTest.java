package com.cursosdedesarrollo;

import com.cursosdedesarrollo.singletable.Cliente;
import com.cursosdedesarrollo.singletable.Empleado;
import com.cursosdedesarrollo.singletable.Persona;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * SINGLE_TABLE: una sola tabla "personas" con columna discriminadora "tipo".
 *
 * DDL resultante:
 *   CREATE TABLE personas (
 *     id      BIGINT PRIMARY KEY,
 *     tipo    VARCHAR(3) NOT NULL,
 *     nombre  VARCHAR NOT NULL,
 *     empresa VARCHAR,
 *     salario DOUBLE
 *   )
 */
public class HerenciaSingleTableTest {

    @Test
    public void subclasesSeGuardanEnMismaTabla() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Empleado e1 = new Empleado("Ana",      3000);
        Empleado e2 = new Empleado("Carlos",   2500);
        Cliente  c1 = new Cliente("Empresa A", "Acme Corp");
        s.save(e1); s.save(e2); s.save(c1);

        t.commit();
        Long e1Id = e1.getId(), e2Id = e2.getId(), c1Id = c1.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        assertTrue(s2.get(Persona.class, e1Id) instanceof Empleado);
        assertTrue(s2.get(Persona.class, e2Id) instanceof Empleado);
        assertTrue(s2.get(Persona.class, c1Id) instanceof Cliente);
        s2.close();
    }

    @Test
    public void consultarSoloUnaSubclase() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(new Empleado("María", 3500));
        s.save(new Cliente("Empresa B", "Beta SL"));
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Empleado> empleados = s2.createQuery("from Empleado").list();
        assertTrue(empleados.stream().allMatch(e -> e.getSalario() > 0));
        s2.close();
    }

    @Test
    public void instanceofTrabajaConPolimorfismo() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Empleado emp = new Empleado("Pedro", 2800);
        Cliente  cli = new Cliente("Empresa C", "Gamma SA");
        s.save(emp);
        s.save(cli);
        t.commit();
        Long empId = emp.getId();
        Long cliId = cli.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        assertTrue(s2.get(Persona.class, empId) instanceof Empleado);
        assertTrue(s2.get(Persona.class, cliId) instanceof Cliente);
        s2.close();
    }

    @Test
    public void columnaDiscriminadoraEsTransparente() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Empleado e = new Empleado("Laura", 3200);
        s.save(e);
        t.commit();
        Long id = e.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Empleado leido = (Empleado) s2.get(Empleado.class, id);
        assertNotNull(leido);
        assertEquals("Laura", leido.getNombre());
        assertEquals(3200.0, leido.getSalario(), 0.01);
        s2.close();
    }
}
