package com.cursosdedesarrollo;

import org.junit.Test;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

public class CarritoServletTest {

    @Test
    public void carritoNuevoEstaVacio() {
        HttpSession s = stubSesion();
        List<String> carrito = CarritoServlet.obtenerCarrito(s);
        assertTrue(carrito.isEmpty());
    }

    @Test
    public void agregarAumentaTamano() {
        HttpSession s = stubSesion();
        CarritoServlet.agregar(s, "Teclado");
        CarritoServlet.agregar(s, "Ratón");
        assertEquals(2, CarritoServlet.tamano(s));
    }

    @Test
    public void agregarConservaOrden() {
        HttpSession s = stubSesion();
        CarritoServlet.agregar(s, "Primero");
        CarritoServlet.agregar(s, "Segundo");
        List<String> carrito = CarritoServlet.obtenerCarrito(s);
        assertEquals("Primero", carrito.get(0));
        assertEquals("Segundo", carrito.get(1));
    }

    @Test
    public void obtenerCarritoMismaInstancia() {
        HttpSession s = stubSesion();
        List<String> primera  = CarritoServlet.obtenerCarrito(s);
        List<String> segunda  = CarritoServlet.obtenerCarrito(s);
        assertSame(primera, segunda);
    }

    @Test
    public void catalogoNoEstaVacio() {
        assertFalse(CarritoServlet.CATALOGO.isEmpty());
    }

    // ── Stub HttpSession con Map interno ─────────────────────────────────────

    private static HttpSession stubSesion() {
        return new HttpSession() {
            private final Map<String, Object> attrs = new HashMap<>();

            @Override public Object getAttribute(String n) { return attrs.get(n); }
            @Override public void setAttribute(String n, Object v) { attrs.put(n, v); }
            @Override public void removeAttribute(String n) { attrs.remove(n); }
            @Override public Enumeration<String> getAttributeNames() {
                return Collections.enumeration(attrs.keySet());
            }
            @Override public String getId() { return "test"; }
            @Override public long getCreationTime() { return 0; }
            @Override public long getLastAccessedTime() { return 0; }
            @Override public javax.servlet.ServletContext getServletContext() { return null; }
            @Override public void setMaxInactiveInterval(int i) {}
            @Override public int getMaxInactiveInterval() { return 0; }
            @Override public void invalidate() {}
            @Override public boolean isNew() { return false; }
            @Override public javax.servlet.http.HttpSessionContext getSessionContext() { return null; }
            @Override public Object getValue(String n) { return null; }
            @Override public String[] getValueNames() { return new String[0]; }
            @Override public void putValue(String n, Object v) {}
            @Override public void removeValue(String n) {}
        };
    }
}
