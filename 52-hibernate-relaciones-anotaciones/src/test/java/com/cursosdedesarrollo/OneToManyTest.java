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
 * Demuestra @OneToMany en sus dos variantes:
 *
 *  UNIDIRECCIONAL: Categoria → Articulo
 *    - Categoria tiene la lista de artículos con @JoinColumn.
 *    - Articulo no tiene ningún @ManyToOne — no sabe a qué categoría pertenece.
 *    - La FK categoria_id está en la tabla articulos pero Articulo no la expone.
 *
 *  BIDIRECCIONAL: Departamento ↔ Empleado
 *    - Departamento tiene @OneToMany(mappedBy="departamento").
 *    - Empleado tiene @ManyToOne → Departamento (es el dueño de la FK).
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
        s.save(java); // cascade=ALL guarda los artículos

        t.commit();
        s.close();

        // Navegar Categoria → artículos
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Categoria leida = (Categoria) s2.get(Categoria.class, java.getId());
        org.hibernate.Hibernate.initialize(leida.getArticulos());
        assertEquals(2, leida.getArticulos().size());
        s2.close();
    }

    @Test
    public void uni_articuloNoConoceCategoria() {
        // Articulo no tiene campo "categoria" — la relación es solo desde Categoria
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Articulo art = new Articulo("Artículo huérfano");
        Categoria cat = new Categoria("Test");
        cat.getArticulos().add(art);
        s.save(cat);
        Long artId = cat.getArticulos().get(0).getId();
        t.commit();
        s.close();

        // El Articulo existe pero para saber su categoría hay que usar HQL
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Articulo leido = (Articulo) s2.get(Articulo.class, artId);
        assertNotNull(leido);
        // leido.getCategoria() no existe — no hay ese campo en Articulo
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

        // Quitar un artículo de la lista → orphanRemoval=true lo borra de BD
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
        ana.setDepartamento(it); // Empleado conoce a Departamento (@ManyToOne)
        s.save(ana);

        t.commit();
        s.close();

        // Navegar Empleado → Departamento (lado dueño de la FK)
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
        rrhh.addEmpleado(new Empleado("Luis",  25000)); // helper: asigna ambos lados
        rrhh.addEmpleado(new Empleado("María", 28000));
        s.save(rrhh);

        t.commit();
        s.close();

        // Navegar Departamento → Empleados (lado inverso mappedBy)
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

        // Desde Empleado → Departamento
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Empleado emp = (Empleado) s2.get(Empleado.class, pedro.getId());
        assertEquals("Dev", emp.getDepartamento().getNombre());

        // Desde Departamento → Empleados
        Departamento dpto = (Departamento) s2.get(Departamento.class, dev.getId());
        org.hibernate.Hibernate.initialize(dpto.getEmpleados());
        assertEquals(1, dpto.getEmpleados().size());
        assertEquals("Pedro", dpto.getEmpleados().get(0).getNombre());
        s2.close();
    }
}
