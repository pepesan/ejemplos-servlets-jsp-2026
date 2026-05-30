package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AlumnoTest {

    @Test
    public void constructorGuardaAtributos() {
        Alumno a = new Alumno("Ana", "García", 8.5, true);
        assertEquals("Ana",    a.getNombre());
        assertEquals("García", a.getApellidos());
        assertEquals(8.5,      a.getNota(), 0.001);
        assertTrue(a.isActivo());
    }

    @Test
    public void nombreCompletoUneNombreYApellidos() {
        Alumno a = new Alumno("Ana", "García López", 7.0, true);
        assertEquals("Ana García López", a.getNombreCompleto());
    }

    @Test
    public void aprobadoConNotaMayorOIgualACinco() {
        assertTrue(new Alumno("A", "B", 5.0, true).isAprobado());
        assertTrue(new Alumno("A", "B", 9.5, true).isAprobado());
    }

    @Test
    public void suspensoConNotaMenorQueCinco() {
        assertFalse(new Alumno("A", "B", 4.9,  true).isAprobado());
        assertFalse(new Alumno("A", "B", 0.0,  true).isAprobado());
    }

    @Test
    public void toStringContieneNombreCompleto() {
        Alumno a = new Alumno("Luis", "Martínez", 6.0, true);
        assertTrue(a.toString().contains("Luis Martínez"));
    }
}
