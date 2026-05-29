package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class PersonaTest {

    @Test
    public void mayorDeEdad() {
        assertTrue(new Persona("Ana", 18).esMayorDeEdad());
    }

    @Test
    public void menorDeEdad() {
        assertFalse(new Persona("Luis", 17).esMayorDeEdad());
    }

    @Test
    public void toStringFormato() {
        assertEquals("Ana (30 años)", new Persona("Ana", 30).toString());
    }
}
