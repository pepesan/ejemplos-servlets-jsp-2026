package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactoDispatchActionTest {

    private ContactoDispatchAction action;
    private ContactoForm form;

    @Before
    public void setUp() {
        action = new ContactoDispatchAction();
        form   = new ContactoForm();
        ContactoRepositorio.limpiar();
    }

    // ── validar() ────────────────────────────────────────────────────────────

    @Test
    public void validarFormVacioDevuelveErroresDeNombreYEmail() {
        ActionErrors errors = action.validar(form);
        assertEquals(2, errors.size());
    }

    @Test
    public void validarConSoloNombreDevuelveErrorDeEmail() {
        form.setNombre("Ana");
        ActionErrors errors = action.validar(form);
        assertEquals(1, errors.size());
        assertNotNull(errors.get("email"));
    }

    @Test
    public void validarConSoloEmailDevuelveErrorDeNombre() {
        form.setEmail("ana@test.com");
        ActionErrors errors = action.validar(form);
        assertEquals(1, errors.size());
        assertNotNull(errors.get("nombre"));
    }

    @Test
    public void validarFormCompletoNoDevuelveErrores() {
        form.setNombre("Ana");
        form.setEmail("ana@test.com");
        ActionErrors errors = action.validar(form);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void validarNombreSoloBlancoEsError() {
        form.setNombre("   ");
        form.setEmail("ana@test.com");
        ActionErrors errors = action.validar(form);
        assertFalse(errors.isEmpty());
    }

    // ── repositorio (integración básica) ─────────────────────────────────────

    @Test
    public void repositorioGuardaYRecuperaContacto() {
        Contacto c = new Contacto(0, "Test", "test@test.com", "");
        ContactoRepositorio.guardar(c);
        assertNotNull(ContactoRepositorio.buscarPorId(c.getId()));
    }

    @Test
    public void repositorioEliminaContacto() {
        Contacto c = new Contacto(0, "Test", "test@test.com", "");
        ContactoRepositorio.guardar(c);
        ContactoRepositorio.eliminar(c.getId());
        assertNull(ContactoRepositorio.buscarPorId(c.getId()));
    }
}
