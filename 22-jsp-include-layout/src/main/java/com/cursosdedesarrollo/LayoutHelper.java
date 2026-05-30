package com.cursosdedesarrollo;

/**
 * Utilidades para la generación de layout.
 * Métodos estáticos invocables desde scriptlets JSP o directamente en tests.
 */
public class LayoutHelper {

    /**
     * Devuelve "activo" si la página actual coincide con el ítem de menú,
     * cadena vacía en caso contrario. Útil para marcar el enlace activo en la nav.
     */
    public static String activo(String paginaActual, String itemMenu) {
        if (paginaActual == null || itemMenu == null) return "";
        return paginaActual.equals(itemMenu) ? "activo" : "";
    }

    /**
     * Construye una cadena de migas de pan separada por " › ".
     * Ejemplo: migas("Inicio", "Demos", "Estático") → "Inicio › Demos › Estático"
     */
    public static String migas(String... paginas) {
        if (paginas == null || paginas.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paginas.length; i++) {
            if (i > 0) sb.append(" › ");
            sb.append(paginas[i]);
        }
        return sb.toString();
    }
}
