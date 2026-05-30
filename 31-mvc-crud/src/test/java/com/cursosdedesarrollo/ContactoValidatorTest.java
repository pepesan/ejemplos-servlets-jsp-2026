package com.cursosdedesarrollo;

import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class ContactoValidatorTest {

    // ── validarNombre ──────────────────────────────────────────────────────────
    @Test public void nombreNullEsInvalido()       { assertNotNull(ContactoValidator.validarNombre(null)); }
    @Test public void nombreVacioEsInvalido()      { assertNotNull(ContactoValidator.validarNombre("")); }
    @Test public void nombreUnCharEsInvalido()     { assertNotNull(ContactoValidator.validarNombre("A")); }
    @Test public void nombreDosCharsEsValido()     { assertNull(ContactoValidator.validarNombre("AB")); }
    @Test public void nombreNormalEsValido()       { assertNull(ContactoValidator.validarNombre("Ana García")); }

    // ── validarEmail ───────────────────────────────────────────────────────────
    @Test public void emailNullEsInvalido()        { assertNotNull(ContactoValidator.validarEmail(null)); }
    @Test public void emailVacioEsInvalido()       { assertNotNull(ContactoValidator.validarEmail("")); }
    @Test public void emailSinArrobaEsInvalido()   { assertNotNull(ContactoValidator.validarEmail("noemail")); }
    @Test public void emailConArrobaEsValido()     { assertNull(ContactoValidator.validarEmail("a@b.com")); }

    // ── validarTelefono ────────────────────────────────────────────────────────
    @Test public void telefonoNullEsOpcional()     { assertNull(ContactoValidator.validarTelefono(null)); }
    @Test public void telefonoVacioEsOpcional()    { assertNull(ContactoValidator.validarTelefono("")); }
    @Test public void telefonoLetrasEsInvalido()   { assertNotNull(ContactoValidator.validarTelefono("abcdef")); }
    @Test public void telefonoDigitosEsValido()    { assertNull(ContactoValidator.validarTelefono("600111222")); }
    @Test public void telefonoConGuionEsValido()   { assertNull(ContactoValidator.validarTelefono("91-234-56-78")); }

    // ── validarCategoria ───────────────────────────────────────────────────────
    @Test public void categoriaNullEsInvalida()    { assertNotNull(ContactoValidator.validarCategoria(null)); }
    @Test public void categoriaVaciaEsInvalida()   { assertNotNull(ContactoValidator.validarCategoria("")); }
    @Test public void categoriaDesconocidaInvalida(){ assertNotNull(ContactoValidator.validarCategoria("VECINO")); }
    @Test public void categoriaAmigoEsValida()     { assertNull(ContactoValidator.validarCategoria("AMIGO")); }
    @Test public void categoriaTrabajoEsValida()   { assertNull(ContactoValidator.validarCategoria("TRABAJO")); }
    @Test public void categoriaFamiliaEsValida()   { assertNull(ContactoValidator.validarCategoria("FAMILIA")); }

    // ── validar completo ───────────────────────────────────────────────────────
    @Test
    public void datosValidosSinErrores() {
        Map<String, String> errores = ContactoValidator.validar("Ana", "ana@test.com", "600111222", "AMIGO");
        assertTrue(errores.isEmpty());
    }

    @Test
    public void datosVaciosTieneTresErrores() {
        // telefono es opcional: campo vacío no genera error
        Map<String, String> errores = ContactoValidator.validar("", "", "", "");
        assertEquals(3, errores.size());
        assertTrue(errores.containsKey("nombre"));
        assertTrue(errores.containsKey("email"));
        assertTrue(errores.containsKey("categoria"));
        assertFalse(errores.containsKey("telefono"));
    }

    @Test
    public void telefonoOpcionalNoGeneraError() {
        Map<String, String> errores = ContactoValidator.validar("Ana", "ana@test.com", "", "AMIGO");
        assertFalse(errores.containsKey("telefono"));
    }
}
