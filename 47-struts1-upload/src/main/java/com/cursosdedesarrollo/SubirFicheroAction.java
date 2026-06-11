package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class SubirFicheroAction extends Action {

    private static final Set<String> TIPOS_PERMITIDOS = new HashSet<>(Arrays.asList(
        "image/jpeg", "image/png", "image/gif", "image/webp",
        "application/pdf", "text/plain"
    ));

    private static final int MAX_BYTES = 2 * 1024 * 1024; // 2 MB

    @Override
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        SubirFicheroForm f = (SubirFicheroForm) form;
        FormFile ff = f.getArchivo();

        ActionMessages errors = new ActionMessages();

        if (ff == null || ff.getFileSize() == 0 || ff.getFileName().trim().isEmpty()) {
            errors.add("archivo", new ActionMessage("error.archivo.requerido"));
        } else if (!TIPOS_PERMITIDOS.contains(ff.getContentType())) {
            errors.add("archivo", new ActionMessage("error.archivo.tipo", ff.getContentType()));
        } else if (ff.getFileSize() > MAX_BYTES) {
            errors.add("archivo", new ActionMessage("error.archivo.tamanio",
                formatearTam(ff.getFileSize())));
        }

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.getInputForward();
        }

        // Guardar en el directorio temporal del servidor
        File tmpDir = (File) getServlet().getServletContext()
                              .getAttribute("javax.servlet.context.tempdir");
        String nombreOriginal = ff.getFileName().trim();
        String nombreSeguro   = nombreOriginal.replaceAll("[^a-zA-Z0-9._-]", "_");
        File destino = new File(tmpDir, nombreSeguro);

        try (InputStream in  = ff.getInputStream();
             OutputStream out = new FileOutputStream(destino)) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) out.write(buf, 0, n);
        }

        ff.destroy(); // liberar recursos internos del FormFile

        request.setAttribute("nombreOriginal", nombreOriginal);
        request.setAttribute("nombreSeguro",   nombreSeguro);
        request.setAttribute("nombreUrl",      URLEncoder.encode(nombreSeguro, "UTF-8"));
        request.setAttribute("mime",           ff.getContentType());
        request.setAttribute("tamanio",        formatearTam(ff.getFileSize()));
        request.setAttribute("ruta",           destino.getAbsolutePath());
        request.setAttribute("descripcion",    f.getDescripcion());

        return mapping.findForward("exito");
    }

    static String formatearTam(int bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format(Locale.ROOT, "%.1f KB", bytes / 1024.0);
        return String.format(Locale.ROOT, "%.2f MB", bytes / (1024.0 * 1024));
    }
}
