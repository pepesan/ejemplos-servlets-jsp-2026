package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerProductoComando implements Comando {

    @Override
    public String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro id");
            return null;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro id debe ser numérico");
            return null;
        }
        Producto producto = ProductoRepositorio.buscarPorId(id);
        if (producto == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto con id=" + id + " no encontrado");
            return null;
        }
        req.setAttribute("producto", producto);
        return "detalle.jsp";
    }
}
