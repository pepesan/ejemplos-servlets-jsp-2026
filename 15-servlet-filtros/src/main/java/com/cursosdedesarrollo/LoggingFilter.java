package com.cursosdedesarrollo;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Registra en stdout el método HTTP, la URI y el tiempo de respuesta
 * de cada petición que pasa por el filtro.
 *
 * Se ejecuta en todos los recursos (/*) y envuelve la llamada a la cadena:
 *   1. Captura la hora de inicio.
 *   2. Deja pasar la petición (chain.doFilter).
 *   3. Calcula el tiempo transcurrido y lo escribe en el log.
 */
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
        System.out.println("[LoggingFilter] inicializado");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String metodo = req.getMethod();
        String uri    = req.getRequestURI();
        long inicio   = System.currentTimeMillis();

        try {
            chain.doFilter(request, response);
        } finally {
            long ms = System.currentTimeMillis() - inicio;
            System.out.printf("[LoggingFilter] %s %s → %d ms%n", metodo, uri, ms);
        }
    }

    @Override
    public void destroy() {
        System.out.println("[LoggingFilter] destruido");
    }
}
