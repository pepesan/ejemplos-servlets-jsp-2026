package com.cursosdedesarrollo;

import com.cursosdedesarrollo.muchosamuchos.Actor;
import com.cursosdedesarrollo.muchosamuchos.Etiqueta;
import com.cursosdedesarrollo.muchosamuchos.Pelicula;
import com.cursosdedesarrollo.muchosamuchos.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Demuestra @ManyToMany en sus dos variantes:
 *
 *  UNIDIRECCIONAL: Producto → Etiqueta
 *    - Solo Producto conoce a Etiqueta (tiene la @JoinTable).
 *    - Etiqueta no tiene ninguna referencia a Producto.
 *    - No se puede navegar Etiqueta → Productos sin HQL.
 *
 *  BIDIRECCIONAL: Pelicula ↔ Actor
 *    - Pelicula es el dueño (tiene @JoinTable actores_peliculas).
 *    - Actor tiene @ManyToMany(mappedBy="actores") como lado inverso.
 *    - Se puede navegar en ambas direcciones.
 */
public class ManyToManyTest {

    // ── UNIDIRECCIONAL: Producto → Etiqueta ───────────────────────────────

    @Test
    public void uni_productoConVariasEtiquetas() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Etiqueta oferta   = new Etiqueta("oferta");
        Etiqueta novedad  = new Etiqueta("novedad");
        s.save(oferta);
        s.save(novedad);

        Producto teclado = new Producto("Teclado mecánico");
        teclado.getEtiquetas().add(oferta);
        teclado.getEtiquetas().add(novedad);
        s.save(teclado);

        t.commit();
        s.close();

        // Navegar Producto → Etiquetas
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Producto leido = (Producto) s2.get(Producto.class, teclado.getId());
        org.hibernate.Hibernate.initialize(leido.getEtiquetas());
        assertEquals(2, leido.getEtiquetas().size());
        s2.close();
    }

    @Test
    public void uni_etiquetaCompartidaEntreProdcutos() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Etiqueta destacado = new Etiqueta("destacado");
        s.save(destacado);

        Producto p1 = new Producto("Ratón");
        Producto p2 = new Producto("Monitor");
        p1.getEtiquetas().add(destacado);
        p2.getEtiquetas().add(destacado);
        s.save(p1);
        s.save(p2);

        t.commit();
        s.close();

        // Etiqueta no conoce a sus productos: hay que usar HQL
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> conDestacado = s2.createQuery(
                "from Producto p join p.etiquetas e where e.nombre = :nom")
                .setString("nom", "destacado")
                .list();
        assertEquals(2, conDestacado.size());
        s2.close();
    }

    @Test
    public void uni_etiquetaNoConoceAProductos() {
        // Etiqueta.java no tiene ningún campo List<Producto>
        // Es puramente pasiva — esto demuestra la asimetría de la relación unidireccional
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Etiqueta e = new Etiqueta("premium");
        s.save(e);
        Producto p = new Producto("Auriculares");
        p.getEtiquetas().add(e);
        s.save(p);
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Etiqueta leida = (Etiqueta) s2.get(Etiqueta.class, e.getId());
        // Etiqueta solo tiene id y nombre — no tiene referencia a Producto
        assertNotNull(leida);
        assertEquals("premium", leida.getNombre());
        s2.close();
    }

    // ── BIDIRECCIONAL: Pelicula ↔ Actor ───────────────────────────────────

    @Test
    public void bi_peliculaConVariosActores() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Actor a1 = new Actor("Tom Hanks");
        Actor a2 = new Actor("Meryl Streep");
        s.save(a1);
        s.save(a2);

        Pelicula p = new Pelicula("Los Archivos del Pentágono");
        p.addActor(a1); // helper: añade a ambos lados
        p.addActor(a2);
        s.save(p);

        t.commit();
        s.close();

        // Navegar Pelicula → Actores (lado dueño)
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Pelicula leida = (Pelicula) s2.get(Pelicula.class, p.getId());
        org.hibernate.Hibernate.initialize(leida.getActores());
        assertEquals(2, leida.getActores().size());
        s2.close();
    }

    @Test
    public void bi_actorConoceSusPeliculas() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Actor clooney = new Actor("George Clooney");
        s.save(clooney);

        Pelicula p1 = new Pelicula("Gravity");
        Pelicula p2 = new Pelicula("Ocean's Eleven");
        p1.addActor(clooney);
        p2.addActor(clooney);
        s.save(p1);
        s.save(p2);

        t.commit();
        s.close();

        // Navegar Actor → Películas (lado inverso mappedBy)
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Actor leido = (Actor) s2.get(Actor.class, clooney.getId());
        org.hibernate.Hibernate.initialize(leido.getPeliculas());
        assertEquals(2, leido.getPeliculas().size());
        s2.close();
    }

    @Test
    public void bi_navegarEnAmbasDirecciones() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Actor streep = new Actor("Meryl Streep");
        s.save(streep);

        Pelicula diablo = new Pelicula("El diablo viste de Prada");
        diablo.addActor(streep);
        s.save(diablo);

        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();

        // Pelicula → Actor
        Pelicula pel = (Pelicula) s2.get(Pelicula.class, diablo.getId());
        org.hibernate.Hibernate.initialize(pel.getActores());
        assertEquals(1, pel.getActores().size());
        assertEquals("Meryl Streep", pel.getActores().get(0).getNombre());

        // Actor → Pelicula
        Actor act = (Actor) s2.get(Actor.class, streep.getId());
        org.hibernate.Hibernate.initialize(act.getPeliculas());
        assertEquals(1, act.getPeliculas().size());
        assertEquals("El diablo viste de Prada", act.getPeliculas().get(0).getTitulo());

        s2.close();
    }
}
