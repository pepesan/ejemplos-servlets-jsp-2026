package com.cursosdedesarrollo;

import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;

import static org.junit.Assert.*;

public class AuthFilterTest {

    // ── necesitaAutenticacion ─────────────────────────────────────────────────

    @Test
    public void rutaProtegidaNecesitaAuth() {
        assertTrue(AuthFilter.necesitaAutenticacion("/protegido/area"));
    }

    @Test
    public void subrutaProtegidaNecesitaAuth() {
        assertTrue(AuthFilter.necesitaAutenticacion("/protegido/otra-pagina"));
    }

    @Test
    public void loginNoNecesitaAuth() {
        assertFalse(AuthFilter.necesitaAutenticacion("/login"));
    }

    @Test
    public void raizNoNecesitaAuth() {
        assertFalse(AuthFilter.necesitaAutenticacion("/"));
    }

    @Test
    public void indexNoNecesitaAuth() {
        assertFalse(AuthFilter.necesitaAutenticacion("/index.html"));
    }

    @Test
    public void uriNullNoNecesitaAuth() {
        assertFalse(AuthFilter.necesitaAutenticacion(null));
    }

    // ── estaAutenticado ───────────────────────────────────────────────────────

    @Test
    public void sesionNullNoEstaAutenticado() {
        assertFalse(AuthFilter.estaAutenticado(null));
    }

    @Test
    public void sesionSinUsuarioNoEstaAutenticado() {
        assertFalse(AuthFilter.estaAutenticado(stubSesion(null)));
    }

    @Test
    public void sesionConUsuarioEstaAutenticado() {
        assertTrue(AuthFilter.estaAutenticado(stubSesion("admin")));
    }

    // ── Stub mínimo de HttpSession ────────────────────────────────────────────

    private static HttpSession stubSesion(final String usuario) {
        return new HttpSession() {
            @Override public Object getAttribute(String name) {
                return "usuario".equals(name) ? usuario : null;
            }
            @Override public long getCreationTime() { return 0; }
            @Override public String getId() { return "test-id"; }
            @Override public long getLastAccessedTime() { return 0; }
            @Override public javax.servlet.ServletContext getServletContext() { return null; }
            @Override public void setMaxInactiveInterval(int interval) {}
            @Override public int getMaxInactiveInterval() { return 0; }
            @Override public Enumeration<String> getAttributeNames() { return Collections.emptyEnumeration(); }
            @Override public void setAttribute(String name, Object value) {}
            @Override public void removeAttribute(String name) {}
            @Override public void invalidate() {}
            @Override public boolean isNew() { return false; }
            /** @deprecated */ @Override public javax.servlet.http.HttpSessionContext getSessionContext() { return null; }
            /** @deprecated */ @Override public Object getValue(String name) { return null; }
            /** @deprecated */ @Override public String[] getValueNames() { return new String[0]; }
            /** @deprecated */ @Override public void putValue(String name, Object value) {}
            /** @deprecated */ @Override public void removeValue(String name) {}
        };
    }
}
