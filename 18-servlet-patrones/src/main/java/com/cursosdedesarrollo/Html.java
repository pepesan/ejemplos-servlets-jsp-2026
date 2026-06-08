package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

class Html {

    static void cabecera(PrintWriter out, String titulo) {
        out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
        out.println("<title>" + esc(titulo) + "</title>");
        out.println("<style>");
        out.println("body{font-family:monospace;max-width:960px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}");
        out.println("h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.4em}");
        out.println("p,li{line-height:1.6;color:#a6adc8}.sub{color:#a6adc8;font-size:.9em;margin-top:-.5em}");
        out.println("a{color:#89dceb}code{color:#a6e3a1}");
        out.println("table{border-collapse:collapse;width:100%;margin:.6em 0}");
        out.println("td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;vertical-align:top;font-size:.88em}");
        out.println("th{background:#313244;color:#cba6f7;white-space:nowrap}");
        out.println("pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;");
        out.println("    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}");
        out.println(".nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;color:#a6adc8;margin:.5em 0}");
        out.println(".ok{color:#a6e3a1}.ko{color:#f38ba8}.warn{color:#fab387}");
        out.println(".nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}");
        out.println(".nav a{color:#a6adc8;text-decoration:none;margin-right:1.2em}");
        out.println(".nav a:hover{color:#cdd6f4}");
        out.println(".badge{display:inline-block;padding:.1em .5em;border-radius:3px;font-size:.8em;font-weight:bold}");
        out.println(".b1{background:#313244;color:#89b4fa}.b2{background:#313244;color:#a6e3a1}");
        out.println(".b3{background:#313244;color:#fab387}.b4{background:#313244;color:#cba6f7}");
        out.println("</style></head><body>");
    }

    static void nav(PrintWriter out) {
        out.println("<div class='nav'>");
        out.println("<a href='/'>&#127968; Inicio</a>");
        out.println("<a href='/patrones'>&#128218; Referencia</a>");
        out.println("<span style='color:#585b70'>|</span>");
        out.println("<a href='/exacto'>[1] Exacto</a>");
        out.println("<a href='/catalogo/'>[2] Prefijo</a>");
        out.println("<a href='/listar.do'>[3] Extensión</a>");
        out.println("<a href='/no-mapeado'>[4] Default</a>");
        out.println("<span style='color:#585b70'>|</span>");
        out.println("<a href='/'>[\"\"] Raíz</a>");
        out.println("<a href='/buscar'>[multi] Multi-patrón</a>");
        out.println("<a href='/comodin-total'>[/*] Wildcard total</a>");
        out.println("<a href='/registrado'>[prog] Registro</a>");
        out.println("</div>");
    }

    static void pie(PrintWriter out) {
        out.println("</body></html>");
    }

    static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    static void tablaInfo(PrintWriter out, HttpServletRequest req) {
        out.println("<table>");
        out.println("<tr><th>Método HttpServletRequest</th><th>Valor en esta petición</th><th>Qué representa</th></tr>");
        fila(out, "getContextPath()",  req.getContextPath(),  "raíz del contexto (vacío si desplegado en /)");
        fila(out, "getServletPath()",  req.getServletPath(),  "parte de la URI que coincidió con el patrón");
        fila(out, "getPathInfo()",     req.getPathInfo(),     "sub-ruta tras el patrón (* en prefijo; null en exacto/extensión)");
        fila(out, "getRequestURI()",   req.getRequestURI(),   "URI completa (contextPath + servletPath + pathInfo)");
        fila(out, "getQueryString()",  req.getQueryString(),  "parámetros GET (la parte tras ?)");
        out.println("</table>");
    }

    private static void fila(PrintWriter out, String metodo, String valor, String desc) {
        String valorHtml = valor != null
                ? "<code>" + esc(valor) + "</code>"
                : "<span class='ko'>null</span>";
        out.println("<tr><td><code>" + esc(metodo) + "</code></td><td>" + valorHtml + "</td><td>" + esc(desc) + "</td></tr>");
    }
}
