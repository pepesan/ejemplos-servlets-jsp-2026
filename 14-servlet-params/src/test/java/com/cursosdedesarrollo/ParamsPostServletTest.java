package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParamsPostServletTest {

    @Test
    public void valorNullEsInvalido() {
        assertFalse(ParamsPostServlet.esValido(null));
    }

    @Test
    public void cadenaVaciaEsInvalida() {
        assertFalse(ParamsPostServlet.esValido(""));
    }

    @Test
    public void cadenaEspaciosEsInvalida() {
        assertFalse(ParamsPostServlet.esValido("   "));
    }

    @Test
    public void valorConTextoEsValido() {
        assertTrue(ParamsPostServlet.esValido("Ana"));
    }

    @Test
    public void valorConEspaciosInternosEsValido() {
        assertTrue(ParamsPostServlet.esValido("  Ana García  "));
    }
}
