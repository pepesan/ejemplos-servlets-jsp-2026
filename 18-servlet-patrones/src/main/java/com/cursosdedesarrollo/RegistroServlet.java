package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Servlet registrado exclusivamente de forma programática.
 *
 * No tiene @WebServlet y no aparece en web.xml.
 * RegistroListener lo registra en /registrado durante el arranque del contexto
 * mediante ServletContext.addServlet().
 *
 * Esto es lo que hacen internamente los frameworks como Spring MVC o Jersey
 * cuando se integran en una aplicación Servlet estándar.
 */
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Registro programático");
        Html.nav(out);

        out.println("<h1><span class='badge b1'>prog</span> Registro programático de servlets</h1>");
        out.println("<p class='sub'>Este servlet no tiene <code>@WebServlet</code> ni entrada en <code>web.xml</code>. " +
                "Fue registrado por <code>RegistroListener</code> durante el arranque.</p>");

        out.println("<div class='nota ok'>");
        out.println("&#10003; Si ves esta página, el registro programático funcionó. " +
                "El contenedor encontró <code>RegistroListener</code> gracias a <code>@WebListener</code> " +
                "y ejecutó su <code>contextInitialized()</code> antes de la primera petición.");
        out.println("</div>");

        out.println("<h2>Valores de la petición</h2>");
        Html.tablaInfo(out, req);

        out.println("<h2>Init parameters inyectados por el listener</h2>");
        out.println("<table>");
        out.println("<tr><th>Nombre</th><th>Valor</th></tr>");
        Enumeration<String> nombres = getServletConfig().getInitParameterNames();
        boolean hayParams = false;
        while (nombres.hasMoreElements()) {
            hayParams = true;
            String nombre = nombres.nextElement();
            out.println("<tr><td><code>" + Html.esc(nombre) + "</code></td>" +
                    "<td>" + Html.esc(getInitParameter(nombre)) + "</td></tr>");
        }
        if (!hayParams) {
            out.println("<tr><td colspan='2' class='nota'>Sin parámetros</td></tr>");
        }
        out.println("</table>");

        out.println("<h2>Código del listener</h2>");
        out.println("<pre>");
        out.println("@WebListener");
        out.println("public class RegistroListener implements ServletContextListener {");
        out.println("");
        out.println("    @Override");
        out.println("    public void contextInitialized(ServletContextEvent sce) {");
        out.println("        ServletContext ctx = sce.getServletContext();");
        out.println("");
        out.println("        // addServlet() devuelve un Dynamic para configurar el servlet");
        out.println("        ServletRegistration.Dynamic reg =");
        out.println("            ctx.addServlet(\"miServlet\", MiServlet.class);");
        out.println("");
        out.println("        reg.addMapping(\"/registrado\");");
        out.println("        reg.setInitParameter(\"clave\", \"valor\");");
        out.println("        reg.setLoadOnStartup(1);");
        out.println("    }");
        out.println("}");
        out.println("</pre>");

        out.println("<h2>Cuándo se usa en la realidad</h2>");
        out.println("<ul>");
        out.println("<li><strong>Spring MVC</strong>: <code>AbstractAnnotationConfigDispatcherServletInitializer</code> " +
                "registra el <code>DispatcherServlet</code> programáticamente al arranque.</li>");
        out.println("<li><strong>Jersey / JAX-RS</strong>: registra su servlet de aplicación sin web.xml.</li>");
        out.println("<li><strong>Plugins y librerías</strong>: añaden sus propios endpoints sin modificar el web.xml " +
                "del proyecto que las usa.</li>");
        out.println("<li><strong>Tests de integración</strong>: se registra el servlet al vuelo antes de cada test.</li>");
        out.println("</ul>");

        out.println("<h2>Alternativa: <code>ServletContainerInitializer</code></h2>");
        out.println("<pre>");
        out.println("// Para librerías que se añaden como JAR (sin acceso al web.xml del proyecto):");
        out.println("// En META-INF/services/javax.servlet.ServletContainerInitializer:");
        out.println("//   com.mibiblioteca.MiInicializador");
        out.println("");
        out.println("@HandlesTypes(MiAnotacion.class)");
        out.println("public class MiInicializador implements ServletContainerInitializer {");
        out.println("    public void onStartup(Set&lt;Class&lt;?&gt;&gt; clases, ServletContext ctx) {");
        out.println("        ctx.addServlet(\"dinamico\", NuevoServlet.class).addMapping(\"/dinamico\");");
        out.println("    }");
        out.println("}");
        out.println("</pre>");

        out.println("<p><a href='/'>← Inicio</a> &nbsp;|&nbsp; <a href='/patrones'>Referencia</a></p>");
        Html.pie(out);
    }
}
