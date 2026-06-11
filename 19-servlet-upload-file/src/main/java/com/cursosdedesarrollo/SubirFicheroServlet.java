package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@WebServlet("/subir")
@MultipartConfig(
    fileSizeThreshold = 64 * 1024,       // 64 KB — por encima se vuelca a disco temporal
    maxFileSize       = 2 * 1024 * 1024, // 2 MB máximo por fichero
    maxRequestSize    = 5 * 1024 * 1024  // 5 MB máximo por petición completa
)
public class SubirFicheroServlet extends HttpServlet {

    private static final Set<String> TIPOS_PERMITIDOS = new HashSet<>(Arrays.asList(
        "image/jpeg", "image/png", "image/gif", "image/webp",
        "application/pdf", "text/plain"
    ));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        Html.cabecera(out, "Subida de ficheros");
        Html.nav(out);

        out.println("<h1>Subida de ficheros con <code>@MultipartConfig</code></h1>");
        out.println("<p class='sub'>Tipos permitidos: JPEG · PNG · GIF · WEBP · PDF · TXT &mdash; máximo 2 MB</p>");

        out.println("<form method='post' action='subir' enctype='multipart/form-data'>");
        out.println("  <label>Fichero</label>");
        out.println("  <input type='file' name='archivo' accept='image/*,.pdf,.txt'><br>");
        out.println("  <label>Descripción (campo de texto normal en formulario multipart)</label>");
        out.println("  <input type='text' name='descripcion' placeholder='Descripción opcional'>");
        out.println("  <button type='submit' style='margin-top:1em'>Subir &rarr;</button>");
        out.println("</form>");

        out.println("<h2>Cómo funciona</h2>");
        out.println("<pre>// 1. La anotación @MultipartConfig habilita request.getPart()");
        out.println("@WebServlet(\"/subir\")");
        out.println("@MultipartConfig(");
        out.println("    fileSizeThreshold = 64 * 1024,         // &lt; umbral: en memoria");
        out.println("    maxFileSize       = 2 * 1024 * 1024,   // por fichero: 2 MB");
        out.println("    maxRequestSize    = 5 * 1024 * 1024    // petición completa: 5 MB");
        out.println(")");
        out.println("");
        out.println("// 2. El formulario requiere enctype multipart");
        out.println("&lt;form method='post' enctype='multipart/form-data'&gt;");
        out.println("    &lt;input type='file' name='archivo'&gt;");
        out.println("    &lt;input type='text' name='descripcion'&gt;");
        out.println("&lt;/form&gt;");
        out.println("");
        out.println("// 3. Leer la parte del fichero en doPost()");
        out.println("Part parte = request.getPart(\"archivo\");");
        out.println("String nombre = extraerNombreFichero(parte.getHeader(\"content-disposition\"));");
        out.println("String mime   = parte.getContentType();");
        out.println("long   tam    = parte.getSize();");
        out.println("");
        out.println("// 4. Los campos de texto siguen con request.getParameter()");
        out.println("String desc = request.getParameter(\"descripcion\");");
        out.println("");
        out.println("// 5. Guardar en disco");
        out.println("File tmpDir = (File) getServletContext()");
        out.println("                 .getAttribute(\"javax.servlet.context.tempdir\");");
        out.println("parte.write(new File(tmpDir, nombreSeguro).getAbsolutePath());</pre>");

        out.println("<h2>Equivalente en <code>web.xml</code></h2>");
        out.println("<pre>&lt;servlet&gt;");
        out.println("    &lt;servlet-name&gt;subir&lt;/servlet-name&gt;");
        out.println("    &lt;servlet-class&gt;com.cursosdedesarrollo.SubirFicheroServlet&lt;/servlet-class&gt;");
        out.println("    &lt;multipart-config&gt;");
        out.println("        &lt;file-size-threshold&gt;65536&lt;/file-size-threshold&gt;");
        out.println("        &lt;max-file-size&gt;2097152&lt;/max-file-size&gt;");
        out.println("        &lt;max-request-size&gt;5242880&lt;/max-request-size&gt;");
        out.println("    &lt;/multipart-config&gt;");
        out.println("&lt;/servlet&gt;</pre>");
        out.println("<p class='nota'>Anotación y XML son equivalentes. En producción se prefiere XML");
        out.println("para modificar los límites sin recompilar.</p>");

        Html.pie(out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        Html.cabecera(out, "Resultado — subida de fichero");
        Html.nav(out);

        Part parte;
        try {
            parte = req.getPart("archivo");
        } catch (IllegalStateException e) {
            out.println("<h1 class='ko'>Fichero demasiado grande</h1>");
            out.println("<p>" + Html.esc(e.getMessage()) + "</p>");
            out.println("<p><a href='subir'>&larr; Volver</a></p>");
            Html.pie(out);
            return;
        }

        String nombre      = extraerNombreFichero(parte.getHeader("content-disposition"));
        String mime        = parte.getContentType();
        long   tam         = parte.getSize();
        String descripcion = req.getParameter("descripcion");

        out.println("<h1>Resultado de la subida</h1>");

        if (nombre.isEmpty() || tam == 0) {
            out.println("<p class='ko'>No se seleccionó ningún fichero.</p>");
        } else if (!TIPOS_PERMITIDOS.contains(mime)) {
            out.println("<p class='ko'>Tipo MIME no permitido: <code>" + Html.esc(mime) + "</code></p>");
            out.println("<p class='nota'>Admitidos: image/jpeg, image/png, image/gif, image/webp, application/pdf, text/plain</p>");
        } else {
            File tmpDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
            String nombreSeguro = nombre.replaceAll("[^a-zA-Z0-9._-]", "_");
            File destino = new File(tmpDir, nombreSeguro);
            parte.write(destino.getAbsolutePath());

            out.println("<p class='ok'>&#10003; Fichero guardado correctamente.</p>");
            out.println("<table>");
            out.println("<tr><th>Propiedad</th><th>Valor</th></tr>");
            out.println("<tr><td>Nombre original</td><td>" + Html.esc(nombre) + "</td></tr>");
            out.println("<tr><td>Nombre en servidor</td><td><code>" + Html.esc(nombreSeguro) + "</code></td></tr>");
            out.println("<tr><td>MIME type</td><td><code>" + Html.esc(mime) + "</code></td></tr>");
            out.println("<tr><td>Tamaño</td><td>" + formatearTam(tam) + "</td></tr>");
            out.println("<tr><td>Ruta temporal</td><td><code>" + Html.esc(destino.getAbsolutePath()) + "</code></td></tr>");
            if (descripcion != null && !descripcion.trim().isEmpty()) {
                out.println("<tr><td>Descripción</td><td>" + Html.esc(descripcion) + "</td></tr>");
            }
            out.println("</table>");

            String urlNombre = URLEncoder.encode(nombreSeguro, "UTF-8");
            out.println("<p style='margin-top:1em'>");
            out.println("  <a href='descargar?nombre=" + urlNombre + "'>&#8595; Descargar</a>");
            out.println("  &nbsp;&nbsp;");
            out.println("  <a href='descargar?nombre=" + urlNombre + "&ver=true'>&#128065; Ver en el navegador</a>");
            out.println("</p>");
        }

        out.println("<p style='margin-top:1.2em'><a href='subir'>&larr; Subir otro fichero</a></p>");
        Html.pie(out);
    }

    static String extraerNombreFichero(String contentDisposition) {
        if (contentDisposition == null) return "";
        for (String token : contentDisposition.split(";")) {
            token = token.trim();
            if (token.startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }

    static String formatearTam(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format(Locale.ROOT, "%.1f KB", bytes / 1024.0);
        return String.format(Locale.ROOT, "%.2f MB", bytes / (1024.0 * 1024));
    }
}
