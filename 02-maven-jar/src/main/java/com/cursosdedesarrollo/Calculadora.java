package com.cursosdedesarrollo;

import org.apache.commons.lang3.Validate;

/**
 * Biblioteca de operaciones aritméticas básicas.
 * Demuestra un JAR de librería: API pública, sin main().
 */
public class Calculadora {

    public int sumar(int a, int b) {
        return a + b;
    }

    public int restar(int a, int b) {
        return a - b;
    }

    public double dividir(int dividendo, int divisor) {
        Validate.isTrue(divisor != 0, "El divisor no puede ser cero");
        return (double) dividendo / divisor;
    }
}
