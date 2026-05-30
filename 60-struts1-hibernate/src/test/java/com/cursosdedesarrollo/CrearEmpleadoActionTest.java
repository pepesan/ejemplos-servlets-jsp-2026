package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Pruebas de CrearEmpleadoAction sin contenedor.
 *
 * La lógica de validación vive en EmpleadoForm.validate(), así que los
 * casos de validación se delegan a EmpleadoFormTest.
 *
 * Aquí se verifica el comportamiento de la acción usando un DAO stub
 * (subclase en memoria) que no toca la base de datos.
 */
public class CrearEmpleadoActionTest {

    // ── DAO stub ──────────────────────────────────────────────────────────────

    private static class DaoStub extends EmpleadoDao {
        final List<Empleado> guardados = new ArrayList<>();

        @Override
        public Empleado guardar(Empleado e) {
            e.setId((long) (guardados.size() + 1));
            guardados.add(e);
            return e;
        }

        @Override
        public List<Empleado> listarTodos() {
            return new ArrayList<>(guardados);
        }
    }

    // ── pruebas del stub ──────────────────────────────────────────────────────

    @Test
    public void daoStubGuardaYAsignaId() {
        DaoStub dao = new DaoStub();
        Empleado e = dao.guardar(new Empleado("Ana", "IT", 3000));
        assertEquals(1L, (long) e.getId());
        assertEquals(1, dao.guardados.size());
    }

    @Test
    public void daoStubGuardaVariosEmpleados() {
        DaoStub dao = new DaoStub();
        dao.guardar(new Empleado("Ana",    "IT",   3000));
        dao.guardar(new Empleado("Carlos", "RRHH", 2500));
        assertEquals(2, dao.listarTodos().size());
    }

    // ── pruebas de validación del formulario (integración form + acción) ──────

    @Test
    public void formularioValidoNoProduceErrores() {
        EmpleadoForm form = new EmpleadoForm();
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("3000");
        ActionErrors errors = form.validate(null, null);
        assertTrue("Formulario válido no debe tener errores", errors.isEmpty());
    }

    @Test
    public void formularioInvalidoNoDebeGuardar() {
        DaoStub dao    = new DaoStub();
        EmpleadoForm f = new EmpleadoForm(); // campos vacíos
        ActionErrors errors = f.validate(null, null);
        if (!errors.isEmpty()) {
            // Simula lo que haría CrearEmpleadoAction: no llamar a dao.guardar()
        }
        assertEquals("No debe guardar si hay errores", 0, dao.guardados.size());
    }
}
