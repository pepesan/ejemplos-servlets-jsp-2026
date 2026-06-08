package com.cursosdedesarrollo;

import com.cursosdedesarrollo.unoauno.Pasaporte;
import com.cursosdedesarrollo.unoauno.Perfil;
import com.cursosdedesarrollo.unoauno.Persona;
import com.cursosdedesarrollo.unoauno.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Demuestra @OneToOne en sus dos variantes:
 *
 *  UNIDIRECCIONAL: Persona → Pasaporte
 *    - Solo Persona conoce al Pasaporte (FK pasaporte_id en tabla personas).
 *    - Pasaporte no tiene ninguna referencia a Persona.
 *    - cascade=ALL: guardar Persona guarda Pasaporte automáticamente.
 *
 *  BIDIRECCIONAL: Usuario ↔ Perfil
 *    - Usuario es el lado dueño (FK perfil_id en tabla usuarios).
 *    - Perfil tiene @OneToOne(mappedBy="perfil") como lado inverso.
 *    - Se puede navegar en ambas direcciones.
 */
public class OneToOneTest {

    // ── UNIDIRECCIONAL: Persona → Pasaporte ───────────────────────────────

    @Test
    public void uni_guardarPersonaGuardaPasaportePorCascade() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Persona ana = new Persona("Ana García");
        ana.setPasaporte(new Pasaporte("ESP-12345"));  // cascade=ALL: no hace falta save(pasaporte)

        s.save(ana);
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Persona leida = (Persona) s2.get(Persona.class, ana.getId());
        assertNotNull(leida.getPasaporte());
        assertEquals("ESP-12345", leida.getPasaporte().getNumero());
        s2.close();
    }

    @Test
    public void uni_pasaporteNoConoceAPersona() {
        // Pasaporte no tiene campo Persona — la relación es solo desde Persona
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Persona pepe = new Persona("Pepe");
        pepe.setPasaporte(new Pasaporte("ESP-99999"));
        s.save(pepe);
        Long pasId = pepe.getPasaporte().getId();
        t.commit();
        s.close();

        // Pasaporte existe pero para saber su dueño hay que usar HQL
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Pasaporte pas = (Pasaporte) s2.get(Pasaporte.class, pasId);
        assertNotNull(pas);
        // pas.getPersona() no existe — Pasaporte es puramente pasivo
        Persona dueño = (Persona) s2.createQuery(
                "from Persona p where p.pasaporte.id = :pid")
                .setLong("pid", pasId)
                .uniqueResult();
        assertNotNull(dueño);
        assertEquals("Pepe", dueño.getNombre());
        s2.close();
    }

    @Test
    public void uni_personaSinPasaporteEsValida() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Persona carlos = new Persona("Carlos");  // sin pasaporte
        s.save(carlos);
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Persona leido = (Persona) s2.get(Persona.class, carlos.getId());
        assertNull(leido.getPasaporte());
        s2.close();
    }

    // ── BIDIRECCIONAL: Usuario ↔ Perfil ───────────────────────────────────

    @Test
    public void bi_guardarUsuarioGuardaPerfilPorCascade() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        // setPerfil() mantiene la coherencia bidireccional internamente
        Usuario usuario = new Usuario("maria");
        usuario.setPerfil(new Perfil("Desarrolladora Java", "linkedin.com/in/maria"));

        s.save(usuario);  // cascade=ALL: guarda Perfil automáticamente
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Usuario leido = (Usuario) s2.get(Usuario.class, usuario.getId());
        assertNotNull(leido.getPerfil());
        assertEquals("Desarrolladora Java", leido.getPerfil().getBio());
        s2.close();
    }

    @Test
    public void bi_perfilConoceASuUsuario() {
        // mappedBy: desde Perfil se puede navegar a Usuario sin HQL
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Usuario u = new Usuario("pepe");
        u.setPerfil(new Perfil("Arquitecto software", "linkedin.com/pepe"));
        s.save(u);
        Long perfilId = u.getPerfil().getId();
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Perfil leido = (Perfil) s2.get(Perfil.class, perfilId);
        org.hibernate.Hibernate.initialize(leido.getUsuario());
        assertNotNull(leido.getUsuario());
        assertEquals("pepe", leido.getUsuario().getLogin());
        s2.close();
    }

    @Test
    public void bi_navegarEnAmbasDirecciones() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Usuario u = new Usuario("lucia");
        Perfil p  = new Perfil("Scrum Master", "linkedin.com/lucia");
        u.setPerfil(p);
        s.save(u);
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();

        // Usuario → Perfil (lado dueño)
        Usuario uLeido = (Usuario) s2.get(Usuario.class, u.getId());
        assertNotNull(uLeido.getPerfil());
        assertEquals("Scrum Master", uLeido.getPerfil().getBio());

        // Perfil → Usuario (lado inverso mappedBy)
        Perfil pLeido = (Perfil) s2.get(Perfil.class, p.getId());
        org.hibernate.Hibernate.initialize(pLeido.getUsuario());
        assertNotNull(pLeido.getUsuario());
        assertEquals("lucia", pLeido.getUsuario().getLogin());

        s2.close();
    }
}
