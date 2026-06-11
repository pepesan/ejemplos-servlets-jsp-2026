package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class DescargarFicheroAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        String nombreParam = request.getParameter("nombre");
        if (nombreParam == null || nombreParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'nombre' requerido");
            return null;
        }

        String nombreSeguro = nombreParam.replaceAll("[^a-zA-Z0-9._-]", "_");

        File tmpDir  = (File) getServlet().getServletContext()
                                          .getAttribute("javax.servlet.context.tempdir");
        File fichero = new File(tmpDir, nombreSeguro);

        if (!fichero.getCanonicalPath().startsWith(tmpDir.getCanonicalPath() + File.separator)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        if (!fichero.exists() || !fichero.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                               "Fichero no encontrado: " + nombreSeguro);
            return null;
        }

        String mime = getServlet().getServletContext().getMimeType(nombreSeguro);
        if (mime == null) mime = "application/octet-stream";

        boolean inline = "true".equalsIgnoreCase(request.getParameter("ver"));

        response.setContentType(mime);
        response.setContentLength((int) fichero.length());
        response.setHeader("Content-Disposition",
            (inline ? "inline" : "attachment") + "; filename=\"" + nombreSeguro + "\"");

        try (FileInputStream in = new FileInputStream(fichero);
             OutputStream out   = response.getOutputStream()) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) out.write(buf, 0, n);
        }

        return null; // ya escribimos directamente en el response
    }
}
