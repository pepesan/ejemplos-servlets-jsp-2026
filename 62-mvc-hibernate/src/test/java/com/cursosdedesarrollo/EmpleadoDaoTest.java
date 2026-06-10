package com.cursosdedesarrollo;

import com.cursosdedesarrollo.modelo.Empleado;
import com.cursosdedesarrollo.modelo.EmpleadoDao;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests de integración del DAO contra una BD H2 en memoria real.
 * No se usan mocks: Hibernate crea el esquema al inicializar la SessionFactory.
 */
public class EmpleadoDaoTest {

    private static EmpleadoDao dao;

    @BeforeClass
    public static void iniciarHibernate() {
        // Crea la SessionFactory → Hibernate genera el esquema H2
        HibernateUtil.getSessionFactory();
        dao = new EmpleadoDao();
    }

    @AfterClass
    public static void cerrarHibernate() {
        HibernateUtil.shutdown();
    }

    @Test
    public void guardarAsignaId() {
        Empleado e = new Empleado("Test Guardar", "IT", 30000.0, true);
        dao.guardar(e);
        assertNotNull("Hibernate debe asignar el id tras el INSERT", e.getId());
        assertTrue(e.getId() > 0);
    }

    @Test
    public void buscarPorIdExistenteDevuelveEmpleado() {
        Empleado nuevo = new Empleado("Test Buscar", "QA", 25000.0, true);
        dao.guardar(nuevo);

        Empleado encontrado = dao.buscarPorId(nuevo.getId());
        assertNotNull(encontrado);
        assertEquals("Test Buscar", encontrado.getNombre());
    }

    @Test
    public void buscarPorIdInexistenteDevuelveNull() {
        assertNull(dao.buscarPorId(99999L));
    }

    @Test
    public void listarTodosDevuelveTodosLosRegistros() {
        int antesTotal = dao.listarTodos().size();
        dao.guardar(new Empleado("Extra1", "Dpto", 20000.0, true));
        dao.guardar(new Empleado("Extra2", "Dpto", 21000.0, false));

        assertEquals(antesTotal + 2, dao.listarTodos().size());
    }

    @Test
    public void actualizarModificaElRegistro() {
        Empleado e = new Empleado("Original", "Dpto", 30000.0, true);
        dao.guardar(e);

        e.setNombre("Modificado");
        e.setSalario(35000.0);
        dao.actualizar(e);

        Empleado actualizado = dao.buscarPorId(e.getId());
        assertEquals("Modificado", actualizado.getNombre());
        assertEquals(35000.0, actualizado.getSalario(), 0.001);
    }

    @Test
    public void eliminarBorraElRegistro() {
        Empleado e = new Empleado("ABorrar", "Dpto", 10000.0, true);
        dao.guardar(e);
        Long id = e.getId();

        dao.eliminar(id);

        assertNull(dao.buscarPorId(id));
    }

    @Test
    public void buscarFiltraPorNombreYDepartamento() {
        dao.guardar(new Empleado("Búsqueda Nombre", "OtraDpto", 40000.0, true));
        dao.guardar(new Empleado("Otro Empleado",   "Búsqueda Depto", 40000.0, true));

        List<Empleado> porNombre = dao.buscar("Búsqueda Nombre");
        assertTrue(porNombre.stream().anyMatch(e -> e.getNombre().contains("Búsqueda")));

        List<Empleado> porDpto = dao.buscar("Búsqueda Depto");
        assertTrue(porDpto.stream().anyMatch(e -> "Búsqueda Depto".equals(e.getDepartamento())));
    }
}
