package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EmpleadoDao {

    public Empleado guardar(Empleado e) {
        Session tx = HibernateUtil.getSessionFactory().openSession();
        Transaction t = tx.beginTransaction();
        tx.save(e);
        t.commit();
        tx.close();
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
