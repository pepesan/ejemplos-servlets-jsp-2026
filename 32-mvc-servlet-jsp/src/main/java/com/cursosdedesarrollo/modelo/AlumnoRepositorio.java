package com.cursosdedesarrollo.modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Capa de datos (repositorio) de la entidad Alumno.
 *
 * En este ejemplo los datos se guardan en memoria: se pierden al reiniciar
 * el servidor. En una aplicación real esta clase accedería a una base de
 * datos (JDBC, Hibernate, JPA…).
 *
 * Se implementa como singleton para que todos los servlets compartan
 * la misma instancia durante el ciclo de vida de la aplicación.
 * En producción este rol lo desempeña el contexto de persistencia.
 *
 * Se usa LinkedHashMap para mantener el orden de inserción al listar.
 */
public class AlumnoRepositorio {

    private static final AlumnoRepositorio INSTANCIA = new AlumnoRepositorio();

    private final Map<Integer, Alumno> datos = new LinkedHashMap<>();
    private int siguienteId = 1;

    /** Constructor privado: garantiza una única instancia (patrón Singleton). */
    private AlumnoRepositorio() {
        crear(new Alumno("Ana García",    "ana@ejemplo.com",    8.5, true));
        crear(new Alumno("Luis Pérez",    "luis@ejemplo.com",   4.2, true));
        crear(new Alumno("Marta Ruiz",    "marta@ejemplo.com",  7.0, false));
        crear(new Alumno("Carlos López",  "carlos@ejemplo.com", 9.1, true));
        crear(new Alumno("Sara Martín",   "sara@ejemplo.com",   3.5, true));
    }

    public static AlumnoRepositorio instancia() {
        return INSTANCIA;
    }

    public List<Alumno> listar() {
        return new ArrayList<>(datos.values());
    }

    public Alumno buscarPorId(int id) {
        return datos.get(id);
    }

    public void crear(Alumno a) {
        a.setId(siguienteId++);
        datos.put(a.getId(), a);
    }

    public void actualizar(Alumno a) {
        datos.put(a.getId(), a);
    }

    public void eliminar(int id) {
        datos.remove(id);
    }
}
