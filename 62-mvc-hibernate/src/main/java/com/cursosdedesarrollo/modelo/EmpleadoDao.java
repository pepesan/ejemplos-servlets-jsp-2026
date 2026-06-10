package com.cursosdedesarrollo.modelo;

import com.cursosdedesarrollo.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Objeto de Acceso a Datos (DAO) para la entidad {@link Empleado}.
 *
 * Encapsula todas las operaciones Hibernate para que el controller
 * no sepa nada de sesiones, transacciones ni SQL.
 *
 * Patrón de sesión:
 *   openSession() → operación → [commit] → close()
 * Cada método abre y cierra su propia sesión (sesión por operación).
 * En producción se usaría "sesión por petición HTTP" (Open Session in View).
 */
public class EmpleadoDao {

    // ── Escritura ──────────────────────────────────────────────────────────

    public void guardar(Empleado e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(e);          // INSERT; Hibernate asigna el id generado al objeto
        t.commit();
        s.close();
    }

    public void actualizar(Empleado e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.update(e);        // UPDATE por id
        t.commit();
        s.close();
    }

    public void eliminar(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        Empleado e = (Empleado) s.get(Empleado.class, id);
        if (e != null) s.delete(e);
        t.commit();
        s.close();
    }

    // ── Lectura ────────────────────────────────────────────────────────────

    public Empleado buscarPorId(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        // s.get() devuelve null si no existe; s.load() lanzaría excepción
        Empleado e = (Empleado) s.get(Empleado.class, id);
        s.close();
        return e;
    }

    @SuppressWarnings("unchecked")
    public List<Empleado> listarTodos() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        // HQL: "from Empleado" es equivalente a "SELECT * FROM empleados"
        List<Empleado> lista = s.createQuery("from Empleado order by id").list();
        s.close();
        return lista;
    }

    /**
     * Búsqueda por nombre o departamento (insensible a mayúsculas).
     * Usa HQL con lower() — funciona porque este módulo no incluye Struts,
     * por lo que no hay conflicto de versiones de ANTLR.
     */
    @SuppressWarnings("unchecked")
    public List<Empleado> buscar(String texto) {
        String patron = "%" + texto.toLowerCase() + "%";
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Empleado> lista = s.createQuery(
                "from Empleado e " +
                "where lower(e.nombre) like :p or lower(e.departamento) like :p " +
                "order by e.id")
                .setParameter("p", patron)
                .list();
        s.close();
        return lista;
    }
}
