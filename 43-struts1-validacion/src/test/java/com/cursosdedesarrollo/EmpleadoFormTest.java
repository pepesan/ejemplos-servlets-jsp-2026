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

    private ActionErrors validate(String nombre, String email, String telefono, String nif) {
        form.setNombre(nombre);
        form.setEmail(email);
        form.setTelefono(telefono);
        form.setNif(nif);
        return form.validate(null, null);
    }

    @Test
    public void formularioValidoNoGeneraErrores() {
        ActionErrors errors = validate("Ana García", "ana@test.com", "666777888", "12345678Z");
        assertTrue(errors.isEmpty());
    }

    @Test
    public void nombreVacioGeneraError() {
        ActionErrors errors = validate("", "ana@test.com", "666777888", "12345678Z");
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("nombre"));
    }

    @Test
    public void nombreDeUnCaracterGeneraError() {
        ActionErrors errors = validate("A", "ana@test.com", "666777888", "12345678Z");
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("nombre"));
    }

    @Test
    public void emailVacioGeneraError() {
        ActionErrors errors = validate("Ana", "", "666777888", "12345678Z");
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("email"));
    }

    @Test
    public void emailSinArrobaGeneraError() {
        ActionErrors errors = validate("Ana", "noesuncorreo", "666777888", "12345678Z");
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("email"));
    }

    @Test
    public void telefonoConLetrasGeneraError() {
        ActionErrors errors = validate("Ana", "ana@test.com", "abc123", "12345678Z");
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("telefono"));
    }

    @Test
    public void telefonoDeMenosDe9DigitosGeneraError() {
        ActionErrors errors = validate("Ana", "ana@test.com", "12345678", "12345678Z");
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("telefono"));
    }

    @Test
    public void nifSinLetraFinalGeneraError() {
        ActionErrors errors = validate("Ana", "ana@test.com", "666777888", "12345678");
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("nif"));
    }

    @Test
    public void nifConLetraMinusculaGeneraError() {
        ActionErrors errors = validate("Ana", "ana@test.com", "666777888", "12345678z");
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("nif"));
    }

    @Test
    public void nifCorrectoNoGeneraError() {
        ActionErrors errors = validate("Ana", "ana@test.com", "666777888", "12345678Z");
        assertTrue(errors.isEmpty());
    }

    @Test
    public void todosCamposVaciosGeneraErrorEnCadaCampo() {
        ActionErrors errors = validate("", "", "", "");
        assertFalse(errors.isEmpty());
        assertTrue("nombre debe tener error", errors.get("nombre").hasNext());
        assertTrue("email debe tener error",  errors.get("email").hasNext());
        assertTrue("telefono debe tener error", errors.get("telefono").hasNext());
        assertTrue("nif debe tener error",    errors.get("nif").hasNext());
    }
}
