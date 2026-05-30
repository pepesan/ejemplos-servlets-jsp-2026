package com.cursosdedesarrollo;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class ContactoRepositorioTest {

    @Before
    public void limpiar() {
        ContactoRepositorio.reset();
    }

    @Test
    public void repositorioVacioTrasReset() {
        assertTrue(ContactoRepositorio.listarTodos().isEmpty());
    }

    @Test
    public void guardarAsignaIdAutoincremental() {
        Contacto c1 = ContactoRepositorio.guardar(new Contacto("A", "a@a.com", "", Contacto.AMIGO));
        Contacto c2 = ContactoRepositorio.guardar(new Contacto("B", "b@b.com", "", Contacto.TRABAJO));
        assertEquals(1, c1.getId());
        assertEquals(2, c2.getId());
    }

    @Test
    public void listarTodosDevuelveTodos() {
        ContactoRepositorio.guardar(new Contacto("A", "a@a.com", "", Contacto.AMIGO));
        ContactoRepositorio.guardar(new Contacto("B", "b@b.com", "", Contacto.FAMILIA));
        assertEquals(2, ContactoRepositorio.listarTodos().size());
    }

    @Test
    public void buscarPorIdDevuelveContactoCorrecto() {
        ContactoRepositorio.guardar(new Contacto("A", "a@a.com", "", Contacto.AMIGO));
        Contacto c = ContactoRepositorio.guardar(new Contacto("B", "b@b.com", "", Contacto.TRABAJO));
        Contacto encontrado = ContactoRepositorio.buscarPorId(c.getId());
        assertNotNull(encontrado);
        assertEquals("B", encontrado.getNombre());
    }

    @Test
    public void buscarPorIdInexistenteDevuelveNull() {
        assertNull(ContactoRepositorio.buscarPorId(999));
    }

    @Test
    public void actualizarModificaDatos() {
        Contacto c = ContactoRepositorio.guardar(new Contacto("Ana", "ana@a.com", "", Contacto.AMIGO));
        c.setNombre("Ana Actualizada");
        c.setCategoria(Contacto.TRABAJO);
        assertTrue(ContactoRepositorio.actualizar(c));
        Contacto leido = ContactoRepositorio.buscarPorId(c.getId());
        assertEquals("Ana Actualizada", leido.getNombre());
        assertEquals(Contacto.TRABAJO,  leido.getCategoria());
    }

    @Test
    public void actualizarIdInexistenteDevuelveFalse() {
        Contacto c = new Contacto("X", "x@x.com", "", Contacto.AMIGO);
        c.setId(999);
        assertFalse(ContactoRepositorio.actualizar(c));
    }

    @Test
    public void eliminarBorraContacto() {
        Contacto c = ContactoRepositorio.guardar(new Contacto("A", "a@a.com", "", Contacto.AMIGO));
        int id = c.getId();
        assertTrue(ContactoRepositorio.eliminar(id));
        assertNull(ContactoRepositorio.buscarPorId(id));
    }

    @Test
    public void eliminarIdInexistenteDevuelveFalse() {
        assertFalse(ContactoRepositorio.eliminar(999));
    }

    @Test
    public void filtrarPorCategoriaDevuelveSoloLosDeEsaCategoria() {
        ContactoRepositorio.guardar(new Contacto("A", "a@a.com", "", Contacto.AMIGO));
        ContactoRepositorio.guardar(new Contacto("B", "b@b.com", "", Contacto.TRABAJO));
        ContactoRepositorio.guardar(new Contacto("C", "c@c.com", "", Contacto.AMIGO));
        List<Contacto> amigos = ContactoRepositorio.filtrarPorCategoria(Contacto.AMIGO);
        assertEquals(2, amigos.size());
        for (Contacto ct : amigos) {
            assertEquals(Contacto.AMIGO, ct.getCategoria());
        }
    }

    @Test
    public void filtrarCategoriaInexistenteDevuelveListaVacia() {
        ContactoRepositorio.guardar(new Contacto("A", "a@a.com", "", Contacto.AMIGO));
        List<Contacto> resultado = ContactoRepositorio.filtrarPorCategoria("DESCONOCIDA");
        assertTrue(resultado.isEmpty());
    }
}
