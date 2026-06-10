package com.cursosdedesarrollo;

import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class EditarPerfilServletTest {

    // ── validarNombre ──────────────────────────────────────────────────────────
    @Test public void nombreNullEsInvalido()    { assertNotNull(EditarPerfilServlet.validarNombre(null)); }
    @Test public void nombreVacioEsInvalido()   { assertNotNull(EditarPerfilServlet.validarNombre("")); }
    @Test public void nombreCortoEsInvalido()   { assertNotNull(EditarPerfilServlet.validarNombre("Ab")); }
    @Test public void nombreValidoTresChars()   { assertNull(EditarPerfilServlet.validarNombre("Ana")); }

    // ── validarEmail ───────────────────────────────────────────────────────────
    @Test public void emailNullEsInvalido()     { assertNotNull(EditarPerfilServlet.validarEmail(null)); }
    @Test public void emailSinArrobaEsInvalido(){ assertNotNull(EditarPerfilServlet.validarEmail("noemail")); }
    @Test public void emailConArrobaEsValido()  { assertNull(EditarPerfilServlet.validarEmail("a@b.com")); }

    // ── validarTelefono ────────────────────────────────────────────────────────
    @Test public void telefonoVacioEsValido()   { assertNull(EditarPerfilServlet.validarTelefono("")); }
    @Test public void telefonoNullEsValido()    { assertNull(EditarPerfilServlet.validarTelefono(null)); }
    @Test public void telefonoCortoEsInvalido() { assertNotNull(EditarPerfilServlet.validarTelefono("12345")); }
    @Test public void telefonoLetrasEsInvalido(){ assertNotNull(EditarPerfilServlet.validarTelefono("abc123456")); }
    @Test public void telefonoNueveDigitosValido(){ assertNull(EditarPerfilServlet.validarTelefono("612345678")); }

    // ── validarCiudad ──────────────────────────────────────────────────────────
    @Test public void ciudadNullEsInvalida()    { assertNotNull(EditarPerfilServlet.validarCiudad(null)); }
    @Test public void ciudadVaciaEsInvalida()   { assertNotNull(EditarPerfilServlet.validarCiudad("")); }
    @Test public void ciudadValidaEsValida()    { assertNull(EditarPerfilServlet.validarCiudad("Madrid")); }

    // ── validar (bean completo) ────────────────────────────────────────────────
    @Test
    public void beanValidoSinErrores() {
        PerfilBean b = new PerfilBean(1, "Ana García", "ana@test.com", "612345678", "Madrid", "admin", true);
        assertTrue(EditarPerfilServlet.validar(b).isEmpty());
    }

    @Test
    public void beanValidoTelefonoOpcionalVacio() {
        PerfilBean b = new PerfilBean(1, "Ana García", "ana@test.com", "", "Madrid", "usuario", true);
        assertTrue(EditarPerfilServlet.validar(b).isEmpty());
    }

    @Test
    public void beanConNombreYCiudadVaciosTieneErrores() {
        PerfilBean b = new PerfilBean(1, "", "ana@test.com", "", "", "usuario", true);
        Map<String, String> errores = EditarPerfilServlet.validar(b);
        assertEquals(2, errores.size());
        assertTrue(errores.containsKey("nombre"));
        assertTrue(errores.containsKey("ciudad"));
    }
}
