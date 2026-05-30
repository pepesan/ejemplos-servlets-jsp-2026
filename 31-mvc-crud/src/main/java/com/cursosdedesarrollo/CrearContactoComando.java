package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class CrearContactoComando implements Comando {

    @Override
    public String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("POST".equals(req.getMethod())) {
            return post(req, resp);
        }
        req.setAttribute("errores", Collections.emptyMap());
        req.setAttribute("categorias", ContactoValidator.CATEGORIAS);
        return "formulario.jsp";
    }

    private String post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String nombre    = req.getParameter("nombre");
        String email     = req.getParameter("email");
        String telefono  = req.getParameter("telefono");
        String categoria = req.getParameter("categoria");

        Map<String, String> errores = ContactoValidator.validar(nombre, email, telefono, categoria);

        if (!errores.isEmpty()) {
            req.setAttribute("nombre",    nombre);
            req.setAttribute("email",     email);
            req.setAttribute("telefono",  telefono);
            req.setAttribute("categoria", categoria);
            req.setAttribute("errores",   errores);
            req.setAttribute("categorias", ContactoValidator.CATEGORIAS);
            return "formulario.jsp";
        }

        ContactoRepositorio.guardar(new Contacto(
                nombre.trim(), email.trim(),
                telefono == null ? "" : telefono.trim(),
                categoria.trim().toUpperCase()));
        resp.sendRedirect(req.getContextPath() + "/app/contactos");
        return null;
    }
}
