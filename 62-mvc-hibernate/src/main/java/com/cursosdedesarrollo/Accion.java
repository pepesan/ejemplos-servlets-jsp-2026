package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interfaz funcional que representa una acción del controlador.
 *
 * Al ser {@code @FunctionalInterface}, permite registrar métodos del controller
 * mediante referencias a métodos:
 * <pre>
 *   getAcciones.put("/empleados", controller::listar);
 * </pre>
 *
 * Valor de retorno interpretado por {@link FrontControllerServlet}:
 * <ul>
 *   <li>{@code "lista.jsp"}              → forward a {@code WEB-INF/vistas/lista.jsp}</li>
 *   <li>{@code "redirect:/app/empleados"} → {@code sendRedirect} (patrón PRG)</li>
 *   <li>{@code null}                      → respuesta ya escrita por el controller</li>
 * </ul>
 */
@FunctionalInterface
public interface Accion {
    String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
