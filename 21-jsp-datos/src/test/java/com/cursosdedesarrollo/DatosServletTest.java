package com.cursosdedesarrollo;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class DatosServletTest {

    @Test
    public void listaColoresTieneCincoElementos() {
        assertEquals(5, DatosServlet.listaColores().size());
    }

    @Test
    public void listaNumsTieneCincoElementos() {
        assertEquals(5, DatosServlet.listaNums().size());
    }

    @Test
    public void listaNumsContieneValoresEsperados() {
        List<Integer> nums = DatosServlet.listaNums();
        assertTrue(nums.contains(10));
        assertTrue(nums.contains(50));
    }

    @Test
    public void listaProductosTieneCincoElementos() {
        assertEquals(5, DatosServlet.listaProductos().size());
    }

    @Test
    public void todosLosProductosTienenNombre() {
        for (Producto p : DatosServlet.listaProductos()) {
            assertNotNull(p.getNombre());
            assertFalse(p.getNombre().isEmpty());
        }
    }

    @Test
    public void todosLosProductosTienenPrecioPositivo() {
        for (Producto p : DatosServlet.listaProductos()) {
            assertTrue(p.getPrecio() > 0);
        }
    }

    @Test
    public void listaAlumnosTieneCincoElementos() {
        assertEquals(5, DatosServlet.listaAlumnos().size());
    }

    @Test
    public void hayAlumnosAprobadosYSuspensos() {
        List<Alumno> alumnos = DatosServlet.listaAlumnos();
        long aprobados = alumnos.stream().filter(Alumno::isAprobado).count();
        long suspensos = alumnos.stream().filter(a -> !a.isAprobado()).count();
        assertTrue("Debe haber al menos un aprobado", aprobados > 0);
        assertTrue("Debe haber al menos un suspenso", suspensos > 0);
    }

    @Test
    public void alumnoEjemploPropiedadesCorrectas() {
        Alumno a = DatosServlet.alumnoEjemplo();
        assertEquals("Ana", a.getNombre());
        assertTrue(a.isAprobado());
    }
}
