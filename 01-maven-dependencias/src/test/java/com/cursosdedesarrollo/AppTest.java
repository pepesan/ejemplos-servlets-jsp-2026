package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void formatearNombreNormal() {
        assertEquals("Maven", App.formatearNombre("  MAVEN  "));
    }

    @Test
    public void formatearNombreNulo() {
        assertEquals("Desconocido", App.formatearNombre(null));
    }

    @Test
    public void formatearNombreBlanco() {
        assertEquals("Desconocido", App.formatearNombre("   "));
    }

    @Test
    public void esNumeroTrue() {
        assertTrue(App.esNumero("42"));
    }

    @Test
    public void esNumeroFalse() {
        assertFalse(App.esNumero("abc"));
    }
}
