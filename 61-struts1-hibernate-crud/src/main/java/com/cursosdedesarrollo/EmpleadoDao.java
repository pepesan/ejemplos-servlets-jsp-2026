package com.cursosdedesarrollo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class EmpleadoDao {

    public void guardar(Empleado e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(e);
        t.commit();
        s.close();
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
        List<Empleado> lista = s.createQuery("from Empleado order by id").list();
        s.close();
        return lista;
    }

    /**
     * Búsqueda por nombre o departamento (insensible a mayúsculas).
     * Usa Criteria API en lugar de HQL con lower() para evitar conflictos
     * de versiones de ANTLR entre Struts (2.7.2) e Hibernate (2.7.6).
     */
    @SuppressWarnings("unchecked")
    public List<Empleado> buscar(String texto) {
        String patron = "%" + texto + "%";
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Empleado> lista = s.createCriteria(Empleado.class)
                .add(Restrictions.or(
                        Restrictions.ilike("nombre",       patron),
                        Restrictions.ilike("departamento", patron)
                ))
                .addOrder(Order.asc("id"))
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
