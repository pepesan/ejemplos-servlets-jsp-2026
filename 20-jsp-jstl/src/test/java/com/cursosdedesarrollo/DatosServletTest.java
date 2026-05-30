package com.cursosdedesarrollo;

import org.junit.Test;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

public class DatosServletTest {

    @Test
    public void frutasContieneElementos() {
        List<String> frutas = DatosServlet.crearFrutas();
        assertFalse(frutas.isEmpty());
    }

    @Test
    public void frutasTieneExactamenteCinco() {
        assertEquals(5, DatosServlet.crearFrutas().size());
    }

    @Test
    public void frutasContieneManzana() {
        assertTrue(DatosServlet.crearFrutas().contains("Manzana"));
    }

    @Test
    public void productosContieneElementos() {
        List<Map<String, Object>> productos = DatosServlet.crearProductos();
        assertFalse(productos.isEmpty());
    }

    @Test
    public void productosTieneClavesObligatorias() {
        for (Map<String, Object> p : DatosServlet.crearProductos()) {
            assertTrue("Falta clave 'nombre'",     p.containsKey("nombre"));
            assertTrue("Falta clave 'precio'",     p.containsKey("precio"));
            assertTrue("Falta clave 'disponible'", p.containsKey("disponible"));
        }
    }

    @Test
    public void productosPrecioEsNumerico() {
        for (Map<String, Object> p : DatosServlet.crearProductos()) {
            assertTrue(p.get("precio") instanceof Number);
        }
    }

    @Test
    public void productosDisponibleEsBoolean() {
        for (Map<String, Object> p : DatosServlet.crearProductos()) {
            assertTrue(p.get("disponible") instanceof Boolean);
        }
    }

    @Test
    public void hayAlMenosUnProductoDisponible() {
        long disponibles = DatosServlet.crearProductos().stream()
                .filter(p -> Boolean.TRUE.equals(p.get("disponible")))
                .count();
        assertTrue(disponibles > 0);
    }
}
