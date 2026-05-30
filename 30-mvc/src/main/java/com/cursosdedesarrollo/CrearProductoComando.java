package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CrearProductoComando implements Comando {

    @Override
    public String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("POST".equals(req.getMethod())) {
            return post(req, resp);
        }
        req.setAttribute("errores", new LinkedHashMap<String, String>());
        return "formulario.jsp";
    }

    private String post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String nombre    = req.getParameter("nombre");
        String precioStr = req.getParameter("precio");

        Map<String, String> errores = new LinkedHashMap<>();
        String e;
        if ((e = validarNombre(nombre))    != null) errores.put("nombre", e);
        if ((e = validarPrecio(precioStr)) != null) errores.put("precio", e);

        if (!errores.isEmpty()) {
            req.setAttribute("nombre",  nombre);
            req.setAttribute("precio",  precioStr);
            req.setAttribute("errores", errores);
            return "formulario.jsp";
        }

        ProductoRepositorio.guardar(new Producto(nombre.trim(), Double.parseDouble(precioStr.trim())));
        resp.sendRedirect(req.getContextPath() + "/app/productos");
        return null;
    }

    static String validarNombre(String v) {
        if (v == null || v.trim().isEmpty()) return "El nombre es obligatorio.";
        if (v.trim().length() < 2)           return "El nombre debe tener al menos 2 caracteres.";
        return null;
    }

    static String validarPrecio(String v) {
        if (v == null || v.trim().isEmpty()) return "El precio es obligatorio.";
        try {
            double precio = Double.parseDouble(v.trim());
            if (precio <= 0) return "El precio debe ser mayor que 0.";
        } catch (NumberFormatException ex) {
            return "El precio debe ser un número válido (ej: 19.99).";
        }
        return null;
    }
}
