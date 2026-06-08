package com.cursosdedesarrollo;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas de aceptación de FormularioServlet contra el servidor real.
 * Se ejecutan con: mvn verify  (tomcat7 arranca en pre-integration-test)
 */
public class FormularioServletIT {

    private static final String BASE = "http://localhost:8012";

    @Test
    public void get_formulario_retorna200ConHtml() throws Exception {
        HttpURLConnection conn = abrir("GET", "/formulario");

        assertEquals(200, conn.getResponseCode());
        assertTrue(conn.getContentType().contains("text/html"));

        conn.disconnect();
    }

    @Test
    public void get_formulario_cuerpoContieneFormulario() throws Exception {
        HttpURLConnection conn = abrir("GET", "/formulario");
        String body = leerCuerpo(conn);

        assertTrue(body.contains("Formulario de contacto"));
        assertTrue(body.contains("<form"));

        conn.disconnect();
    }

    @Test
    public void post_datosValidos_muestraResumenConNombre() throws Exception {
        HttpURLConnection conn = abrir("POST", "/formulario");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write("nombre=Ana+Garcia&email=ana%40test.com&mensaje=Hola+mundo"
                    .getBytes(StandardCharsets.UTF_8));
        }

        assertEquals(200, conn.getResponseCode());
        String body = leerCuerpo(conn);
        assertTrue(body.contains("Ana Garcia"));
        assertTrue(body.contains("ana@test.com"));

        conn.disconnect();
    }

    @Test
    public void post_camposVacios_redirigeCon302() throws Exception {
        HttpURLConnection conn = abrir("POST", "/formulario");
        conn.setInstanceFollowRedirects(false);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write("nombre=&email=&mensaje=".getBytes(StandardCharsets.UTF_8));
        }

        assertEquals(302, conn.getResponseCode());
        assertTrue(conn.getHeaderField("Location").contains("error=vacio"));

        conn.disconnect();
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
