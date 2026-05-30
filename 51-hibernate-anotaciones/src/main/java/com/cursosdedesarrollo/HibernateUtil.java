package com.cursosdedesarrollo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Usa AnnotationConfiguration en lugar de Configuration para que Hibernate
 * procese las anotaciones JPA (@Entity, @Id, etc.) declaradas en las clases.
 *
 * Comparación con 50-hibernate:
 *   XML:         new Configuration().configure()
 *   Anotaciones: new AnnotationConfiguration().configure()
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        SESSION_FACTORY = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }
}
