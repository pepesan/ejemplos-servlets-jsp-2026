package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * JOINED: tabla padre "vehiculos" + tablas hijas "coches" y "motos".
 *
 * SQL generado:
 *   CREATE TABLE vehiculos (id BIGINT PK, marca VARCHAR)
 *   CREATE TABLE coches    (id BIGINT FK → vehiculos.id, puertas INT)
 *   CREATE TABLE motos     (id BIGINT FK → vehiculos.id, cilindrada INT)
 *
 * Consulta polimórfica: "from Vehiculo" hace LEFT JOIN a coches y motos.
 */
public class HerenciaJoinedTest {

    @Test
    public void guardarSubclasesConTablaPropia() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Coche toyota = new Coche("Toyota", 4);
        Coche ford   = new Coche("Ford",   2);
        Moto  honda  = new Moto("Honda",   600);
        s.save(toyota); s.save(ford); s.save(honda);

        t.commit();
        s.close();

        // Verificar por ID: Coche en tabla "coches", Moto en tabla "motos"
        // pero ambas son Vehiculo (polimorfismo)
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        assertNotNull(s2.get(Coche.class,   toyota.getId()));
        assertNotNull(s2.get(Coche.class,   ford.getId()));
        assertNotNull(s2.get(Moto.class,    honda.getId()));
        assertTrue(s2.get(Vehiculo.class,   toyota.getId()) instanceof Coche);
        assertTrue(s2.get(Vehiculo.class,   honda.getId())  instanceof Moto);
        s2.close();
    }

    @Test
    public void columnaEspecificaDeSubclaseAccesible() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Coche c = new Coche("BMW", 4);
        s.save(c);
        t.commit();
        s.close();

        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Coche leido = (Coche) s2.get(Coche.class, c.getId());
        assertEquals(4, leido.getPuertas());
        assertEquals("BMW", leido.getMarca());
        s2.close();
    }
}
