package com.cursosdedesarrollo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Punto de acceso único a la SessionFactory de Hibernate.
 *
 * La SessionFactory es un objeto pesado (crea el pool de conexiones,
 * parsea los mappings, compila las queries): se crea UNA SOLA VEZ
 * al arrancar la aplicación y se destruye al pararla.
 *
 * {@link HibernateListener} se encarga del ciclo de vida (init/shutdown).
 */
public class HibernateUtil {

    // Se inicializa al cargar la clase; lee hibernate.cfg.xml del classpath
    private static final SessionFactory SESSION_FACTORY =
            new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }
}
