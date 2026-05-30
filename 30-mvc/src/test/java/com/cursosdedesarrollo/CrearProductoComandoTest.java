package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class CrearProductoComandoTest {

    // ── validarNombre ──────────────────────────────────────────────────────────

    @Test public void nombreNullEsInvalido()     { assertNotNull(CrearProductoComando.validarNombre(null)); }
    @Test public void nombreVacioEsInvalido()    { assertNotNull(CrearProductoComando.validarNombre("")); }
    @Test public void nombreUnCaracterInvalido() { assertNotNull(CrearProductoComando.validarNombre("A")); }
    @Test public void nombreDosCaracteresValido(){ assertNull(CrearProductoComando.validarNombre("AB")); }
    @Test public void nombreNormalEsValido()     { assertNull(CrearProductoComando.validarNombre("Teclado mecánico")); }

    // ── validarPrecio ──────────────────────────────────────────────────────────

    @Test public void precioNullEsInvalido()     { assertNotNull(CrearProductoComando.validarPrecio(null)); }
    @Test public void precioVacioEsInvalido()    { assertNotNull(CrearProductoComando.validarPrecio("")); }
    @Test public void precioTextoEsInvalido()    { assertNotNull(CrearProductoComando.validarPrecio("abc")); }
    @Test public void precioCeroEsInvalido()     { assertNotNull(CrearProductoComando.validarPrecio("0")); }
    @Test public void precioNegativoEsInvalido() { assertNotNull(CrearProductoComando.validarPrecio("-5")); }
    @Test public void precioPositivoEsValido()   { assertNull(CrearProductoComando.validarPrecio("19.99")); }
    @Test public void precioEnteroEsValido()     { assertNull(CrearProductoComando.validarPrecio("100")); }
    @Test public void precioConEspaciosValido()  { assertNull(CrearProductoComando.validarPrecio("  49.99  ")); }
}
