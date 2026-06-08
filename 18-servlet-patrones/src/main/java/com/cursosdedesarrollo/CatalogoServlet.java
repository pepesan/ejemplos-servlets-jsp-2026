package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Patrón 2 — Prefijo wildcard: url-pattern = "/catalogo/*"
 *
 * Responde a cualquier URL que empiece por /catalogo/:
 *   /catalogo/         → lista todos los productos
 *   /catalogo/1        → detalle del producto 1
 *   /catalogo/1/editar → formulario de edición (solo demo)
 *
 * Clave: getServletPath() devuelve "/catalogo" y getPathInfo() devuelve
 * la parte variable (ej. "/1/editar"). Con eso se puede implementar
 * enrutamiento REST-like sin framework.
 */
public class CatalogoServlet extends HttpServlet {

    private static final String[][] PRODUCTOS = {
        {"1", "Teclado mecánico",   "89,99 €"},
        {"2", "Monitor 4K 27\"",    "349,00 €"},
        {"3", "Ratón inalámbrico",  "45,50 €"},
        {"4", "Webcam HD",          "62,00 €"},
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String[] segmentos = parsearSegmentos(req.getPathInfo());

        if (segmentos.length == 0) {
            mostrarLista(req, out);
        } else if (segmentos.length == 1) {
            mostrarDetalle(req, out, segmentos[0]);
        } else if (segmentos.length == 2 && "editar".equals(segmentos[1])) {
            mostrarEditar(req, out, segmentos[0]);
        } else {
            mostrarNoEncontrado(req, out);
        }
    }

    private void mostrarLista(HttpServletRequest req, PrintWriter out) {
        Html.cabecera(out, "Catálogo – lista");
        Html.nav(out);

        out.println("<h1><span class='badge b2'>2</span> Prefijo wildcard — <code>/catalogo/*</code></h1>");
        out.println("<p class='sub'>El servlet captura cualquier URL que empiece por <code>/catalogo/</code>. " +
                "La sub-ruta variable llega en <code>getPathInfo()</code>.</p>");

        out.println("<h2>Valores de la petición (lista)</h2>");
        Html.tablaInfo(out, req);

        out.println("<h2>Productos (enrutamiento por pathInfo)</h2>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Nombre</th><th>Precio</th><th>Acciones</th></tr>");
        for (String[] p : PRODUCTOS) {
            out.println("<tr>");
            out.println("<td>" + Html.esc(p[0]) + "</td>");
            out.println("<td>" + Html.esc(p[1]) + "</td>");
            out.println("<td>" + Html.esc(p[2]) + "</td>");
            out.println("<td>");
            out.println("<a href='/catalogo/" + p[0] + "'>Ver</a> · ");
            out.println("<a href='/catalogo/" + p[0] + "/editar'>Editar</a>");
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<h2>Cómo funciona el enrutamiento</h2>");
        out.println("<pre>");
        out.println("String[] seg = parsearSegmentos(req.getPathInfo());");
        out.println("");
        out.println("// /catalogo/        → pathInfo=null o \"/\"   → seg.length==0 → lista");
        out.println("// /catalogo/1       → pathInfo=\"/1\"          → seg=[\"1\"]     → detalle");
        out.println("// /catalogo/1/editar→ pathInfo=\"/1/editar\"   → seg=[\"1\",\"editar\"] → editar");
        out.println("");
        out.println("static String[] parsearSegmentos(String pathInfo) {");
        out.println("    if (pathInfo == null || \"/\".equals(pathInfo)) return new String[0];");
        out.println("    String sin = pathInfo.startsWith(\"/\") ? pathInfo.substring(1) : pathInfo;");
        out.println("    return sin.isEmpty() ? new String[0] : sin.split(\"/\");");
        out.println("}");
        out.println("</pre>");

        out.println("<p><a href='/'>← Inicio</a> &nbsp;|&nbsp; <a href='/patrones'>Referencia</a></p>");
        Html.pie(out);
    }

    private void mostrarDetalle(HttpServletRequest req, PrintWriter out, String id) {
        Html.cabecera(out, "Catálogo – detalle");
        Html.nav(out);

        String[] producto = buscarProducto(id);
        out.println("<h1><span class='badge b2'>2</span> Prefijo wildcard — detalle de producto</h1>");

        out.println("<h2>Valores de la petición (detalle)</h2>");
        Html.tablaInfo(out, req);

        if (producto != null) {
            out.println("<h2>Producto #" + Html.esc(id) + "</h2>");
            out.println("<table>");
            out.println("<tr><th>Campo</th><th>Valor</th></tr>");
            out.println("<tr><td>ID</td><td>" + Html.esc(producto[0]) + "</td></tr>");
            out.println("<tr><td>Nombre</td><td>" + Html.esc(producto[1]) + "</td></tr>");
            out.println("<tr><td>Precio</td><td>" + Html.esc(producto[2]) + "</td></tr>");
            out.println("</table>");
            out.println("<p><a href='/catalogo/" + Html.esc(id) + "/editar'>Editar este producto</a></p>");
        } else {
            out.println("<div class='nota'>Producto <code>" + Html.esc(id) + "</code> no encontrado.</div>");
        }

        out.println("<p><a href='/catalogo/'>← Volver al catálogo</a></p>");
        Html.pie(out);
    }

    private void mostrarEditar(HttpServletRequest req, PrintWriter out, String id) {
        Html.cabecera(out, "Catálogo – editar");
        Html.nav(out);

        out.println("<h1><span class='badge b2'>2</span> Prefijo wildcard — sub-ruta anidada</h1>");
        out.println("<p class='sub'><code>/catalogo/" + Html.esc(id) + "/editar</code> → " +
                "pathInfo = <code>" + Html.esc(req.getPathInfo()) + "</code></p>");

        out.println("<h2>Valores de la petición (editar)</h2>");
        Html.tablaInfo(out, req);

        out.println("<h2>Demostración de sub-rutas</h2>");
        out.println("<p>Con un solo servlet (<code>/catalogo/*</code>) podemos manejar " +
                "URLs con cualquier profundidad:</p>");
        out.println("<pre>");
        out.println("/catalogo/           pathInfo = null");
        out.println("/catalogo/1          pathInfo = /1");
        out.println("/catalogo/1/editar   pathInfo = /1/editar");
        out.println("/catalogo/a/b/c/d    pathInfo = /a/b/c/d");
        out.println("</pre>");

        out.println("<p><a href='/catalogo/" + Html.esc(id) + "'>← Volver al detalle</a> &nbsp;|&nbsp; " +
                "<a href='/catalogo/'>← Catálogo</a></p>");
        Html.pie(out);
    }

    private void mostrarNoEncontrado(HttpServletRequest req, PrintWriter out) {
        Html.cabecera(out, "Catálogo – ruta desconocida");
        Html.nav(out);
        out.println("<h1>Ruta no reconocida</h1>");
        Html.tablaInfo(out, req);
        out.println("<p><a href='/catalogo/'>← Catálogo</a></p>");
        Html.pie(out);
    }

    private static String[] buscarProducto(String id) {
        for (String[] p : PRODUCTOS) {
            if (p[0].equals(id)) return p;
        }
        return null;
    }

    static String[] parsearSegmentos(String pathInfo) {
        if (pathInfo == null || "/".equals(pathInfo)) return new String[0];
        String sinBarra = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;
        return sinBarra.isEmpty() ? new String[0] : sinBarra.split("/", -1);
    }
}
