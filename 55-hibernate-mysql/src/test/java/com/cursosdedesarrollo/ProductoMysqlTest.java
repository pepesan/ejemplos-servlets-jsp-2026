package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Tests de integración contra MySQL.
 *
 * Requisito previo: servidor MySQL en ejecución.
 *   cd docker && docker compose up -d mysql
 *
 * Credenciales (hibernate.cfg.xml):
 *   host: localhost:3306  BD: cursosdb  usuario: curso  contraseña: curso123
 */
@Ignore("Requiere MySQL en marcha: cd docker && docker compose up -d mysql")
public class ProductoMysqlTest {

    @Test
    public void guardarYRecuperar() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Producto p = new Producto("Teclado mecánico", 89.99, "Periféricos");
        s.save(p);

        t.commit();
        Long id = p.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Producto leido = (Producto) s2.get(Producto.class, id);
        assertNotNull(leido);
        assertEquals("Teclado mecánico", leido.getNombre());
        assertEquals(89.99, leido.getPrecio(), 0.01);
        s2.close();
    }

    @Test
    public void consultaHQL() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(new Producto("Ratón inalámbrico", 29.99, "Periféricos"));
        s.save(new Producto("Monitor 27\"",      249.99, "Monitores"));
        s.save(new Producto("Webcam HD",          49.99, "Periféricos"));
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Producto> perifericos = s2.createQuery(
                "from Producto p where p.categoria = :cat order by p.precio")
                .setString("cat", "Periféricos")
                .list();
        assertFalse(perifericos.isEmpty());
        // El primero por precio ascendente es el ratón
        assertEquals("Ratón inalámbrico", perifericos.get(0).getNombre());
        s2.close();
    }

    @Test
    public void actualizarProducto() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Producto p = new Producto("Auriculares", 59.99, "Audio");
        s.save(p);
        t.commit();
        Long id = p.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction t2 = s2.beginTransaction();
        Producto editar = (Producto) s2.get(Producto.class, id);
        editar.setPrecio(49.99);
        t2.commit();
        s2.close();

        Session s3 = HibernateUtil.getSessionFactory().openSession();
        assertEquals(49.99, ((Producto) s3.get(Producto.class, id)).getPrecio(), 0.01);
        s3.close();
    }

    @Test
    public void eliminarProducto() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Producto p = new Producto("Producto a borrar", 1.0, "Test");
        s.save(p);
        t.commit();
        Long id = p.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction t2 = s2.beginTransaction();
        s2.delete(s2.get(Producto.class, id));
        t2.commit();
        s2.close();

        Session s3 = HibernateUtil.getSessionFactory().openSession();
        assertNull(s3.get(Producto.class, id));
        s3.close();
    }
}
