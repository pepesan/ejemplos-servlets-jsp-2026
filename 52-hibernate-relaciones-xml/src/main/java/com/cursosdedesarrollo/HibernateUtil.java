package com.cursosdedesarrollo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() { return SESSION_FACTORY; }
    public static void shutdown()                    { SESSION_FACTORY.close(); }
}
