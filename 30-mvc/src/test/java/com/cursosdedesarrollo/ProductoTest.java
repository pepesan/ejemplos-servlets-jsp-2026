package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductoTest {

    @Test
    public void constructorVacioPermiteSetters() {
        Producto p = new Producto();
        p.setId(1);
        p.setNombre("Teclado");
        p.setPrecio(49.99);
        assertEquals(1,        p.getId());
        assertEquals("Teclado", p.getNombre());
        assertEquals(49.99,    p.getPrecio(), 0.001);
    }

    @Test
    public void constructorParametricoGuardaNombreYPrecio() {
        Producto p = new Producto("Ratón", 19.99);
        assertEquals("Ratón", p.getNombre());
        assertEquals(19.99,   p.getPrecio(), 0.001);
    }

    @Test
    public void toStringContieneNombreYPrecio() {
        Producto p = new Producto("Monitor", 199.0);
        String s = p.toString();
        assertTrue(s.contains("Monitor"));
        assertTrue(s.contains("199.0"));
    }
}
