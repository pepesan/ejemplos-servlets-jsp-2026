package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AnotacionesServletTest {

    // ── LoadOnStartupServlet.diferencia ───────────────────────────────────────

    @Test
    public void diferenciaMilisegundos() {
        assertEquals("250 ms", LoadOnStartupServlet.diferencia(1000L, 1250L));
    }

    @Test
    public void diferenciaSegundos() {
        assertEquals("2 s 500 ms", LoadOnStartupServlet.diferencia(0L, 2500L));
    }

    @Test
    public void diferenciaExactaUnSegundo() {
        assertEquals("1 s 0 ms", LoadOnStartupServlet.diferencia(0L, 1000L));
    }

    @Test
    public void diferenciaCero() {
        assertEquals("0 ms", LoadOnStartupServlet.diferencia(1000L, 1000L));
    }

    @Test
    public void diferenciaNegativaDevuelveNd() {
        assertEquals("n/d", LoadOnStartupServlet.diferencia(2000L, 1000L));
    }

    // ── LoadOnStartupServlet.fmt ──────────────────────────────────────────────

    @Test
    public void fmtNoEsNull() {
        assertNotNull(LoadOnStartupServlet.fmt(System.currentTimeMillis()));
    }

    @Test
    public void fmtTieneLongitudCorrecta() {
        // HH:mm:ss.SSS = 12 caracteres
        assertEquals(12, LoadOnStartupServlet.fmt(0L).length());
    }
}
