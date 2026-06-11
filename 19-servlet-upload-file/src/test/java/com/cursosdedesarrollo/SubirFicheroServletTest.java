package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class SubirFicheroServletTest {

    @Test
    public void extraeNombreConComillas() {
        String cd = "form-data; name=\"archivo\"; filename=\"foto.jpg\"";
        assertEquals("foto.jpg", SubirFicheroServlet.extraerNombreFichero(cd));
    }

    @Test
    public void extraeNombreSinComillas() {
        String cd = "form-data; name=\"archivo\"; filename=imagen.png";
        assertEquals("imagen.png", SubirFicheroServlet.extraerNombreFichero(cd));
    }

    @Test
    public void extraeNombreConEspacios() {
        String cd = "form-data; name=\"archivo\"; filename=\"mi foto.jpg\"";
        assertEquals("mi foto.jpg", SubirFicheroServlet.extraerNombreFichero(cd));
    }

    @Test
    public void retornaVacioSiNoHayFilename() {
        String cd = "form-data; name=\"archivo\"";
        assertEquals("", SubirFicheroServlet.extraerNombreFichero(cd));
    }

    @Test
    public void retornaVacioConNull() {
        assertEquals("", SubirFicheroServlet.extraerNombreFichero(null));
    }

    @Test
    public void retornaVacioConCadenaVacia() {
        assertEquals("", SubirFicheroServlet.extraerNombreFichero(""));
    }

    @Test
    public void formataTamanioBytes() {
        assertEquals("512 B", SubirFicheroServlet.formatearTam(512));
    }

    @Test
    public void formataTamanioEnKb() {
        String r = SubirFicheroServlet.formatearTam(2048);
        assertTrue("debería contener KB", r.contains("KB"));
        assertTrue("debería ser 2.0 KB", r.startsWith("2.0"));
    }

    @Test
    public void formataTamanioEnMb() {
        String r = SubirFicheroServlet.formatearTam(2 * 1024 * 1024);
        assertTrue("debería contener MB", r.contains("MB"));
        assertTrue("debería ser 2.00 MB", r.startsWith("2.00"));
    }
}
