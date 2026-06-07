package com.cursosdedesarrollo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HibernateUtil.getSessionFactory();
        poblarDatosIniciales();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
    }

    private void poblarDatosIniciales() {
        EmpleadoDao dao = new EmpleadoDao();
        if (!dao.listarTodos().isEmpty()) return;

        dao.guardar(new Empleado("Ana García",     "Ingeniería", 42000.00));
        dao.guardar(new Empleado("Luis Pérez",     "Marketing",  35000.00));
        dao.guardar(new Empleado("María López",    "RRHH",       38000.00));
        dao.guardar(new Empleado("Carlos Sánchez", "Ingeniería", 45000.00));
        dao.guardar(new Empleado("Elena Martín",   "Finanzas",   40000.00));
    }
}
