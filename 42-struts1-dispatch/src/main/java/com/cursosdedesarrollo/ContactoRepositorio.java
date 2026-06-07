package com.cursosdedesarrollo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactoRepositorio {

    private static final Map<Integer, Contacto> datos = new LinkedHashMap<>();
    private static final AtomicInteger contador = new AtomicInteger(1);

    static {
        guardar(new Contacto(0, "Ana García",  "ana@ejemplo.com",   "600 111 222"));
        guardar(new Contacto(0, "Luis Pérez",  "luis@ejemplo.com",  "600 333 444"));
        guardar(new Contacto(0, "María López", "maria@ejemplo.com", "600 555 666"));
    }

    public static List<Contacto> listar() {
        return new ArrayList<>(datos.values());
    }

    public static Contacto buscarPorId(int id) {
        return datos.get(id);
    }

    public static void guardar(Contacto c) {
        if (c.getId() == 0) {
            c.setId(contador.getAndIncrement());
        }
        datos.put(c.getId(), c);
    }

    public static void eliminar(int id) {
        datos.remove(id);
    }

    public static void limpiar() {
        datos.clear();
        contador.set(1);
    }
}
