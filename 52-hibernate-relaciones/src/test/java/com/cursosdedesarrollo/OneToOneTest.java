package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @OneToOne con cascade=ALL:
 *   Empleado (1) ←→ (1) Perfil
 *
 *   - La FK perfil_id vive en la tabla empleados (lado dueño: Empleado).
 *   - cascade=ALL: guardar el Empleado guarda el Perfil automáticamente.
 *   - orphanRemoval implícito en cascade=ALL: borrar el Empleado borra el Perfil.
 */
public class OneToOneTest {

    @Test
    public void guardarEmpleadoGuardaPerfilPorCascade() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Perfil perfil = new Perfil("Desarrolladora Java", "linkedin.com/in/ana");
        Empleado ana  = new Empleado("Ana", 3500);
        ana.setPerfil(perfil); // cascade=ALL: no hace falta s.save(perfil)

        s.save(ana);
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Empleado leido = (Empleado) s2.get(Empleado.class, ana.getId());
        assertNotNull(leido.getPerfil());
        assertEquals("Desarrolladora Java", leido.getPerfil().getBio());
        assertEquals("linkedin.com/in/ana", leido.getPerfil().getLinkedin());
        s2.close();
    }

    @Test
    public void relacionBidireccional_desdePerfil_encontrarEmpleado() {
        // Unidireccional: Empleado → Perfil (la FK está en empleados.perfil_id)
        // Para navegar al revés (Perfil → Empleado) se usa HQL con join
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Perfil p   = new Perfil("Arquitecto software", "linkedin.com/pepe");
        Empleado e = new Empleado("Pepe", 4500);
        e.setPerfil(p);
        s.save(e);
        t.commit();
        s.close();

        // Navegar desde Perfil → Empleado vía HQL (join inverso)
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Empleado encontrado = (Empleado) s2.createQuery(
                "from Empleado e where e.perfil.id = :pid")
                .setLong("pid", p.getId())
                .uniqueResult();
        assertNotNull(encontrado);
        assertEquals("Pepe", encontrado.getNombre());
        s2.close();
    }

    @Test
    public void empleadoSinPerfilEsValido() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Empleado carlos = new Empleado("Carlos", 2800);
        s.save(carlos);
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Empleado leido = (Empleado) s2.get(Empleado.class, carlos.getId());
        assertNull(leido.getPerfil());
        s2.close();
    }
}
