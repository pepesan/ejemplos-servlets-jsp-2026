package com.cursosdedesarrollo;

import org.junit.Test;
import javax.servlet.http.Cookie;
import static org.junit.Assert.*;

public class TemaServletTest {

    @Test
    public void sinCookiesRetornaDefecto() {
        assertEquals(TemaServlet.TEMA_DEFECTO, TemaServlet.leerTema(null));
    }

    @Test
    public void cookiesVacioRetornaDefecto() {
        assertEquals(TemaServlet.TEMA_DEFECTO, TemaServlet.leerTema(new Cookie[0]));
    }

    @Test
    public void cookieTemaOscuroEsLeida() {
        Cookie c = new Cookie(TemaServlet.NOMBRE_COOKIE, "oscuro");
        assertEquals("oscuro", TemaServlet.leerTema(new Cookie[]{c}));
    }

    @Test
    public void cookieTemaClaroEsLeida() {
        Cookie c = new Cookie(TemaServlet.NOMBRE_COOKIE, "claro");
        assertEquals("claro", TemaServlet.leerTema(new Cookie[]{c}));
    }

    @Test
    public void cookieTemaInvalidoRetornaDefecto() {
        Cookie c = new Cookie(TemaServlet.NOMBRE_COOKIE, "morado");
        assertEquals(TemaServlet.TEMA_DEFECTO, TemaServlet.leerTema(new Cookie[]{c}));
    }

    @Test
    public void otrasCookiesNoAfectan() {
        Cookie otra  = new Cookie("otra", "cosa");
        Cookie tema  = new Cookie(TemaServlet.NOMBRE_COOKIE, "claro");
        assertEquals("claro", TemaServlet.leerTema(new Cookie[]{otra, tema}));
    }

    @Test
    public void temasValidosReconocidos() {
        assertTrue(TemaServlet.esValido("oscuro"));
        assertTrue(TemaServlet.esValido("claro"));
        assertTrue(TemaServlet.esValido("sistema"));
    }

    @Test
    public void temasInvalidosRechazados() {
        assertFalse(TemaServlet.esValido(null));
        assertFalse(TemaServlet.esValido(""));
        assertFalse(TemaServlet.esValido("azul"));
    }
}
