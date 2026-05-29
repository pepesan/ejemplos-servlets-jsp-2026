package com.cursosdedesarrollo;

import org.apache.commons.lang3.StringUtils;

/**
 * Ejecutable que consume la biblioteca Calculadora.
 * Demuestra la diferencia entre JAR estándar, fat-JAR y sources JAR.
 */
public class App {

    public static void main(String[] args) {
        Calculadora calc = new Calculadora();

        String separador = StringUtils.repeat("─", 36);
        System.out.println(separador);
        System.out.println("  Demo biblioteca Calculadora");
        System.out.println(separador);
        System.out.printf("  2 + 3   = %s%n", calc.sumar(2, 3));
        System.out.printf("  10 - 4  = %s%n", calc.restar(10, 4));
        System.out.printf("  7 / 2   = %.2f%n", calc.dividir(7, 2));
        System.out.println(separador);
    }
}
