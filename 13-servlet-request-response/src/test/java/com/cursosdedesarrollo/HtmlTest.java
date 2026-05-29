package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class HtmlTest {

    @Test
    public void escapeAmpersand() {
        assertEquals("a &amp; b", Html.esc("a & b"));
    }

    @Test
    public void escapeEtiquetas() {
        assertEquals("&lt;b&gt;negrita&lt;/b&gt;", Html.esc("<b>negrita</b>"));
    }

    @Test
    public void escapeComillasDobles() {
        assertEquals("&quot;hola&quot;", Html.esc("\"hola\""));
    }

    @Test
    public void textoLimpioSinCambios() {
        assertEquals("Hola mundo", Html.esc("Hola mundo"));
    }

    @Test
    public void nullDevuelveCadenaVacia() {
        assertEquals("", Html.esc(null));
    }
}
