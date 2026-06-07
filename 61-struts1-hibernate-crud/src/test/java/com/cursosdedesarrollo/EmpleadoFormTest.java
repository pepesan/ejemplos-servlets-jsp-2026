package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmpleadoFormTest {

    private EmpleadoForm form;

    @Before
    public void setUp() {
        form = new EmpleadoForm();
    }

    @Test
    public void formVacioTieneTreeErrores() {
        ActionErrors errors = form.validate(null, null);
        assertEquals(3, errors.size());
    }

    @Test
    public void formCompletoNoTieneErrores() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("40000");
        ActionErrors errors = form.validate(null, null);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void salarioNoNumericoEsError() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("no-es-numero");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("salario"));
    }

    @Test
    public void salarioNegativoEsError() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("-100");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("salario"));
    }

    @Test
    public void salarioCeroEsValido() {
        form.setNombre("Becario");
        form.setDepartamento("IT");
        form.setSalario("0");
        ActionErrors errors = form.validate(null, null);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void nombreSoloBlancoEsError() {
        form.setNombre("   ");
        form.setDepartamento("IT");
        form.setSalario("40000");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("nombre"));
    }

    @Test
    public void campoBusquedaNoAfectaAValidacion() {
        form.setNombre("Ana");
        form.setDepartamento("IT");
        form.setSalario("40000");
        form.setBusqueda("esto no se valida");
        ActionErrors errors = form.validate(null, null);
        assertTrue(errors.isEmpty());
    }
}
