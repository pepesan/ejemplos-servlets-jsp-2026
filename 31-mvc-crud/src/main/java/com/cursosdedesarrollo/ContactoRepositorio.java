package com.cursosdedesarrollo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactoRepositorio {

    private static final List<Contacto> datos = new ArrayList<>();
    private static int nextId = 1;

    static {
        guardar(new Contacto("Ana García",    "ana@gmail.com",    "600111222", Contacto.AMIGO));
        guardar(new Contacto("Carlos López",  "carlos@empresa.es","912345678", Contacto.TRABAJO));
        guardar(new Contacto("María Martín",  "maria@gmail.com",  "655999000", Contacto.FAMILIA));
        guardar(new Contacto("Pedro Sánchez", "pedro@empresa.es", "611223344", Contacto.TRABAJO));
        guardar(new Contacto("Lucía Fernández","lucia@gmail.com", "699887766", Contacto.AMIGO));
    }

    public static List<Contacto> listarTodos() {
        return Collections.unmodifiableList(datos);
    }

    public static List<Contacto> filtrarPorCategoria(String categoria) {
        List<Contacto> resultado = new ArrayList<>();
        for (Contacto c : datos) {
            if (categoria.equalsIgnoreCase(c.getCategoria())) {
                resultado.add(c);
            }
        }
        return Collections.unmodifiableList(resultado);
    }

    public static Contacto buscarPorId(int id) {
        for (Contacto c : datos) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public static Contacto guardar(Contacto c) {
        c.setId(nextId++);
        datos.add(c);
        return c;
    }

    public static boolean actualizar(Contacto actualizado) {
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).getId() == actualizado.getId()) {
                datos.set(i, actualizado);
                return true;
            }
        }
        return false;
    }

    public static boolean eliminar(int id) {
        return datos.removeIf(c -> c.getId() == id);
    }

    static void reset() {
        datos.clear();
        nextId = 1;
    }
}
