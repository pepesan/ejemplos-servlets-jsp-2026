package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * SINGLE_TABLE: una sola tabla "personas" con columna discriminadora "tipo".
 *
 * SQL generado:
 *   CREATE TABLE personas (id BIGINT, tipo VARCHAR, nombre VARCHAR, salario DOUBLE, empresa VARCHAR)
 *
 * Consulta polimórfica: "from Persona" devuelve EmpleadoST y Cliente en la misma lista.
 */
public class HerenciaSingleTableTest {

    @Test
    public void guardarSubclasesEnMismaTabla() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        EmpleadoST e1 = new EmpleadoST("Ana",    3000);
        EmpleadoST e2 = new EmpleadoST("Carlos", 2500);
        Cliente    c1 = new Cliente("Empresa A", "Acme Corp");
        s.save(e1); s.save(e2); s.save(c1);

        t.commit();
        s.close();

        // Consulta polimórfica: recuperar por ID verifica que ambas subclases
        // están en la misma tabla "personas"
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        assertNotNull(s2.get(EmpleadoST.class, e1.getId()));
        assertNotNull(s2.get(EmpleadoST.class, e2.getId()));
        assertNotNull(s2.get(Cliente.class,    c1.getId()));
        // Los tres tienen el mismo tipo base Persona
        assertTrue(s2.get(Persona.class, e1.getId()) instanceof EmpleadoST);
        assertTrue(s2.get(Persona.class, c1.getId()) instanceof Cliente);
        s2.close();
    }

    @Test
    public void consultarSoloSubclaseEmpleado() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(new EmpleadoST("María",  3500));
        s.save(new Cliente("Empresa B", "Beta SL"));
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<EmpleadoST> empleados = s2.createQuery("from EmpleadoST").list();
        // Solo los EmpleadoST, no los clientes
        for (EmpleadoST e : empleados) {
            assertTrue(e.getSalario() > 0);
        }
        s2.close();
    }
}
