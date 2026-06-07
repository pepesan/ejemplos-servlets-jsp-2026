package com.cursosdedesarrollo;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ContactoRepositorioTest {

    @Before
    public void setUp() {
        ContactoRepositorio.limpiar();
    }

    @Test
    public void guardarAsignaIdAutomatico() {
        Contacto c = new Contacto(0, "Ana", "ana@test.com", "");
        ContactoRepositorio.guardar(c);

        assertTrue("El id debe ser mayor que 0", c.getId() > 0);
    }

    @Test
    public void buscarPorIdDevuelveElContactoGuardado() {
        Contacto c = new Contacto(0, "Luis", "luis@test.com", "600 000 001");
        ContactoRepositorio.guardar(c);

        Contacto encontrado = ContactoRepositorio.buscarPorId(c.getId());
        assertNotNull(encontrado);
        assertEquals("Luis", encontrado.getNombre());
        assertEquals("luis@test.com", encontrado.getEmail());
    }

    @Test
    public void buscarPorIdInexistenteDevuelveNull() {
        assertNull(ContactoRepositorio.buscarPorId(999));
    }

    @Test
    public void guardarActualizaContactoExistente() {
        Contacto c = new Contacto(0, "María", "maria@test.com", "");
        ContactoRepositorio.guardar(c);
        int id = c.getId();

        c.setNombre("María Actualizada");
        ContactoRepositorio.guardar(c);

        assertEquals("María Actualizada", ContactoRepositorio.buscarPorId(id).getNombre());
        assertEquals(1, ContactoRepositorio.listar().size());
    }

    @Test
    public void eliminarBorraElContacto() {
        Contacto c = new Contacto(0, "Pedro", "pedro@test.com", "");
        ContactoRepositorio.guardar(c);
        int id = c.getId();

        ContactoRepositorio.eliminar(id);

        assertNull(ContactoRepositorio.buscarPorId(id));
        assertTrue(ContactoRepositorio.listar().isEmpty());
    }

    @Test
    public void listarDevuelveTodosLosContactos() {
        ContactoRepositorio.guardar(new Contacto(0, "A", "a@test.com", ""));
        ContactoRepositorio.guardar(new Contacto(0, "B", "b@test.com", ""));
        ContactoRepositorio.guardar(new Contacto(0, "C", "c@test.com", ""));

        List<Contacto> lista = ContactoRepositorio.listar();
        assertEquals(3, lista.size());
    }

    @Test
    public void limpiarVaciaElRepositorio() {
        ContactoRepositorio.guardar(new Contacto(0, "X", "x@test.com", ""));
        ContactoRepositorio.limpiar();

        assertTrue(ContactoRepositorio.listar().isEmpty());
    }

    @Test
    public void idsCorrelativos() {
        ContactoRepositorio.guardar(new Contacto(0, "A", "a@test.com", ""));
        ContactoRepositorio.guardar(new Contacto(0, "B", "b@test.com", ""));

        List<Contacto> lista = ContactoRepositorio.listar();
        assertEquals(1, lista.get(0).getId());
        assertEquals(2, lista.get(1).getId());
    }
}
