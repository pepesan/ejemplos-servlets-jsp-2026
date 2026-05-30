package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListarProductosComando implements Comando {

    @Override
    public String ejecutar(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("productos", ProductoRepositorio.listarTodos());
        return "lista.jsp";
    }
}
