package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EmpleadoDaoTest {

    private EmpleadoDao dao;

    @Before
    public void setUp() {
        dao = new EmpleadoDao();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.createQuery("delete from Empleado").executeUpdate();
        t.commit();
        s.close();
    }

    @Test
    public void guardarAsignaId() {
        Empleado e = new Empleado("Ana", "IT", 40000);
        dao.guardar(e);
        assertNotNull(e.getId());
        assertTrue(e.getId() > 0);
    }

    @Test
    public void buscarPorIdDevuelveElEmpleadoGuardado() {
        Empleado e = new Empleado("Luis", "Marketing", 35000);
        dao.guardar(e);
        Empleado encontrado = dao.buscarPorId(e.getId());
        assertNotNull(encontrado);
        assertEquals("Luis", encontrado.getNombre());
    }

    @Test
    public void buscarPorIdInexistenteDevuelveNull() {
        assertNull(dao.buscarPorId(9999L));
    }

    @Test
    public void listarTodosDevuelveTodosLosEmpleados() {
        dao.guardar(new Empleado("A", "X", 1000));
        dao.guardar(new Empleado("B", "Y", 2000));
        dao.guardar(new Empleado("C", "Z", 3000));
        assertEquals(3, dao.listarTodos().size());
    }

    @Test
    public void actualizarModificaElRegistro() {
        Empleado e = new Empleado("Pedro", "Ventas", 30000);
        dao.guardar(e);
        e.setNombre("Pedro Actualizado");
        e.setSalario(32000);
        dao.actualizar(e);
        Empleado actualizado = dao.buscarPorId(e.getId());
        assertEquals("Pedro Actualizado", actualizado.getNombre());
        assertEquals(32000, actualizado.getSalario(), 0.01);
    }

    @Test
    public void eliminarBorraElRegistro() {
        Empleado e = new Empleado("María", "RRHH", 38000);
        dao.guardar(e);
        Long id = e.getId();
        dao.eliminar(id);
        assertNull(dao.buscarPorId(id));
    }

    @Test
    public void buscarFiltraPorNombre() {
        dao.guardar(new Empleado("Ana García",  "IT",       42000));
        dao.guardar(new Empleado("Luis Pérez",  "Marketing", 35000));
        dao.guardar(new Empleado("Ana Martínez","Finanzas",  40000));

        List<Empleado> resultado = dao.buscar("ana");
        assertEquals(2, resultado.size());
    }

    @Test
    public void buscarFiltraPorDepartamento() {
        dao.guardar(new Empleado("Carlos", "Ingeniería", 45000));
        dao.guardar(new Empleado("Elena",  "Finanzas",   40000));
        dao.guardar(new Empleado("Sofía",  "Ingeniería", 43000));

        List<Empleado> resultado = dao.buscar("ingeniería");
        assertEquals(2, resultado.size());
    }

    @Test
    public void buscarSinCoincidenciasDevueltaListaVacia() {
        dao.guardar(new Empleado("Ana", "IT", 40000));
        List<Empleado> resultado = dao.buscar("xyz_inexistente");
        assertTrue(resultado.isEmpty());
    }
}
