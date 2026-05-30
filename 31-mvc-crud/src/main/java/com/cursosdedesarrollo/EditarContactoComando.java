package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class EditarContactoComando implements Comando {

    @Override
    public String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("POST".equals(req.getMethod())) {
            return post(req, resp);
        }
        return get(req, resp);
    }

    private String get(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        Contacto contacto = ContactoRepositorio.buscarPorId(id);
        if (contacto == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Contacto con id=" + id + " no encontrado");
            return null;
        }
        req.setAttribute("contacto",   contacto);
        req.setAttribute("errores",    java.util.Collections.emptyMap());
        req.setAttribute("categorias", ContactoValidator.CATEGORIAS);
        return "formulario.jsp";
    }

    private String post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String idParam   = req.getParameter("id");
        String nombre    = req.getParameter("nombre");
        String email     = req.getParameter("email");
        String telefono  = req.getParameter("telefono");
        String categoria = req.getParameter("categoria");

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro id debe ser numérico");
            return null;
        }

        Map<String, String> errores = ContactoValidator.validar(nombre, email, telefono, categoria);

        if (!errores.isEmpty()) {
            Contacto contacto = ContactoRepositorio.buscarPorId(id);
            req.setAttribute("contacto",   contacto);
            req.setAttribute("nombre",     nombre);
            req.setAttribute("email",      email);
            req.setAttribute("telefono",   telefono);
            req.setAttribute("categoria",  categoria);
            req.setAttribute("errores",    errores);
            req.setAttribute("categorias", ContactoValidator.CATEGORIAS);
            return "formulario.jsp";
        }

        Contacto actualizado = new Contacto(
                nombre.trim(), email.trim(),
                telefono == null ? "" : telefono.trim(),
                categoria.trim().toUpperCase());
        actualizado.setId(id);
        ContactoRepositorio.actualizar(actualizado);
        resp.sendRedirect(req.getContextPath() + "/app/contactos");
        return null;
    }
}
