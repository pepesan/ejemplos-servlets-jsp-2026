package com.cursosdedesarrollo;

import com.cursosdedesarrollo.modelo.Alumno;
import com.cursosdedesarrollo.modelo.AlumnoRepositorio;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlumnoRepositorioTest {

    private final AlumnoRepositorio repo = AlumnoRepositorio.instancia();

    @Test
    public void listarDevuelveAlumnosIniciales() {
        assertTrue(repo.listar().size() >= 5);
    }

    @Test
    public void buscarPorIdExistenteDevuelveAlumno() {
        Alumno a = repo.buscarPorId(1);
        assertNotNull(a);
        assertEquals(1, a.getId());
    }

    @Test
    public void buscarPorIdInexistenteDevuelveNull() {
        assertNull(repo.buscarPorId(9999));
    }

    @Test
    public void crearAsignaIdYAgregaAlumno() {
        int antesTotal = repo.listar().size();
        Alumno nuevo = new Alumno("Nuevo", "n@n.com", 6.0, true);
        repo.crear(nuevo);

        assertTrue(nuevo.getId() > 0);
        assertEquals(antesTotal + 1, repo.listar().size());
        assertNotNull(repo.buscarPorId(nuevo.getId()));
    }

    @Test
    public void actualizarModificaDatos() {
        Alumno a = repo.buscarPorId(1);
        String nombreOriginal = a.getNombre();
        a.setNombre("Modificado");
        repo.actualizar(a);

        assertEquals("Modificado", repo.buscarPorId(1).getNombre());
        a.setNombre(nombreOriginal);
        repo.actualizar(a);
    }

    @Test
    public void eliminarBorraElAlumno() {
        Alumno nuevo = new Alumno("ABorrar", "ab@ab.com", 5.0, true);
        repo.crear(nuevo);
        int id = nuevo.getId();

        repo.eliminar(id);

        assertNull(repo.buscarPorId(id));
    }
}
