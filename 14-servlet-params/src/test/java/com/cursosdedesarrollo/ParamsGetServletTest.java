package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParamsGetServletTest {

    @Test
    public void formatearValoresUnSoloValor() {
        assertEquals("rojo", ParamsGetServlet.formatearValores(new String[]{"rojo"}));
    }

    @Test
    public void formatearValoresMultiplesValores() {
        assertEquals("rojo, verde, azul",
                ParamsGetServlet.formatearValores(new String[]{"rojo", "verde", "azul"}));
    }

    @Test
    public void formatearValoresArrayVacio() {
        assertEquals("", ParamsGetServlet.formatearValores(new String[]{}));
    }

    @Test
    public void formatearValoresNull() {
        assertEquals("", ParamsGetServlet.formatearValores(null));
    }

    @Test
    public void formatearValoresDosValores() {
        String resultado = ParamsGetServlet.formatearValores(new String[]{"a", "b"});
        assertEquals("a, b", resultado);
    }
}
