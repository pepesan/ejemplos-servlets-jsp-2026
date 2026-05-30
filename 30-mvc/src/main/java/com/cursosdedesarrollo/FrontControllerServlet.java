package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontControllerServlet extends HttpServlet {

    private Map<String, Comando> comandos;

    @Override
    public void init() {
        comandos = new HashMap<>();
        comandos.put("/productos",       new ListarProductosComando());
        comandos.put("/productos/ver",   new VerProductoComando());
        comandos.put("/productos/nuevo", new CrearProductoComando());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/app/productos");
            return;
        }

        Comando cmd = comandos.get(path);
        if (cmd == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta desconocida: " + path);
            return;
        }

        try {
            String vista = cmd.ejecutar(req, resp);
            if (vista != null) {
                req.getRequestDispatcher("/WEB-INF/vistas/" + vista).forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException("Error en comando para " + path, e);
        }
    }
}
