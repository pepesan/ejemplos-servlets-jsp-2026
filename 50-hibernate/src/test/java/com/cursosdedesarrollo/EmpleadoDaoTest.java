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
        Empleado e = dao.guardar(new Empleado("Ana", "IT", 30000));
        assertNotNull(e.getId());
        assertTrue(e.getId() > 0);
    }

    @Test
    public void buscarPorIdDevuelveElMismoEmpleado() {
        Empleado guardado = dao.guardar(new Empleado("Carlos", "RRHH", 25000));
        Empleado leido    = dao.buscarPorId(guardado.getId());
        assertNotNull(leido);
        assertEquals("Carlos", leido.getNombre());
        assertEquals("RRHH",   leido.getDepartamento());
        assertEquals(25000,     leido.getSalario(), 0.01);
    }

    @Test
    public void buscarPorIdInexistenteDevuelveNull() {
        assertNull(dao.buscarPorId(99999L));
    }

    @Test
    public void listarTodosDevuelveTodos() {
        dao.guardar(new Empleado("Ana",    "IT",   30000));
        dao.guardar(new Empleado("Carlos", "RRHH", 25000));
        assertEquals(2, dao.listarTodos().size());
    }

    @Test
    public void actualizarModificaDatos() {
        Empleado e = dao.guardar(new Empleado("Pedro", "IT", 20000));
        e.setSalario(28000);
        e.setDepartamento("Ventas");
        dao.actualizar(e);

        Empleado leido = dao.buscarPorId(e.getId());
        assertEquals(28000,    leido.getSalario(),     0.01);
        assertEquals("Ventas", leido.getDepartamento());
    }

    @Test
    public void eliminarBorraElEmpleado() {
        Empleado e = dao.guardar(new Empleado("María", "IT", 32000));
        Long id = e.getId();
        dao.eliminar(id);
        assertNull(dao.buscarPorId(id));
    }

    @Test
    public void buscarPorDepartamentoFiltrasCorrectamente() {
        dao.guardar(new Empleado("Ana",   "IT",    30000));
        dao.guardar(new Empleado("Luis",  "IT",    28000));
        dao.guardar(new Empleado("María", "Ventas",25000));

        List<Empleado> it = dao.buscarPorDepartamento("IT");
        assertEquals(2, it.size());
        for (Empleado emp : it) {
            assertEquals("IT", emp.getDepartamento());
        }
    }
}
