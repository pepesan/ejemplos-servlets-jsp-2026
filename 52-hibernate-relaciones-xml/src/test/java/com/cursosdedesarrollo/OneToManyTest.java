package com.cursosdedesarrollo;

import com.cursosdedesarrollo.unoamuchos.Articulo;
import com.cursosdedesarrollo.unoamuchos.Categoria;
import com.cursosdedesarrollo.unoamuchos.Departamento;
import com.cursosdedesarrollo.unoamuchos.Empleado;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Demuestra OneToMany en sus dos variantes:
 *
 *  UNIDIRECCIONAL: Categoria → Articulo
 *    - Categoria tiene la lista de artículos con FK en Categoria.hbm.xml.
 *    - Articulo no tiene ningún campo Categoria.
 *    - La FK categoria_id está en la tabla articulos pero Articulo no la expone.
 *
 *  BIDIRECCIONAL: Departamento ↔ Empleado
 *    - Departamento tiene la colección con inverse="true".
 *    - Empleado tiene many-to-one → Departamento (dueño de la FK).
 *    - Se puede navegar en ambas direcciones sin HQL extra.
 */
public class OneToManyTest {

    // ── UNIDIRECCIONAL: Categoria → Articulo ──────────────────────────────

    @Test
    public void uni_guardarCategoriaConArticulos() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Categoria java = new Categoria("Java");
        java.getArticulos().add(new Articulo("Introducción a Java"));
        java.getArticulos().add(new Articulo("Streams en Java 8"));
        s.save(java);

        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Categoria leida = (Categoria) s2.get(Categoria.class, java.getId());
        org.hibernate.Hibernate.initialize(leida.getArticulos());
        assertEquals(2, leida.getArticulos().size());
        s2.close();
    }

    @Test
    public void uni_articuloNoConoceCategoria() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Articulo art = new Articulo("Artículo huérfano");
        Categoria cat = new Categoria("Test");
        cat.getArticulos().add(art);
        s.save(cat);
        Long artId = cat.getArticulos().get(0).getId();
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Articulo leido = (Articulo) s2.get(Articulo.class, artId);
        assertNotNull(leido);
        Categoria catLeida = (Categoria) s2.createQuery(
                "select c from Categoria c join c.articulos a where a.id = :aid")
                .setLong("aid", artId)
                .uniqueResult();
        assertNotNull(catLeida);
        assertEquals("Test", catLeida.getNombre());
        s2.close();
    }

    @Test
    public void uni_orphanRemoval_borrarArticuloDeLaLista() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Categoria spring = new Categoria("Spring");
        spring.getArticulos().add(new Articulo("Spring Boot"));
        spring.getArticulos().add(new Articulo("Spring MVC"));
        s.save(spring);
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction t2 = s2.beginTransaction();
        Categoria leida = (Categoria) s2.get(Categoria.class, spring.getId());
        org.hibernate.Hibernate.initialize(leida.getArticulos());
        leida.getArticulos().remove(0);
        t2.commit();
        s2.close();

        Session s3 = HibernateUtil.getSessionFactory().openSession();
        Categoria final_ = (Categoria) s3.get(Categoria.class, spring.getId());
        org.hibernate.Hibernate.initialize(final_.getArticulos());
        assertEquals(1, final_.getArticulos().size());
        s3.close();
    }

    // ── BIDIRECCIONAL: Departamento ↔ Empleado ────────────────────────────

    @Test
    public void bi_empleadoConoceADepartamento() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Departamento it = new Departamento("IT");
        s.save(it);

        Empleado ana = new Empleado("Ana", 3000);
        ana.setDepartamento(it);
        s.save(ana);

        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Empleado leido = (Empleado) s2.get(Empleado.class, ana.getId());
        assertNotNull(leido.getDepartamento());
        assertEquals("IT", leido.getDepartamento().getNombre());
        s2.close();
    }

    @Test
    public void bi_departamentoConoceASusEmpleados() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Departamento rrhh = new Departamento("RRHH");
        rrhh.addEmpleado(new Empleado("Luis",  2500));
        rrhh.addEmpleado(new Empleado("María", 2800));
        s.save(rrhh);

        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Departamento leido = (Departamento) s2.get(Departamento.class, rrhh.getId());
        org.hibernate.Hibernate.initialize(leido.getEmpleados());
        assertEquals(2, leido.getEmpleados().size());
        s2.close();
    }

    @Test
    public void bi_navegarEnAmbasDirecciones() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Departamento dev = new Departamento("Dev");
        Empleado pedro = new Empleado("Pedro", 3500);
        dev.addEmpleado(pedro);
        s.save(dev);

        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Empleado emp = (Empleado) s2.get(Empleado.class, pedro.getId());
        assertEquals("Dev", emp.getDepartamento().getNombre());

        Departamento dpto = (Departamento) s2.get(Departamento.class, dev.getId());
        org.hibernate.Hibernate.initialize(dpto.getEmpleados());
        assertEquals(1, dpto.getEmpleados().size());
        assertEquals("Pedro", dpto.getEmpleados().get(0).getNombre());
        s2.close();
    }
}
