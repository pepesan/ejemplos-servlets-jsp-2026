package com.cursosdedesarrollo;

import java.io.PrintWriter;

class Html {

    static void cabecera(PrintWriter out, String titulo) {
        out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
        out.println("<title>" + esc(titulo) + "</title>");
        out.println("<style>");
        out.println("body{font-family:monospace;max-width:860px;margin:2em auto;background:#1e1e2e;color:#cdd6f4}");
        out.println("h1{color:#89b4fa}h2{color:#cba6f7;margin-top:1.4em}");
        out.println("p,li{line-height:1.6;color:#a6adc8}.sub{color:#a6adc8;font-size:.9em;margin-top:-.5em}");
        out.println(".hint{color:#a6adc8;font-size:.85em;font-style:italic}");
        out.println("a{color:#89dceb}");
        out.println("table{border-collapse:collapse;width:100%;margin:.6em 0}");
        out.println("td,th{border:1px solid #45475a;padding:.4em .8em;text-align:left;vertical-align:top}");
        out.println("th{background:#313244;color:#cba6f7;white-space:nowrap}");
        out.println("pre{background:#181825;border-left:3px solid #89b4fa;padding:.8em 1em;");
        out.println("    overflow-x:auto;border-radius:4px;font-size:.85em;line-height:1.5}");
        out.println("input{background:#313244;color:#cdd6f4;border:1px solid #45475a;");
        out.println("    padding:.4em .7em;border-radius:4px;font-family:monospace;font-size:.95em;width:100%;box-sizing:border-box}");
        out.println("label{display:block;margin-top:.8em;color:#cba6f7;font-size:.9em}");
        out.println("button{margin-top:1em;background:#89b4fa;color:#1e1e2e;border:none;");
        out.println("    padding:.5em 1.4em;border-radius:4px;cursor:pointer;font-weight:bold;font-size:.95em}");
        out.println("button:hover{background:#b4d0f7}.ok{color:#a6e3a1}.error{color:#f38ba8}");
        out.println(".badge{display:inline-block;padding:.1em .4em;border-radius:3px;font-size:.75em;font-weight:bold;margin-right:.3em}");
        out.println(".b-log{background:#fab387;color:#1e1e2e}.b-charset{background:#89dceb;color:#1e1e2e}");
        out.println(".b-auth{background:#cba6f7;color:#1e1e2e}.b-ok{background:#a6e3a1;color:#1e1e2e}");
        out.println("</style></head><body>");
    }

    static void pie(PrintWriter out) {
        out.println("</body></html>");
    }

    static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
