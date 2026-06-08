package com.cursosdedesarrollo;

import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas de aceptación de CicloVidaServlet contra el servidor real.
 * Se ejecutan con: mvn verify  (tomcat7 arranca en pre-integration-test)
 */
public class CicloVidaServletIT {

    private static final String BASE = "http://localhost:8012";

    @Test
    public void get_retorna200() throws Exception {
        HttpURLConnection conn = abrir("GET", "/ciclo-vida");

        assertEquals(200, conn.getResponseCode());

        conn.disconnect();
    }

    @Test
    public void get_contentTypeEsTextHtml() throws Exception {
        HttpURLConnection conn = abrir("GET", "/ciclo-vida");
        conn.getResponseCode();

        assertTrue(conn.getContentType().contains("text/html"));

        conn.disconnect();
    }

    @Test
    public void get_cuerpoMuestraInitParams() throws Exception {
        HttpURLConnection conn = abrir("GET", "/ciclo-vida");
        String body = leerCuerpo(conn);

        assertTrue(body.contains("Curso de Servlets 2026")); // init-param autor
        assertTrue(body.contains("1.0"));                    // init-param version

        conn.disconnect();
    }

    @Test
    public void get_cuerpoIncrementaElContador() throws Exception {
        // Primera petición — el contador debe ser al menos 1
        HttpURLConnection conn = abrir("GET", "/ciclo-vida");
        String body = leerCuerpo(conn);
        conn.disconnect();

        // El HTML incluye "Petición nº" + un número ≥ 1
        assertTrue(body.contains("Ciclo de Vida del Servlet"));
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private static HttpURLConnection abrir(String metodo, String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(BASE + path).openConnection();
        conn.setRequestMethod(metodo);
        conn.setConnectTimeout(5_000);
        conn.setReadTimeout(5_000);
        return conn;
    }

    private static String leerCuerpo(HttpURLConnection conn) throws Exception {
        try (InputStream is = conn.getInputStream();
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        }
    }
}
