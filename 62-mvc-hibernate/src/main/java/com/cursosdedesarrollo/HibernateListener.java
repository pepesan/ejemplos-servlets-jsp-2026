package com.cursosdedesarrollo;

import com.cursosdedesarrollo.modelo.Empleado;
import com.cursosdedesarrollo.modelo.EmpleadoDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener del ciclo de vida de la aplicación web.
 *
 * Al arrancar Tomcat ({@code contextInitialized}):
 *   1. Fuerza la inicialización de HibernateUtil → construye la SessionFactory
 *      → Hibernate crea el esquema H2 ({@code hbm2ddl.auto=create-drop}).
 *   2. Inserta datos de ejemplo si la tabla está vacía.
 *
 * Al parar Tomcat ({@code contextDestroyed}):
 *   Cierra la SessionFactory → libera el pool de conexiones
 *   → Hibernate ejecuta el DROP del esquema H2.
 */
@WebListener
public class HibernateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Inicializa la SessionFactory (crea el esquema H2)
        HibernateUtil.getSessionFactory();
        poblarDatosIniciales();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
    }

    private void poblarDatosIniciales() {
        EmpleadoDao dao = new EmpleadoDao();
        if (!dao.listarTodos().isEmpty()) return;   // idempotente: no duplica datos

        dao.guardar(new Empleado("Ana García",     "Ingeniería", 42000.00, true));
        dao.guardar(new Empleado("Luis Pérez",     "Marketing",  35000.00, true));
        dao.guardar(new Empleado("María López",    "RRHH",       38000.00, false));
        dao.guardar(new Empleado("Carlos Sánchez", "Ingeniería", 45000.00, true));
        dao.guardar(new Empleado("Elena Martín",   "Finanzas",   40000.00, true));
    }
}
