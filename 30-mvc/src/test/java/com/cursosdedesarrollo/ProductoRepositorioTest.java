package com.cursosdedesarrollo;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class ProductoRepositorioTest {

    @Before
    public void limpiar() {
        ProductoRepositorio.reset();
    }

    @Test
    public void repositorioVacioTrasReset() {
        assertTrue(ProductoRepositorio.listarTodos().isEmpty());
    }

    @Test
    public void guardarAsignaIdAutoincremental() {
        Producto p1 = ProductoRepositorio.guardar(new Producto("A", 10.0));
        Producto p2 = ProductoRepositorio.guardar(new Producto("B", 20.0));
        assertEquals(1, p1.getId());
        assertEquals(2, p2.getId());
    }

    @Test
    public void listarTodosDevuelveTodosLosProductos() {
        ProductoRepositorio.guardar(new Producto("A", 1.0));
        ProductoRepositorio.guardar(new Producto("B", 2.0));
        List<Producto> lista = ProductoRepositorio.listarTodos();
        assertEquals(2, lista.size());
    }

    @Test
    public void buscarPorIdDevuelveProductoCorrecto() {
        ProductoRepositorio.guardar(new Producto("Teclado", 79.99));
        Producto p = ProductoRepositorio.guardar(new Producto("Ratón", 34.50));
        int id = p.getId();
        Producto encontrado = ProductoRepositorio.buscarPorId(id);
        assertNotNull(encontrado);
        assertEquals("Ratón", encontrado.getNombre());
    }

    @Test
    public void buscarPorIdInexistenteDevuelveNull() {
        assertNull(ProductoRepositorio.buscarPorId(999));
    }

    @Test
    public void listarTodosEsInmutable() {
        ProductoRepositorio.guardar(new Producto("X", 5.0));
        List<Producto> lista = ProductoRepositorio.listarTodos();
        try {
            lista.add(new Producto("Y", 9.0));
            fail("Debería lanzar UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // esperado
        }
    }
}
