package com.cursosdedesarrollo;

import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class RegistroServletTest {

    // ── validarNombre ──────────────────────────────────────────────────────────
    @Test public void nombreNullEsInvalido()      { assertNotNull(RegistroServlet.validarNombre(null)); }
    @Test public void nombreVacioEsInvalido()     { assertNotNull(RegistroServlet.validarNombre("")); }
    @Test public void nombreCortoEsInvalido()     { assertNotNull(RegistroServlet.validarNombre("Ab")); }
    @Test public void nombreValidoTresChars()     { assertNull(RegistroServlet.validarNombre("Ana")); }
    @Test public void nombreValidoLargo()         { assertNull(RegistroServlet.validarNombre("Juan García")); }

    // ── validarEmail ───────────────────────────────────────────────────────────
    @Test public void emailNullEsInvalido()       { assertNotNull(RegistroServlet.validarEmail(null)); }
    @Test public void emailSinArrobaEsInvalido()  { assertNotNull(RegistroServlet.validarEmail("noemail")); }
    @Test public void emailConArrobaEsValido()    { assertNull(RegistroServlet.validarEmail("a@b.com")); }

    // ── validarPassword ────────────────────────────────────────────────────────
    @Test public void passwordNullEsInvalida()    { assertNotNull(RegistroServlet.validarPassword(null)); }
    @Test public void passwordCortaEsInvalida()   { assertNotNull(RegistroServlet.validarPassword("abc")); }
    @Test public void passwordSeisCharsValida()   { assertNull(RegistroServlet.validarPassword("abcdef")); }

    // ── validarEdad ────────────────────────────────────────────────────────────
    @Test public void edadNullEsInvalida()        { assertNotNull(RegistroServlet.validarEdad(null)); }
    @Test public void edadNoNumericaEsInvalida()  { assertNotNull(RegistroServlet.validarEdad("abc")); }
    @Test public void edadMenorDe18EsInvalida()   { assertNotNull(RegistroServlet.validarEdad("17")); }
    @Test public void edadMayorDe120EsInvalida()  { assertNotNull(RegistroServlet.validarEdad("121")); }
    @Test public void edad18EsValida()            { assertNull(RegistroServlet.validarEdad("18")); }
    @Test public void edad30EsValida()            { assertNull(RegistroServlet.validarEdad("30")); }

    // ── validar (bean completo) ────────────────────────────────────────────────
    @Test
    public void beanValidoSinErrores() {
        RegistroBean b = new RegistroBean("Ana", "ana@test.com", "secreto123", "25");
        assertTrue(RegistroServlet.validar(b).isEmpty());
    }

    @Test
    public void beanVacioTieneCuatroErrores() {
        RegistroBean b = new RegistroBean("", "", "", "");
        Map<String, String> errores = RegistroServlet.validar(b);
        assertEquals(4, errores.size());
        assertTrue(errores.containsKey("nombre"));
        assertTrue(errores.containsKey("email"));
        assertTrue(errores.containsKey("password"));
        assertTrue(errores.containsKey("edad"));
    }
}
