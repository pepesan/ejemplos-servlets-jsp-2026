package com.cursosdedesarrollo;

import org.junit.Test;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import static org.junit.Assert.*;

public class ContadorServletTest {

    @Test
    public void primeraLlamadaDevuelveUno() {
        assertEquals(1, ContadorServlet.incrementar(stubSesion(null)));
    }

    @Test
    public void llamadasSuccesivasIncrement() {
        HttpSession s = stubSesion(null);
        ContadorServlet.incrementar(s);
        ContadorServlet.incrementar(s);
        int tercera = ContadorServlet.incrementar(s);
        assertEquals(3, tercera);
    }

    @Test
    public void sesionConValorPrevioIncrementa() {
        assertEquals(6, ContadorServlet.incrementar(stubSesion(5)));
    }

    // ── Stub HttpSession ─────────────────────────────────────────────────────

    private static HttpSession stubSesion(final Integer valorInicial) {
        return new HttpSession() {
            private Object valor = valorInicial;

            @Override public Object getAttribute(String name) {
                return ContadorServlet.ATTR.equals(name) ? valor : null;
            }
            @Override public void setAttribute(String name, Object v) {
                if (ContadorServlet.ATTR.equals(name)) valor = v;
            }
            @Override public String getId() { return "test"; }
            @Override public long getCreationTime() { return 0; }
            @Override public long getLastAccessedTime() { return 0; }
            @Override public javax.servlet.ServletContext getServletContext() { return null; }
            @Override public void setMaxInactiveInterval(int i) {}
            @Override public int getMaxInactiveInterval() { return 0; }
            @Override public Enumeration<String> getAttributeNames() { return Collections.emptyEnumeration(); }
            @Override public void removeAttribute(String n) {}
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
