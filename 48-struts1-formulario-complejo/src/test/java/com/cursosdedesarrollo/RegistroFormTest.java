package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistroFormTest {

    private RegistroForm form;

    @Before
    public void setUp() {
        form = new RegistroForm();
    }

    private void rellenarValido() {
        form.setNombre("Ana");
        form.setApellidos("García López");
        form.setEmail("ana@example.com");
        form.setFechaNacimiento("1990-06-15");
        form.setGenero("f");
        form.setPais("es");
        form.setTecnologias(new String[]{"java", "sql"});
        form.setModalidad("remoto");
        form.setNivel("senior");
    }

    @Test
    public void formularioValidoNoTieneErrores() {
        rellenarValido();
        ActionErrors errors = form.validate(null, null);
        assertTrue("Formulario válido no debe tener errores", errors.isEmpty());
    }

    @Test
    public void nombreVacioGeneraError() {
        rellenarValido();
        form.setNombre("");
        ActionErrors errors = form.validate(null, null);
        assertFalse(errors.isEmpty());
        assertNotNull(errors.get("nombre"));
    }

    @Test
    public void emailSinArrobaGeneraError() {
        rellenarValido();
        form.setEmail("invalido.com");
        ActionErrors errors = form.validate(null, null);
        assertNotNull(errors.get("email"));
    }

    @Test
    public void emailVacioGeneraError() {
        rellenarValido();
        form.setEmail("");
        ActionErrors errors = form.validate(null, null);
        assertNotNull(errors.get("email"));
    }

    @Test
    public void sinTecnologiasGeneraError() {
        rellenarValido();
        form.setTecnologias(new String[0]);
        ActionErrors errors = form.validate(null, null);
        assertNotNull(errors.get("tecnologias"));
    }

    @Test
    public void fechaNacimientoFuturaGeneraError() {
        rellenarValido();
        form.setFechaNacimiento("2099-01-01");
        ActionErrors errors = form.validate(null, null);
        assertNotNull(errors.get("fechaNacimiento"));
    }

    @Test
    public void fechaNacimientoInvalidaGeneraError() {
        rellenarValido();
        form.setFechaNacimiento("no-es-fecha");
        ActionErrors errors = form.validate(null, null);
        assertNotNull(errors.get("fechaNacimiento"));
    }

    @Test
    public void comentariosMuyLargosGeneraError() {
        rellenarValido();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 501; i++) sb.append('x');
        form.setComentarios(sb.toString());
        ActionErrors errors = form.validate(null, null);
        assertNotNull(errors.get("comentarios"));
    }

    @Test
    public void fechaDisponiblePasadaGeneraError() {
        rellenarValido();
        form.setFechaDisponible("2020-01-01");
        ActionErrors errors = form.validate(null, null);
        assertNotNull(errors.get("fechaDisponible"));
    }

    @Test
    public void fechaDisponibleVaciaEsOpcional() {
        rellenarValido();
        form.setFechaDisponible("");
        ActionErrors errors = form.validate(null, null);
        assertTrue("Fecha de disponibilidad opcional no debe generar error si está vacía",
                   errors.isEmpty());
    }
}
