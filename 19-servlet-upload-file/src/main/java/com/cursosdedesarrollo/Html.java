package com.cursosdedesarrollo;

import java.io.PrintWriter;

class Html {

    static void cabecera(PrintWriter out, String titulo) {
        out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
        out.println("<title>" + esc(titulo) + "</title>");
        out.println("<style>");
        out.println("body{font-family:monospace;max-width:900px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}");
        out.println("h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.4em}");
        out.println("p,li{line-height:1.6;color:#a6adc8}.sub{color:#a6adc8;font-size:.9em;margin-top:-.5em}");
        out.println("a{color:#89dceb}code{color:#a6e3a1}");
        out.println("table{border-collapse:collapse;width:100%;margin:.6em 0}");
        out.println("td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;vertical-align:top;font-size:.88em}");
        out.println("th{background:#313244;color:#cba6f7;white-space:nowrap}");
        out.println("pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;");
        out.println("    overflow-x:auto;border-radius:0 4px 4px 0;font-size:.88em;line-height:1.6}");
        out.println(".nota{background:#313244;padding:.6em 1em;border-radius:4px;font-size:.88em;color:#a6adc8;margin:.5em 0}");
        out.println(".ok{color:#a6e3a1}.ko{color:#f38ba8}");
        out.println(".nav{background:#313244;padding:.5em 1em;border-radius:4px;font-size:.85em;margin-bottom:1.5em}");
        out.println(".nav a{color:#a6adc8;text-decoration:none;margin-right:1em}");
        out.println(".nav a:hover{color:#cdd6f4}");
        out.println("input[type=file]{color:#cdd6f4;margin:.6em 0}");
        out.println("button,input[type=submit]{background:#45475a;color:#cdd6f4;border:none;padding:.4em 1em;");
        out.println("    border-radius:3px;cursor:pointer;font-family:monospace;font-size:.9em;margin-top:.6em}");
        out.println("button:hover,input[type=submit]:hover{background:#585b70}");
        out.println("form{background:#313244;padding:1em 1.4em;border-radius:6px;margin:1em 0}");
        out.println("label{color:#cba6f7;display:block;margin-top:.8em;font-size:.9em}");
        out.println("input[type=text]{background:#1e1e2e;color:#cdd6f4;border:1px solid #45475a;");
        out.println("    padding:.3em .6em;border-radius:3px;font-family:monospace;font-size:.9em;width:100%;margin-top:.2em}");
        out.println("</style></head><body>");
    }

    static void nav(PrintWriter out) {
        out.println("<div class='nav'>");
        out.println("<a href='/'>Inicio</a>");
        out.println("<a href='/subir'>Subir fichero</a>");
        out.println("</div>");
    }

    static void pie(PrintWriter out) {
        out.println("</body></html>");
    }

    static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
