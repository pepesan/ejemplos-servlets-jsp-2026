package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Prueba la lógica de ContactoForm.validate() sin contenedor.
 * validate() solo accede a los campos del form, no a ActionMapping ni HttpServletRequest,
 * por lo que se puede invocar pasando null para ambos parámetros.
 */
public class ContactoFormTest {

    private ContactoForm form;

    @Before
    public void setUp() {
        form = new ContactoForm();
    }

    private ContactoForm form(String nombre, String email, String mensaje) {
        form.setNombre(nombre);
        form.setEmail(email);
        form.setMensaje(mensaje);
        return form;
    }

    @Test
    public void formularioValidoNoGeneraErrores() {
        ActionErrors errors = form("Ana García", "ana@test.com", "Este es un mensaje largo").validate(null, null);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void nombreVacioGeneraError() {
        ActionErrors errors = form("", "ana@test.com", "Mensaje largo aquí").validate(null, null);
        assertNotNull(errors.get("nombre"));
        assertFalse(errors.isEmpty());
    }

    @Test
    public void emailVacioGeneraError() {
        ActionErrors errors = form("Ana", "", "Mensaje largo aquí").validate(null, null);
        assertNotNull(errors.get("email"));
    }

    @Test
    public void emailSinArrobaGeneraError() {
        ActionErrors errors = form("Ana", "noesuncorreo", "Mensaje largo aquí").validate(null, null);
        assertNotNull(errors.get("email"));
    }

    @Test
    public void emailConArrobaAlFinalGeneraError() {
        ActionErrors errors = form("Ana", "nombre@", "Mensaje largo aquí").validate(null, null);
        assertNotNull(errors.get("email"));
    }

    @Test
    public void mensajeVacioGeneraError() {
        ActionErrors errors = form("Ana", "ana@test.com", "").validate(null, null);
        assertNotNull(errors.get("mensaje"));
    }

    @Test
    public void mensajeCortoGeneraError() {
        ActionErrors errors = form("Ana", "ana@test.com", "corto").validate(null, null);
        assertNotNull(errors.get("mensaje"));
    }

    @Test
    public void mensajeDe10CaracteresEsValido() {
        ActionErrors errors = form("Ana", "ana@test.com", "1234567890").validate(null, null);
        assertFalse("mensaje válido no debe generar errores", errors.get("mensaje").hasNext());
    }

    @Test
    public void varioscamposInvalidosAcumulanErrores() {
        ActionErrors errors = form("", "", "").validate(null, null);
        assertEquals(3, errors.size());
    }
}
