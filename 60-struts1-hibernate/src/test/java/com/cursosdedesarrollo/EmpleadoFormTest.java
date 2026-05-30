package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmpleadoFormTest {

    private final EmpleadoForm form = new EmpleadoForm();

    // ── nombre ────────────────────────────────────────────────────────────────

    @Test
    public void nombreVacioGeneraError() {
        form.setDepartamento("IT");
        form.setSalario("2000");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
        assertTrue("debe haber error en campo nombre", errors.get("nombre").hasNext());
    }

    @Test
    public void nombrePresenteNoGeneraError() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("2000");
        ActionErrors errors = form.validate(null, null);
        assertFalse("nombre presente no debe generar error", errors.get("nombre").hasNext());
        assertTrue(errors.isEmpty());
    }

    // ── departamento ──────────────────────────────────────────────────────────

    @Test
    public void departamentoVacioGeneraError() {
        form.setNombre("Ana");
        form.setSalario("2000");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
    }

    // ── salario ───────────────────────────────────────────────────────────────

    @Test
    public void salarioVacioGeneraError() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
    }

    @Test
    public void salarioNoNumericoGeneraError() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("abc");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
    }

    @Test
    public void salarioNegativoGeneraError() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("-500");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
    }

    @Test
    public void salarioCeroEsValido() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("0");
        ActionErrors errors = form.validate(null, null);
        assertTrue(errors.isEmpty());
    }

    // ── formulario completo ───────────────────────────────────────────────────

    @Test
    public void formularioValidoSinErrores() {
        form.setNombre("Carlos");
        form.setDepartamento("RRHH");
        form.setSalario("3500.50");
        ActionErrors errors = form.validate(null, null);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void formularioVacioTieneTresErrores() {
        ActionErrors errors = form.validate(null, null);
        assertEquals(3, errors.size());
    }
}
