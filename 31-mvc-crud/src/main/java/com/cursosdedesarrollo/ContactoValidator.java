package com.cursosdedesarrollo;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContactoValidator {

    static final List<String> CATEGORIAS = Arrays.asList(
            Contacto.AMIGO, Contacto.TRABAJO, Contacto.FAMILIA);

    static Map<String, String> validar(String nombre, String email,
                                       String telefono, String categoria) {
        Map<String, String> errores = new LinkedHashMap<>();
        String e;
        if ((e = validarNombre(nombre))       != null) errores.put("nombre",    e);
        if ((e = validarEmail(email))         != null) errores.put("email",     e);
        if ((e = validarTelefono(telefono))   != null) errores.put("telefono",  e);
        if ((e = validarCategoria(categoria)) != null) errores.put("categoria", e);
        return errores;
    }

    static String validarNombre(String v) {
        if (v == null || v.trim().isEmpty()) return "El nombre es obligatorio.";
        if (v.trim().length() < 2)           return "El nombre debe tener al menos 2 caracteres.";
        return null;
    }

    static String validarEmail(String v) {
        if (v == null || v.trim().isEmpty()) return "El email es obligatorio.";
        if (!v.contains("@"))               return "El email debe contener @.";
        return null;
    }

    static String validarTelefono(String v) {
        if (v == null || v.trim().isEmpty()) return null; // campo opcional
        if (!v.trim().matches("[\\d\\s\\-+()]{7,15}"))
            return "El teléfono solo puede contener dígitos, espacios y guiones (7-15 caracteres).";
        return null;
    }

    static String validarCategoria(String v) {
        if (v == null || v.trim().isEmpty()) return "La categoría es obligatoria.";
        if (!CATEGORIAS.contains(v.trim().toUpperCase()))
            return "La categoría debe ser AMIGO, TRABAJO o FAMILIA.";
        return null;
    }
}
