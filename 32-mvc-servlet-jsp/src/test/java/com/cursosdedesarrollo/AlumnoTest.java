package com.cursosdedesarrollo;

import com.cursosdedesarrollo.modelo.Alumno;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlumnoTest {

    @Test
    public void alumnoConNota5EsAprobado() {
        Alumno a = new Alumno("Test", "t@t.com", 5.0, true);
        assertTrue(a.isAprobado());
    }

    @Test
    public void alumnoConNota4_9EsSuspenso() {
        Alumno a = new Alumno("Test", "t@t.com", 4.9, true);
        assertFalse(a.isAprobado());
    }

    @Test
    public void alumnoVacioTieneIdCero() {
        Alumno a = new Alumno();
        assertEquals(0, a.getId());
    }

    @Test
    public void gettersSettersFuncionan() {
        Alumno a = new Alumno();
        a.setId(7);
        a.setNombre("María");
        a.setEmail("m@m.com");
        a.setNota(8.3);
        a.setActivo(false);

        assertEquals(7, a.getId());
        assertEquals("María", a.getNombre());
        assertEquals("m@m.com", a.getEmail());
        assertEquals(8.3, a.getNota(), 0.001);
        assertFalse(a.isActivo());
    }
}
