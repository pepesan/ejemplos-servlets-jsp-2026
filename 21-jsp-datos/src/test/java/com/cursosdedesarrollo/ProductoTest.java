package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductoTest {

    @Test
    public void constructorGuardaAtributos() {
        Producto p = new Producto(1, "Teclado", 89.99, "Electrónica", true);
        assertEquals(1,            p.getId());
        assertEquals("Teclado",    p.getNombre());
        assertEquals(89.99,        p.getPrecio(), 0.001);
        assertEquals("Electrónica",p.getCategoria());
        assertTrue(p.isDisponible());
    }

    @Test
    public void settersModificanAtributos() {
        Producto p = new Producto();
        p.setNombre("Ratón");
        p.setPrecio(34.50);
        p.setDisponible(false);
        assertEquals("Ratón", p.getNombre());
        assertEquals(34.50,   p.getPrecio(), 0.001);
        assertFalse(p.isDisponible());
    }

    @Test
    public void toStringContieneNombre() {
        Producto p = new Producto(2, "Monitor", 399.0, "Electrónica", false);
        assertTrue(p.toString().contains("Monitor"));
    }
}
