package com.cursosdedesarrollo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductoRepositorio {

    private static final List<Producto> datos = new ArrayList<>();
    private static int nextId = 1;

    static {
        guardar(new Producto("Teclado mecánico", 79.99));
        guardar(new Producto("Ratón inalámbrico", 34.50));
        guardar(new Producto("Monitor 24\"", 229.00));
        guardar(new Producto("Auriculares USB", 49.99));
        guardar(new Producto("Webcam HD", 65.00));
    }

    public static List<Producto> listarTodos() {
        return Collections.unmodifiableList(datos);
    }

    public static Producto buscarPorId(int id) {
        for (Producto p : datos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public static Producto guardar(Producto p) {
        p.setId(nextId++);
        datos.add(p);
        return p;
    }

    static void reset() {
        datos.clear();
        nextId = 1;
    }
}
