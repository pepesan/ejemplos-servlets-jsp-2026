package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/** Idéntico al DAO del módulo 50. La diferencia está en el mapeo, no en el acceso. */
public class EmpleadoDao {

    public Empleado guardar(Empleado e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(e);
        t.commit();
        s.close();
        return e;
    }

    public Empleado buscarPorId(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Empleado e = (Empleado) s.get(Empleado.class, id);
        s.close();
        return e;
    }

    @SuppressWarnings("unchecked")
    public List<Empleado> listarTodos() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Empleado> lista = s.createQuery("from Empleado").list();
        s.close();
        return lista;
    }

    @SuppressWarnings("unchecked")
    public List<Empleado> buscarPorDepartamento(String departamento) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Empleado> lista = s.createQuery("from Empleado where departamento = :dep")
                .setString("dep", departamento)
                .list();
        s.close();
        return lista;
    }

    public void actualizar(Empleado e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.update(e);
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
}
