package com.cursosdedesarrollo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demuestra el ciclo de vida de un Servlet:
 *
 *   1. init()    — llamado UNA VEZ al cargar el servlet en el contenedor
 *   2. service() — llamado en CADA petición (delega a doGet/doPost)
 *   3. destroy() — llamado UNA VEZ al descargar el servlet
 *
 * El contador de peticiones usa AtomicInteger porque service() puede
 * ejecutarse en hilos concurrentes: el contenedor mantiene UNA instancia
 * del servlet y atiende múltiples peticiones simultáneas con hilos distintos.
 */
public class CicloVidaServlet extends HttpServlet {

    // Variable de instancia: compartida entre todas las peticiones
    private String autor;
    private String version;
    private LocalDateTime momentoInit;
    private final AtomicInteger contadorPeticiones = new AtomicInteger(0);

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // -------------------------------------------------------------------------
    // 1. INIT — el contenedor llama este método una sola vez, al cargar el servlet
    // -------------------------------------------------------------------------
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);   // IMPORTANTE: siempre llamar a super.init()

        // Leer parámetros de inicialización definidos en web.xml
        autor   = config.getInitParameter("autor");
        version = config.getInitParameter("version");

        momentoInit = LocalDateTime.now();

        log(">>> init() ejecutado — servlet cargado en memoria");
        log("    autor=" + autor + ", version=" + version);
    }

    // -------------------------------------------------------------------------
    // 2. SERVICE — el contenedor llama este método en cada petición HTTP
    //    HttpServlet.service() examina el método HTTP y delega a doGet/doPost
    //    Sobreescribimos aquí solo para hacer visible la llamada; en producción
    //    normalmente NO se sobreescribe service(), solo doGet/doPost.
    // -------------------------------------------------------------------------
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int numPeticion = contadorPeticiones.incrementAndGet();
        log(">>> service() — petición #" + numPeticion + " [" + req.getMethod() + " " + req.getRequestURI() + "]");

        // Delegar al doGet/doPost correspondiente
        super.service(req, resp);
    }

    // -------------------------------------------------------------------------
    // doGet — lógica real para peticiones GET
    // -------------------------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String ahora = LocalDateTime.now().format(FMT);
        int peticiones = contadorPeticiones.get();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'><head><meta charset='UTF-8'>");
        out.println("<title>Ciclo de Vida del Servlet</title>");
        out.println("<style>");
        out.println("  body { font-family: monospace; margin: 2em; background: #1e1e2e; color: #cdd6f4; }");
        out.println("  h1   { color: #89b4fa; }");
        out.println("  .fase { background: #313244; border-left: 4px solid #89b4fa;");
        out.println("          padding: .8em 1.2em; margin: 1em 0; border-radius: 4px; }");
        out.println("  .fase h2 { margin: 0 0 .4em 0; color: #a6e3a1; font-size: 1em; }");
        out.println("  .badge { display:inline-block; padding:.2em .6em; border-radius:4px;");
        out.println("           font-weight:bold; font-size:.85em; }");
        out.println("  .una-vez { background:#f38ba8; color:#1e1e2e; }");
        out.println("  .cada-vez { background:#a6e3a1; color:#1e1e2e; }");
        out.println("  table { border-collapse: collapse; width:100%; }");
        out.println("  td,th { border:1px solid #45475a; padding:.5em 1em; text-align:left; }");
        out.println("  th { background:#313244; color:#cba6f7; }");
        out.println("</style></head><body>");

        out.println("<h1>Ciclo de Vida del Servlet</h1>");

        // Fase 1 — init
        out.println("<div class='fase'>");
        out.println("  <h2>1. init() <span class='badge una-vez'>UNA VEZ</span></h2>");
        out.println("  <table>");
        out.println("  <tr><th>Cuándo se llamó</th><td>" + momentoInit.format(FMT) + "</td></tr>");
        out.println("  <tr><th>init-param: autor</th><td>" + autor + "</td></tr>");
        out.println("  <tr><th>init-param: version</th><td>" + version + "</td></tr>");
        out.println("  <tr><th>Servlet name</th><td>" + getServletName() + "</td></tr>");
        out.println("  </table>");
        out.println("</div>");

        // Fase 2 — service / doGet
        out.println("<div class='fase'>");
        out.println("  <h2>2. service() → doGet() <span class='badge cada-vez'>CADA PETICIÓN</span></h2>");
        out.println("  <table>");
        out.println("  <tr><th>Petición nº</th><td><strong>" + peticiones + "</strong></td></tr>");
        out.println("  <tr><th>Momento</th><td>" + ahora + "</td></tr>");
        out.println("  <tr><th>Método HTTP</th><td>" + req.getMethod() + "</td></tr>");
        out.println("  <tr><th>URI</th><td>" + req.getRequestURI() + "</td></tr>");
        out.println("  <tr><th>IP cliente</th><td>" + req.getRemoteAddr() + "</td></tr>");
        out.println("  <tr><th>User-Agent</th><td>" + req.getHeader("User-Agent") + "</td></tr>");
        out.println("  </table>");
        out.println("</div>");

        // Fase 3 — destroy
        out.println("<div class='fase'>");
        out.println("  <h2>3. destroy() <span class='badge una-vez'>UNA VEZ</span></h2>");
        out.println("  <p>Se ejecutará cuando el servidor se detenga o la aplicación se redespliegue.");
        out.println("  Verás el mensaje <code>&gt;&gt;&gt; destroy()</code> en la consola de Tomcat.</p>");
        out.println("</div>");

        out.println("<p><a href='/'>↺ Recargar (incrementa el contador)</a></p>");
        out.println("</body></html>");
    }

    // -------------------------------------------------------------------------
    // 3. DESTROY — el contenedor llama este método una sola vez, al descargar
    // -------------------------------------------------------------------------
    @Override
    public void destroy() {
        log(">>> destroy() ejecutado — servlet descargado de memoria. Total peticiones atendidas: "
                + contadorPeticiones.get());
        super.destroy();
    }
}
