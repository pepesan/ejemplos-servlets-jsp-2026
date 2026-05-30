package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ContactoTest {

    @Test
    public void constructorVacioPermiteSetters() {
        Contacto c = new Contacto();
        c.setId(1);
        c.setNombre("Ana");
        c.setEmail("ana@test.com");
        c.setTelefono("600111222");
        c.setCategoria(Contacto.AMIGO);
        assertEquals(1,           c.getId());
        assertEquals("Ana",       c.getNombre());
        assertEquals("ana@test.com", c.getEmail());
        assertEquals("600111222", c.getTelefono());
        assertEquals("AMIGO",     c.getCategoria());
    }

    @Test
    public void constructorParametricoGuardaTodosLosCampos() {
        Contacto c = new Contacto("Carlos", "carlos@work.es", "912345678", Contacto.TRABAJO);
        assertEquals("Carlos",        c.getNombre());
        assertEquals("carlos@work.es",c.getEmail());
        assertEquals("912345678",     c.getTelefono());
        assertEquals("TRABAJO",       c.getCategoria());
    }

    @Test
    public void toStringContieneNombreYCategoria() {
        Contacto c = new Contacto("María", "m@m.com", "", Contacto.FAMILIA);
        c.setId(3);
        assertTrue(c.toString().contains("María"));
        assertTrue(c.toString().contains("FAMILIA"));
    }

    @Test
    public void constantesCategoriasDefinidas() {
        assertEquals("AMIGO",   Contacto.AMIGO);
        assertEquals("TRABAJO", Contacto.TRABAJO);
        assertEquals("FAMILIA", Contacto.FAMILIA);
    }
}
