package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contrato que deben cumplir los métodos del controlador.
 *
 * Al ser {@code @FunctionalInterface}, permite registrar métodos del controller
 * directamente mediante referencias a métodos, sin crear clases anónimas:
 *
 * <pre>
 *   // Con referencia a método (Java 8+):
 *   getAcciones.put("/alumnos", controller::listar);
 *
 *   // Equivalente con lambda:
 *   getAcciones.put("/alumnos", (req, resp) -> controller.listar(req, resp));
 * </pre>
 *
 * El valor de retorno lo interpreta {@link FrontControllerServlet}:
 * <ul>
 *   <li>{@code "lista.jsp"}          → forward a {@code WEB-INF/vistas/lista.jsp}</li>
 *   <li>{@code "redirect:/app/..."}  → {@code sendRedirect} al path indicado (patrón PRG)</li>
 *   <li>{@code null}                 → la respuesta ya fue escrita por el controller</li>
 * </ul>
 */
@FunctionalInterface
public interface Accion {
    String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
