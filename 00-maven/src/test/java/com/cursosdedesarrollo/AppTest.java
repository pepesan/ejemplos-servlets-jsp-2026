package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void saludarAdulto() {
        assertEquals("Hola, Ana", App.saludar(new Persona("Ana", 30)));
    }

    @Test
    public void saludarMenor() {
        assertEquals("Hola, Luis (menor de edad)", App.saludar(new Persona("Luis", 15)));
    }
}
