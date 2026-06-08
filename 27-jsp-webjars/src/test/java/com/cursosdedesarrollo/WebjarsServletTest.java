package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class WebjarsServletTest {

    @Test
    public void locatorEncuentraJquery() {
        org.webjars.WebJarVersionLocator loc = new org.webjars.WebJarVersionLocator();
        String resultado = loc.path("jquery", "jquery.min.js");
        assertNotNull("jQuery debe estar en el classpath", resultado);
        assertTrue("Debe contener 3.7.1", resultado.contains("3.7.1"));
    }

    @Test
    public void locatorVersionJquery() {
        org.webjars.WebJarVersionLocator loc = new org.webjars.WebJarVersionLocator();
        assertEquals("3.7.1", loc.version("jquery"));
    }

    @Test
    public void locatorEncuentraBootstrap() {
        org.webjars.WebJarVersionLocator loc = new org.webjars.WebJarVersionLocator();
        String resultado = loc.path("bootstrap", "css/bootstrap.min.css");
        assertNotNull("Bootstrap debe estar en el classpath", resultado);
        assertTrue("Debe contener 5.3.3", resultado.contains("5.3.3"));
    }

    @Test
    public void locatorDevuelveNullParaWebjarInexistente() {
        org.webjars.WebJarVersionLocator loc = new org.webjars.WebJarVersionLocator();
        String resultado = loc.path("no-existe-este-webjar", "algo.js");
        assertNull("WebJar inexistente debe devolver null", resultado);
    }
}
