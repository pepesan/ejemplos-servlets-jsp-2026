package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contrato que debe cumplir cada método del controlador.
 * Devuelve:
 *   - "vista.jsp"          → el FrontController hace forward a WEB-INF/vistas/vista.jsp
 *   - "redirect:/app/..."  → el FrontController hace sendRedirect
 *   - null                 → la respuesta ya fue escrita directamente
 */
@FunctionalInterface
public interface Accion {
    String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
