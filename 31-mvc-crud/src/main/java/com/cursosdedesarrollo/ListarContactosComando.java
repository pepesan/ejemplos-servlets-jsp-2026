package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListarContactosComando implements Comando {

    @Override
    public String ejecutar(HttpServletRequest req, HttpServletResponse resp) {
        String categoria = req.getParameter("categoria");
        List<Contacto> contactos;

        if (categoria != null && !categoria.trim().isEmpty()) {
            contactos = ContactoRepositorio.filtrarPorCategoria(categoria.trim());
            req.setAttribute("categoriaActiva", categoria.trim().toUpperCase());
        } else {
            contactos = ContactoRepositorio.listarTodos();
        }

        req.setAttribute("contactos", contactos);
        req.setAttribute("categorias", ContactoValidator.CATEGORIAS);
        return "lista.jsp";
    }
}
