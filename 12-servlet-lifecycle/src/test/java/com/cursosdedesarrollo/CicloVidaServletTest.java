package com.cursosdedesarrollo;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class CicloVidaServletTest {

    // Subclase que anula log() para no necesitar ServletContext en tests
    private static class ServletParaTest extends CicloVidaServlet {
        @Override public void log(String msg) { /* no-op */ }
        @Override public void log(String msg, Throwable t) { /* no-op */ }
    }

    private ServletParaTest servlet;

    @Before
    public void setUp() {
        servlet = new ServletParaTest();
    }

    @Test
    public void contadorIniciaCero() throws Exception {
        assertEquals(0, getContador().get());
    }

    @Test
    public void contadorEsAtomicInteger() throws Exception {
        Field f = CicloVidaServlet.class.getDeclaredField("contadorPeticiones");
        assertEquals(AtomicInteger.class, f.getType());
    }

    @Test
    public void initGuardaParametros() throws Exception {
        servlet.init(mockConfig("AutorTest", "2.0"));

        assertEquals("AutorTest", getField("autor"));
        assertEquals("2.0",       getField("version"));
    }

    @Test
    public void momentoInitSeEstableceEnInit() throws Exception {
        servlet.init(mockConfig("x", "y"));
        assertNotNull(getField("momentoInit"));
    }

    // -------------------------------------------------------------------------

    private AtomicInteger getContador() throws Exception {
        Field f = CicloVidaServlet.class.getDeclaredField("contadorPeticiones");
        f.setAccessible(true);
        return (AtomicInteger) f.get(servlet);
    }

    private Object getField(String nombre) throws Exception {
        Field f = CicloVidaServlet.class.getDeclaredField(nombre);
        f.setAccessible(true);
        return f.get(servlet);
    }

    private ServletConfig mockConfig(String autor, String version) {
        return new ServletConfig() {
            @Override public String getServletName() { return "cicloVidaTest"; }
            @Override public ServletContext getServletContext() { return null; }
            @Override public String getInitParameter(String name) {
                if ("autor".equals(name))   return autor;
                if ("version".equals(name)) return version;
                return null;
            }
            @Override public Enumeration<String> getInitParameterNames() {
                return Collections.enumeration(Arrays.asList("autor", "version"));
            }
        };
    }
}
