package com.cursosdedesarrollo;

import com.cursosdedesarrollo.joined.Coche;
import com.cursosdedesarrollo.joined.Moto;
import com.cursosdedesarrollo.joined.Vehiculo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JOINED: tabla base "vehiculos" + tabla por subclase con FK.
 *
 * DDL resultante:
 *   CREATE TABLE vehiculos (id BIGINT PK, marca VARCHAR NOT NULL)
 *   CREATE TABLE coches    (id BIGINT PK → vehiculos.id, puertas INT NOT NULL)
 *   CREATE TABLE motos     (id BIGINT PK → vehiculos.id, cilindrada INT NOT NULL)
 */
public class HerenciaJoinedTest {

    @Test
    public void guardarCocheInsertaEnDosTablas() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Coche seat = new Coche("Seat Ibiza", 5);
        s.save(seat);

        t.commit();
        Long id = seat.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Coche leido = (Coche) s2.get(Coche.class, id);
        assertNotNull(leido);
        assertEquals("Seat Ibiza", leido.getMarca());
        assertEquals(5, leido.getPuertas());
        s2.close();
    }

    @Test
    public void consultaPolimorficaHaceJoin() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Coche c1 = new Coche("Toyota Corolla", 4);
        Moto  m1 = new Moto("Honda CBR", 600);
        Moto  m2 = new Moto("Yamaha MT-07", 689);
        s.save(c1); s.save(m1); s.save(m2);
        t.commit();
        Long c1Id = c1.getId(), m1Id = m1.getId(), m2Id = m2.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        assertTrue(s2.get(Vehiculo.class, c1Id) instanceof Coche);
        assertTrue(s2.get(Vehiculo.class, m1Id) instanceof Moto);
        assertTrue(s2.get(Vehiculo.class, m2Id) instanceof Moto);
        s2.close();
    }

    @Test
    public void instanceofTrabajaConPolimorfismo() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Coche c = new Coche("Ford Focus", 5);
        Moto  m = new Moto("Kawasaki Z900", 948);
        s.save(c); s.save(m);
        t.commit();
        Long cId = c.getId(), mId = m.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        assertTrue(s2.get(Vehiculo.class, cId) instanceof Coche);
        assertTrue(s2.get(Vehiculo.class, mId) instanceof Moto);
        s2.close();
    }

    @Test
    public void columnasDeSubclasePuedenSerNotNull() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Moto moto = new Moto("Ducati Monster", 937);
        s.save(moto);
        t.commit();
        Long id = moto.getId();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Moto leida = (Moto) s2.get(Moto.class, id);
        assertEquals(937, leida.getCilindrada());
        s2.close();
    }
}
