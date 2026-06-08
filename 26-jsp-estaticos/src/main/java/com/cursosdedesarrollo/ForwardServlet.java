package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Demuestra el problema de rutas relativas en forwards.
 *
 * Este servlet vive en /api/demo. Hace forward a una JSP dentro de WEB-INF,
 * que es inaccesible directamente. El navegador solo ve /api/demo como URL,
 * por lo que rutas relativas en la JSP se resolverían desde /api/, no desde /.
 */
public class ForwardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/vistas/forward.jsp")
           .forward(req, resp);
    }
}
