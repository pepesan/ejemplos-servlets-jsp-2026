package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class SaludoActionTest {

    @Test
    public void nombreNullEsInvalido() {
        assertNotNull(SaludoAction.validarNombre(null));
    }

    @Test
    public void nombreVacioEsInvalido() {
        assertNotNull(SaludoAction.validarNombre(""));
    }

    @Test
    public void nombreSoloBlancoEsInvalido() {
        assertNotNull(SaludoAction.validarNombre("   "));
    }

    @Test
    public void nombreValidoDevuelveNull() {
        assertNull(SaludoAction.validarNombre("Ana"));
    }

    @Test
    public void nombreConEspaciosValidoDevuelveNull() {
        assertNull(SaludoAction.validarNombre("  Ana García  "));
    }

    @Test
    public void errorDevuelveClaveDeRecurso() {
        assertEquals("error.nombre.requerido", SaludoAction.validarNombre(null));
        assertEquals("error.nombre.requerido", SaludoAction.validarNombre(""));
    }
}
