package com.cursosdedesarrollo;

import com.cursosdedesarrollo.unoamuchos.Departamento;
import com.cursosdedesarrollo.unoamuchos.Empleado;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @ManyToOne / @OneToMany bidireccional:
 *   Departamento (1) ←→ (*) Empleado
 *
 *   - La FK departamento_id vive en la tabla empleados (lado dueño: Empleado).
 *   - Departamento.empleados tiene mappedBy="departamento" (lado inverso).
 */
public class ManyToOneTest {

    @Test
    public void empleadoPerteneceADepartamento() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Departamento it = new Departamento("IT");
        s.save(it);

        Empleado ana = new Empleado("Ana", 3000);
        ana.setDepartamento(it);
        s.save(ana);

        t.commit();
        s.close();

        // Releer desde BD
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Empleado leido = (Empleado) s2.get(Empleado.class, ana.getId());
        assertNotNull(leido.getDepartamento());
        assertEquals("IT", leido.getDepartamento().getNombre());
        s2.close();
    }

    @Test
    public void relacionBidireccional_desdeDepartamentoSeVenEmpleados() {
        // Bidireccional: navegar desde el lado Departamento (@OneToMany inverso)
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Departamento dev = new Departamento("Dev");
        s.save(dev);

        Empleado e1 = new Empleado("Sofía",  3100); e1.setDepartamento(dev); s.save(e1);
        Empleado e2 = new Empleado("Javier", 2900); e2.setDepartamento(dev); s.save(e2);

        t.commit();
        s.close();

        // Navegar desde Departamento → empleados (lado inverso mappedBy)
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Departamento leido = (Departamento) s2.get(Departamento.class, dev.getId());
        // Inicializar la colección lazy
        org.hibernate.Hibernate.initialize(leido.getEmpleados());
        assertEquals(2, leido.getEmpleados().size());
        s2.close();
    }

    @Test
    public void departamentoTieneVariosEmpleados() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Departamento rrhh = new Departamento("RRHH");
        s.save(rrhh);

        Empleado e1 = new Empleado("Luis",  2500); e1.setDepartamento(rrhh); s.save(e1);
        Empleado e2 = new Empleado("María", 2800); e2.setDepartamento(rrhh); s.save(e2);

        t.commit();
        s.close();

        // Consulta HQL para verificar la relación inversa
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Long count = (Long) s2.createQuery(
                "select count(e) from Empleado e where e.departamento.nombre = :dep")
                .setString("dep", "RRHH")
                .uniqueResult();
        assertEquals(2L, count.longValue());
        s2.close();
    }
}
