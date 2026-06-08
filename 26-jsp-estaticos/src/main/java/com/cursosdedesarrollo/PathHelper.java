package com.cursosdedesarrollo;

/**
 * Utilidades para construir rutas a recursos estáticos.
 * Refleja lo que hace ${pageContext.request.contextPath} en EL.
 */
public class PathHelper {

    /**
     * Construye la ruta absoluta a un recurso estático.
     * Equivale a: ${pageContext.request.contextPath} + "/" + recurso
     *
     * @param contextPath resultado de request.getContextPath() (puede ser "" o "/miapp")
     * @param recurso     ruta del recurso, con o sin / inicial (ej. "css/estilos.css")
     */
    public static String estatico(String contextPath, String recurso) {
        String ctx = (contextPath == null) ? "" : contextPath;
        String res = (recurso.startsWith("/")) ? recurso : "/" + recurso;
        return ctx + res;
    }

    /** Devuelve true si la ruta apunta a WEB-INF (inaccesible desde el navegador). */
    public static boolean esWebInf(String uri) {
        return uri != null && uri.contains("/WEB-INF/");
    }
}
