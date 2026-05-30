package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Demostración de HQL y Criteria API (Hibernate 3.x clásico).
 *
 * HQL: lenguaje orientado a objetos similar a SQL.
 *   - Opera sobre nombres de clases y campos Java, NO sobre nombres de tablas/columnas.
 *   - Soporta polimorfismo, funciones de agregación, paginación y parámetros nombrados.
 *
 * Criteria API: construcción de consultas programáticamente (sin strings HQL).
 *   - Deprecada en Hibernate 5, eliminada en Hibernate 6; sustituida por JPA Criteria.
 */
public class HqlCriteriaTest {

    private static boolean cargado = false;

    @Before
    public void cargarDatos() {
        if (cargado) return;
        cargado = true;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(new Producto("Teclado mecánico",   "Informática", 79.99, 50));
        s.save(new Producto("Ratón inalámbrico",  "Informática", 34.50, 80));
        s.save(new Producto("Monitor 24\"",       "Informática",229.00, 20));
        s.save(new Producto("Silla ergonómica",   "Mobiliario",  189.00, 15));
        s.save(new Producto("Auriculares BT",     "Audio",        59.99, 60));
        t.commit();
        s.close();
    }

    // ── HQL básico ─────────────────────────────────────────────────────────

    @Test
    public void hql_listarTodos() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> todos = s.createQuery("from Producto").list();
        assertFalse(todos.isEmpty());
        s.close();
    }

    @Test
    public void hql_filtrarConWhereYParametroNombrado() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> caros = s.createQuery("from Producto where precio > :min")
                .setDouble("min", 100.0)
                .list();
        for (Producto p : caros) {
            assertTrue(p.getPrecio() > 100.0);
        }
        s.close();
    }

    @Test
    public void hql_filtrarPorCategoriaYOrdenar() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> info = s.createQuery(
                "from Producto where categoria = :cat order by precio asc")
                .setString("cat", "Informática")
                .list();
        assertFalse(info.isEmpty());
        // Verificar orden ascendente
        for (int i = 1; i < info.size(); i++) {
            assertTrue(info.get(i).getPrecio() >= info.get(i - 1).getPrecio());
        }
        s.close();
    }

    @Test
    public void hql_agregacion_count_avg_max() {
        Session s = HibernateUtil.getSessionFactory().openSession();

        Long  count  = (Long)   s.createQuery("select count(p) from Producto p").uniqueResult();
        Double avg   = (Double) s.createQuery("select avg(p.precio) from Producto p").uniqueResult();
        Double max   = (Double) s.createQuery("select max(p.precio) from Producto p").uniqueResult();

        assertTrue(count > 0);
        assertTrue(avg   > 0);
        assertTrue(max   >= avg);
        s.close();
    }

    @Test
    public void hql_paginacion() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> pagina = s.createQuery("from Producto order by nombre")
                .setFirstResult(0)
                .setMaxResults(3)
                .list();
        assertEquals(3, pagina.size());
        s.close();
    }

    @Test
    public void hql_uniqueResult() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Producto teclado = (Producto) s.createQuery(
                "from Producto where nombre = :nom")
                .setString("nom", "Teclado mecánico")
                .uniqueResult();
        assertNotNull(teclado);
        assertEquals("Teclado mecánico", teclado.getNombre());
        s.close();
    }

    // ── Criteria API ────────────────────────────────────────────────────────

    @Test
    public void criteria_filtrarPorPrecioMinimo() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> caros = s.createCriteria(Producto.class)
                .add(Restrictions.gt("precio", 100.0))
                .list();
        for (Producto p : caros) {
            assertTrue(p.getPrecio() > 100.0);
        }
        s.close();
    }

    @Test
    public void criteria_ordenarYLimitar() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> primeros = s.createCriteria(Producto.class)
                .addOrder(Order.asc("precio"))
                .setMaxResults(2)
                .list();
        assertEquals(2, primeros.size());
        assertTrue(primeros.get(0).getPrecio() <= primeros.get(1).getPrecio());
        s.close();
    }

    @Test
    public void criteria_proyeccionCount() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Long count = (Long) s.createCriteria(Producto.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();
        assertTrue(count > 0);
        s.close();
    }

    @Test
    public void criteria_filtroCombinado_And() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> resultado = s.createCriteria(Producto.class)
                .add(Restrictions.eq("categoria", "Informática"))
                .add(Restrictions.lt("precio", 100.0))
                .list();
        for (Producto p : resultado) {
            assertEquals("Informática", p.getCategoria());
            assertTrue(p.getPrecio() < 100.0);
        }
        s.close();
    }
}
