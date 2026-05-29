package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class TiposRespuestaServletTest {

    @Test
    public void construirJsonContieneModulo() {
        String json = TiposRespuestaServlet.construirJson("mi-modulo", 200, "OK");
        assertTrue(json.contains("\"mi-modulo\""));
    }

    @Test
    public void construirJsonContieneStatus() {
        String json = TiposRespuestaServlet.construirJson("m", 201, "creado");
        assertTrue(json.contains("201"));
    }

    @Test
    public void construirJsonContieneMensaje() {
        String json = TiposRespuestaServlet.construirJson("m", 200, "respuesta OK");
        assertTrue(json.contains("respuesta OK"));
    }

    @Test
    public void construirJsonEsJsonValido() {
        String json = TiposRespuestaServlet.construirJson("m", 200, "msg");
        assertTrue(json.trim().startsWith("{"));
        assertTrue(json.trim().endsWith("}"));
    }
}
