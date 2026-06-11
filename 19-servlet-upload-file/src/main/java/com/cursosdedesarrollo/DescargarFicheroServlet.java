package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Sirve un fichero previamente subido desde el directorio temporal del servidor.
 *
 * GET /descargar?nombre=foto.jpg           → Content-Disposition: attachment (descarga)
 * GET /descargar?nombre=foto.jpg&ver=true  → Content-Disposition: inline   (ver en navegador)
 */
@WebServlet("/descargar")
public class DescargarFicheroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String nombreParam = req.getParameter("nombre");
        if (nombreParam == null || nombreParam.trim().isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'nombre' requerido");
            return;
        }

        // Mismo saneamiento que en SubirFicheroServlet
        String nombreSeguro = nombreParam.replaceAll("[^a-zA-Z0-9._-]", "_");

        File tmpDir  = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        File fichero = new File(tmpDir, nombreSeguro);

        // Comprobación anti path-traversal: el fichero debe estar dentro de tmpDir
        if (!fichero.getCanonicalPath().startsWith(tmpDir.getCanonicalPath() + File.separator)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (!fichero.exists() || !fichero.isFile()) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND,
                "Fichero no encontrado: " + nombreSeguro);
            return;
        }

        String mime = getServletContext().getMimeType(nombreSeguro);
        if (mime == null) mime = "application/octet-stream";

        boolean inline = "true".equalsIgnoreCase(req.getParameter("ver"));
        String disposicion = inline ? "inline" : "attachment";

        res.setContentType(mime);
        res.setContentLength((int) fichero.length()); // máximo 2 MB → int es suficiente
        res.setHeader("Content-Disposition",
            disposicion + "; filename=\"" + nombreSeguro + "\"");

        try (InputStream in  = new FileInputStream(fichero);
             OutputStream out = res.getOutputStream()) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
        }
    }
}
