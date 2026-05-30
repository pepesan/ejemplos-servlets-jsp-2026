package com.cursosdedesarrollo;

import java.util.Arrays;
import java.util.List;

/**
 * Datos de demostración para la página de funciones JSTL fn:.
 * Separa la preparación de datos del código de presentación JSP.
 */
public class DemosFn {

    static final String TEXTO        = "  Hola, Mundo JSP!  ";
    static final String CSV          = "java,servlet,jsp,jstl,el";
    static final String HTML_INSEGURO = "<script>alert('xss')</script>";

    static List<String> palabrasCsv() {
        return Arrays.asList(CSV.split(","));
    }

    /** Une una lista de palabras con el separador dado. */
    static String unir(List<String> palabras, String sep) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < palabras.size(); i++) {
            if (i > 0) sb.append(sep);
            sb.append(palabras.get(i));
        }
        return sb.toString();
    }

    /** Cuenta las apariciones de sub en s (case-sensitive). */
    static int contar(String s, String sub) {
        if (s == null || sub == null || sub.isEmpty()) return 0;
        int count = 0, idx = 0;
        while ((idx = s.indexOf(sub, idx)) != -1) { count++; idx += sub.length(); }
        return count;
    }
}
