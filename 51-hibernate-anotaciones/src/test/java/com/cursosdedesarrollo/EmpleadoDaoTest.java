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
    }

    @Test
    public void buscarPorIdDevuelveElMismoEmpleado() {
        Empleado g = dao.guardar(new Empleado("Carlos", "RRHH", 2500));
        Empleado l = dao.buscarPorId(g.getId());
        assertNotNull(l);
        assertEquals("Carlos", l.getNombre());
        assertEquals("RRHH",   l.getDepartamento());
        assertEquals(2500,     l.getSalario(), 0.01);
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
        dao.actualizar(e);
        assertEquals(2800, dao.buscarPorId(e.getId()).getSalario(), 0.01);
    }

    @Test
    public void eliminarBorraElEmpleado() {
        Empleado e = dao.guardar(new Empleado("María", "IT", 3200));
        Long id = e.getId();
        dao.eliminar(id);
        assertNull(dao.buscarPorId(id));
    }

    @Test
    public void buscarPorDepartamentoFiltrasCorrectamente() {
        dao.guardar(new Empleado("Ana",   "IT",     3000));
        dao.guardar(new Empleado("Luis",  "IT",     2800));
        dao.guardar(new Empleado("María", "Ventas", 2500));
        List<Empleado> it = dao.buscarPorDepartamento("IT");
        assertEquals(2, it.size());
    }

    @Test
    public void campoTransientNoSePersiste() {
        Empleado e = dao.guardar(new Empleado("Test", "IT", 1000));
        e.setEtiqueta("memo");
        dao.actualizar(e);
        // @Transient: al recargar el objeto el campo etiqueta no se lee de BD
        Empleado recargado = dao.buscarPorId(e.getId());
        assertNull(recargado.getEtiqueta());
    }
}
