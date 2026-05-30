package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class GenerarErrorServletTest {

    @Test
    public void tiposReconocidos() {
        assertTrue(GenerarErrorServlet.tipoReconocido("404"));
        assertTrue(GenerarErrorServlet.tipoReconocido("500"));
        assertTrue(GenerarErrorServlet.tipoReconocido("runtime"));
        assertTrue(GenerarErrorServlet.tipoReconocido("npe"));
        assertTrue(GenerarErrorServlet.tipoReconocido("aritm"));
    }

    @Test
    public void tiposNoReconocidos() {
        assertFalse(GenerarErrorServlet.tipoReconocido(null));
        assertFalse(GenerarErrorServlet.tipoReconocido(""));
        assertFalse(GenerarErrorServlet.tipoReconocido("desconocido"));
        assertFalse(GenerarErrorServlet.tipoReconocido("403"));
    }

    @Test
    public void descripcion404() {
        assertTrue(GenerarErrorServlet.descripcion("404").contains("404"));
    }

    @Test
    public void descripcionRuntimeContieneRuntime() {
        assertTrue(GenerarErrorServlet.descripcion("runtime").toLowerCase().contains("runtime"));
    }

    @Test
    public void descripcionNullDevuelveDesconocido() {
        assertEquals("desconocido", GenerarErrorServlet.descripcion(null));
    }

    @Test
    public void descripcionTipoDesconocidoDevuelveElMismo() {
        assertEquals("xyz", GenerarErrorServlet.descripcion("xyz"));
    }
}
