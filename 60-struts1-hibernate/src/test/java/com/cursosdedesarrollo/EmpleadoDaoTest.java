package com.cursosdedesarrollo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EmpleadoDaoTest {

    private static final EmpleadoDao dao = new EmpleadoDao();

    @Before
    public void limpiarTabla() {
        for (Empleado e : dao.listarTodos()) {
            dao.eliminar(e.getId());
        }
    }

    @AfterClass
    public static void cerrarHibernate() {
        HibernateUtil.shutdown();
    }

    @Test
    public void guardarAsignaId() {
        Empleado e = dao.guardar(new Empleado("Ana", "IT", 3000));
        assertNotNull(e.getId());
        assertTrue(e.getId() > 0);
    }

    @Test
    public void buscarPorIdDevuelveElMismoEmpleado() {
        Empleado guardado = dao.guardar(new Empleado("Carlos", "RRHH", 2500));
        Empleado leido    = dao.buscarPorId(guardado.getId());
        assertNotNull(leido);
        assertEquals("Carlos", leido.getNombre());
        assertEquals("RRHH",   leido.getDepartamento());
        assertEquals(2500,     leido.getSalario(), 0.01);
    }

    @Test
    public void buscarPorIdInexistenteDevuelveNull() {
        assertNull(dao.buscarPorId(99999L));
    }

    @Test
    public void listarTodosDevuelveTodos() {
        dao.guardar(new Empleado("Ana",    "IT",   3000));
        dao.guardar(new Empleado("Carlos", "RRHH", 2500));
        assertEquals(2, dao.listarTodos().size());
    }

    @Test
    public void actualizarModificaDatos() {
        Empleado e = dao.guardar(new Empleado("Pedro", "IT", 2000));
        e.setSalario(2800);
        e.setDepartamento("Ventas");
        dao.actualizar(e);
        Empleado actualizado = dao.buscarPorId(e.getId());
        assertEquals(2800,     actualizado.getSalario(), 0.01);
        assertEquals("Ventas", actualizado.getDepartamento());
    }

    @Test
    public void eliminarBorraElRegistro() {
        Empleado e = dao.guardar(new Empleado("Eva", "Marketing", 3200));
        dao.eliminar(e.getId());
        assertNull(dao.buscarPorId(e.getId()));
    }

    @Test
    public void eliminarIdInexistenteNoLanzaExcepcion() {
        dao.eliminar(99999L);
    }

    @Test
    public void listarTodosOrdenadoPorId() {
        Empleado a = dao.guardar(new Empleado("Primero", "IT",  1000));
        Empleado b = dao.guardar(new Empleado("Segundo", "IT",  2000));
        List<Empleado> lista = dao.listarTodos();
        assertEquals(2, lista.size());
        assertTrue(lista.get(0).getId() < lista.get(1).getId());
    }
}
