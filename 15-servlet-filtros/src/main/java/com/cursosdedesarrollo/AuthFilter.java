package com.cursosdedesarrollo;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Protege todas las rutas bajo /protegido/*.
 * Si el usuario no está autenticado (no hay atributo "usuario" en sesión),
 * redirige a /login. En caso contrario deja pasar la petición.
 *
 * Este patrón es la base de cualquier sistema de autorización clásico
 * en aplicaciones Java EE sin framework de seguridad.
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig config) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (necesitaAutenticacion(req.getRequestURI()) && !estaAutenticado(req.getSession(false))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    /** Determina si la URI está bajo la zona protegida. */
    static boolean necesitaAutenticacion(String uri) {
        return uri != null && uri.contains("/protegido");
    }

    /** Devuelve true si la sesión existe y tiene el atributo "usuario". */
    static boolean estaAutenticado(HttpSession sesion) {
        return sesion != null && sesion.getAttribute("usuario") != null;
    }
}
