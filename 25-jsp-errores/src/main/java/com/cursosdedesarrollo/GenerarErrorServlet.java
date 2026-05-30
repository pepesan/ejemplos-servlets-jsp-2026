package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Genera distintos tipos de error para demostrar los mecanismos
 * de manejo de errores configurados en web.xml.
 *
 * GET /generar?tipo=404       → resp.sendError(404)          → error-404.jsp
 * GET /generar?tipo=500       → resp.sendError(500)          → error-500.jsp
 * GET /generar?tipo=runtime   → throw RuntimeException       → error-500.jsp
 * GET /generar?tipo=npe       → throw NullPointerException   → error-500.jsp
 * GET /generar?tipo=aritm     → división por cero            → error-500.jsp
 * GET /generar (sin tipo)     → resp.sendError(400)          → error-400.jsp
 */
public class GenerarErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String tipo = req.getParameter("tipo");

        if (!tipoReconocido(tipo)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Parámetro 'tipo' requerido: 404 | 500 | runtime | npe | aritm");
            return;
        }

        switch (tipo) {
            case "404":
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Recurso de demostración no encontrado");
                break;
            case "500":
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error interno de demostración (sendError)");
                break;
            case "runtime":
                throw new RuntimeException("RuntimeException lanzada desde el servlet");
            case "npe":
                // Fuerza NullPointerException intencionalmente
                String s = null;
                s.length();   // NPE
                break;
            case "aritm":
                int x = 1 / 0;   // ArithmeticException: / by zero
                break;
        }
    }

    /** Devuelve true si el tipo de error está soportado. */
    static boolean tipoReconocido(String tipo) {
        return "404".equals(tipo)
                || "500".equals(tipo)
                || "runtime".equals(tipo)
                || "npe".equals(tipo)
                || "aritm".equals(tipo);
    }

    /** Texto descriptivo del tipo de error para mostrar en la UI. */
    static String descripcion(String tipo) {
        if (tipo == null) return "desconocido";
        switch (tipo) {
            case "404":     return "HTTP 404 — Not Found (sendError)";
            case "500":     return "HTTP 500 — Internal Server Error (sendError)";
            case "runtime": return "RuntimeException no capturada en el servlet";
            case "npe":     return "NullPointerException — acceso a referencia nula";
            case "aritm":   return "ArithmeticException — división por cero";
            default:        return tipo;
        }
    }
}
