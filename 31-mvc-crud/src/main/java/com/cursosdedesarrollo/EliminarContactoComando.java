package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EliminarContactoComando implements Comando {

    @Override
    public String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!"POST".equals(req.getMethod())) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Solo se permite POST");
            return null;
        }
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                ContactoRepositorio.eliminar(Integer.parseInt(idParam));
            } catch (NumberFormatException ignored) {
                // id inválido → simplemente redirige a la lista
            }
        }
        resp.sendRedirect(req.getContextPath() + "/app/contactos");
        return null;
    }
}
