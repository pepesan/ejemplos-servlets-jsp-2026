package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class PatronesServletTest {

    // ── CatalogoServlet.parsearSegmentos ──────────────────────────────────────

    @Test
    public void parsearSegmentosNullDevuelveVacio() {
        assertArrayEquals(new String[0], CatalogoServlet.parsearSegmentos(null));
    }

    @Test
    public void parsearSegmentosBarraDevuelveVacio() {
        assertArrayEquals(new String[0], CatalogoServlet.parsearSegmentos("/"));
    }

    @Test
    public void parsearSegmentosConId() {
        assertArrayEquals(new String[]{"42"}, CatalogoServlet.parsearSegmentos("/42"));
    }

    @Test
    public void parsearSegmentosConIdYSubruta() {
        assertArrayEquals(new String[]{"42", "editar"}, CatalogoServlet.parsearSegmentos("/42/editar"));
    }

    @Test
    public void parsearSegmentosMultiplesNiveles() {
        assertArrayEquals(new String[]{"a", "b", "c"}, CatalogoServlet.parsearSegmentos("/a/b/c"));
    }

    // ── ExtensionServlet.extraerAccion ────────────────────────────────────────

    @Test
    public void extraerAccionSimple() {
        assertEquals("listar", ExtensionServlet.extraerAccion("/listar.do"));
    }

    @Test
    public void extraerAccionConPath() {
        assertEquals("guardar", ExtensionServlet.extraerAccion("/pedido/guardar.do"));
    }

    @Test
    public void extraerAccionPathProfundo() {
        assertEquals("crear", ExtensionServlet.extraerAccion("/admin/usuario/crear.do"));
    }

    @Test
    public void extraerAccionNullDevuelveVacio() {
        assertEquals("", ExtensionServlet.extraerAccion(null));
    }

    @Test
    public void extraerAccionVacioDevuelveVacio() {
        assertEquals("", ExtensionServlet.extraerAccion(""));
    }

    // ── DefaultServlet.esRaiz ─────────────────────────────────────────────────

    @Test
    public void esRaizConBarra() {
        assertTrue(DefaultServlet.esRaiz("/"));
    }

    @Test
    public void esRaizCadenaVacia() {
        assertTrue(DefaultServlet.esRaiz(""));
    }

    @Test
    public void esRaizOtraRuta() {
        assertFalse(DefaultServlet.esRaiz("/otra"));
    }

    @Test
    public void esRaizNull() {
        assertFalse(DefaultServlet.esRaiz(null));
    }
}
