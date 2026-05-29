package com.cursosdedesarrollo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    // Logger de SLF4J (scope compile): la interfaz está en slf4j-api.
    // La implementación concreta (logback-classic) se inyecta en runtime.
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static String formatearNombre(String nombre) {
        if (StringUtils.isBlank(nombre)) {
            return "Desconocido";
        }
        return StringUtils.capitalize(nombre.trim().toLowerCase());
    }

    public static boolean esNumero(String valor) {
        return NumberUtils.isParsable(valor);
    }

    public static void main(String[] args) {
        log.info("Inicio demo — scope runtime: logback proporciona esta implementación");

        // commons-lang3 (scope compile)
        System.out.println(formatearNombre("  MAVEN  "));
        System.out.println(formatearNombre(null));
        System.out.println("¿'42' es número?  " + esNumero("42"));
        System.out.println("¿'abc' es número? " + esNumero("abc"));

        log.info("Demo completada");
    }
}
