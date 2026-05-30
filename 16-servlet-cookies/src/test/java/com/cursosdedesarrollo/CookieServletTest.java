package com.cursosdedesarrollo;

import org.junit.Test;
import javax.servlet.http.Cookie;
import static org.junit.Assert.*;

public class CookieServletTest {

    @Test
    public void diasASegundosUnDia() {
        assertEquals(86400, CookieServlet.diasASegundos(1));
    }

    @Test
    public void diasASegundosTreintaDias() {
        assertEquals(2_592_000, CookieServlet.diasASegundos(30));
    }

    @Test
    public void crearSessionNoTieneMaxAge() {
        Cookie c = CookieServlet.crearSession("k", "v");
        assertEquals(-1, c.getMaxAge());   // -1 = sin Max-Age = sesión
    }

    @Test
    public void crearSessionGuardaNombreYValor() {
        Cookie c = CookieServlet.crearSession("nombre", "valor");
        assertEquals("nombre", c.getName());
        assertEquals("valor",  c.getValue());
    }

    @Test
    public void crearPersistenteTieneMaxAgeCorrecto() {
        Cookie c = CookieServlet.crearPersistente("k", "v", 7);
        assertEquals(CookieServlet.diasASegundos(7), c.getMaxAge());
    }

    @Test
    public void crearSeguraEsHttpOnly() {
        Cookie c = CookieServlet.crearSegura("k", "v");
        assertTrue(c.isHttpOnly());
    }

    @Test
    public void crearSeguraTienePathRestringido() {
        Cookie c = CookieServlet.crearSegura("k", "v");
        assertEquals("/cookies", c.getPath());
    }

    @Test
    public void crearEliminarTieneMaxAgeCero() {
        Cookie c = CookieServlet.crearEliminar("k");
        assertEquals(0, c.getMaxAge());
    }

    @Test
    public void crearEliminarTienePathRaiz() {
        Cookie c = CookieServlet.crearEliminar("k");
        assertEquals("/", c.getPath());
    }
}
