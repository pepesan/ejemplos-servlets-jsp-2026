package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class FormularioServletTest {

    private final FormularioServlet servlet = new FormularioServlet();

    @Test
    public void escaparHtmlReemplazaAmpersand() {
        assertEquals("a &amp; b", FormularioServlet.escaparHtml("a & b"));
    }

    @Test
    public void escaparHtmlReemplazaEtiquetas() {
        assertEquals("&lt;script&gt;", FormularioServlet.escaparHtml("<script>"));
    }

    @Test
    public void escaparHtmlReemplazaComillas() {
        assertEquals("&quot;hola&quot;", FormularioServlet.escaparHtml("\"hola\""));
    }

    @Test
    public void escaparHtmlTextoLimpioPasaSinCambios() {
        assertEquals("Hola mundo", FormularioServlet.escaparHtml("Hola mundo"));
    }

    @Test
    public void escaparHtmlCadenaVacia() {
        assertEquals("", FormularioServlet.escaparHtml(""));
    }
}
