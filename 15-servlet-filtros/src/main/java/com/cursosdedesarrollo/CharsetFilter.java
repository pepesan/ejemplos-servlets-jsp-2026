package com.cursosdedesarrollo;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Fuerza la codificación UTF-8 tanto en la petición como en la respuesta
 * antes de que ningún servlet lea parámetros o escriba al cliente.
 *
 * Patrón clásico previo a Servlet 4.0 (donde se puede configurar con
 * <request-character-encoding> en web.xml). Con Servlet 3.x es la forma
 * habitual de garantizar UTF-8 globalmente.
 *
 * Importa aplicarlo ANTES de que cualquier otro código llame a
 * getParameter() o getReader(), porque esos métodos fijan el charset en
 * el primer acceso.
 */
public class CharsetFilter implements Filter {

    private static final String UTF8 = "UTF-8";

    @Override
    public void init(FilterConfig config) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(UTF8);
        }
        response.setCharacterEncoding(UTF8);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
