package com.cursosdedesarrollo;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Registro programático de servlets (Servlet 3.0+).
 *
 * Usa @WebListener para que el contenedor lo detecte sin necesidad de
 * declararlo en web.xml. En contextInitialized() tenemos acceso al
 * ServletContext antes de que llegue la primera petición, lo que nos
 * permite registrar servlets, filtros y listeners dinámicamente.
 */
@WebListener
public class RegistroListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        // Registrar RegistroServlet con url-pattern /registrado
        ServletRegistration.Dynamic reg =
                ctx.addServlet("registradoProgramaticamente", RegistroServlet.class);

        reg.addMapping("/registrado");
        reg.setInitParameter("origen", "RegistroListener.contextInitialized()");
        reg.setInitParameter("momento", "arranque del contexto");
        reg.setLoadOnStartup(1);

        ctx.log("[RegistroListener] RegistroServlet registrado en /registrado");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
