package com.cursosdedesarrollo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton que mantiene la SessionFactory compartida.
 * Lee hibernate.cfg.xml desde el classpath.
 * Usa new Configuration() — adecuado para mapeos XML (hbm.xml).
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }
}
