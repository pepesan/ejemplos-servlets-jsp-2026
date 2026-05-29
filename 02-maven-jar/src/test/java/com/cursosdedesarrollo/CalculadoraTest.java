package com.cursosdedesarrollo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculadoraTest {

    private Calculadora calc;

    @Before
    public void setUp() {
        calc = new Calculadora();
    }

    @Test
    public void sumar() {
        assertEquals(5, calc.sumar(2, 3));
    }

    @Test
    public void restar() {
        assertEquals(1, calc.restar(4, 3));
    }

    @Test
    public void dividir() {
        assertEquals(2.5, calc.dividir(5, 2), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dividirPorCero() {
        calc.dividir(10, 0);
    }
}
