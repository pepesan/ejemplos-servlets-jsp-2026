package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class SubirFicheroActionTest {

    @Test
    public void formataTamanioBytes() {
        assertEquals("512 B", SubirFicheroAction.formatearTam(512));
    }

    @Test
    public void formataTamanioKb() {
        String r = SubirFicheroAction.formatearTam(2048);
        assertTrue(r.contains("KB"));
        assertTrue(r.startsWith("2.0"));
    }

    @Test
    public void formataTamanioMb() {
        String r = SubirFicheroAction.formatearTam(2 * 1024 * 1024);
        assertTrue(r.contains("MB"));
        assertTrue(r.startsWith("2.00"));
    }
}
