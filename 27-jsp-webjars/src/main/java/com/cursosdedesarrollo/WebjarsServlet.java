package com.cursosdedesarrollo;

import org.webjars.WebJarVersionLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sirve recursos WebJar sin hardcodear la versión en la URL.
 *
 * URL recibida:  GET /wj/jquery/jquery.min.js
 * URL resuelta:  /webjars/jquery/3.7.1/jquery.min.js  (versión del pom.xml)
 *
 * Funciona gracias a webjars-locator-lite que escanea el classpath al arranque
 * y construye un mapa nombre → versión de cada WebJar presente en el WAR.
 *
 * Patrón de URL: /wj/{webjar}/{path-del-recurso}
 *   /wj/jquery/jquery.min.js
 *   /wj/bootstrap/css/bootstrap.min.css
 *   /wj/bootstrap/js/bootstrap.bundle.min.js
 */
public class WebjarsServlet extends HttpServlet {

    private WebJarVersionLocator locator;

    @Override
    public void init() {
        locator = new WebJarVersionLocator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // pathInfo = /jquery/jquery.min.js
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() < 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el nombre del WebJar");
            return;
        }

        // Separar /nombre/recurso
        String sin = pathInfo.substring(1); // "jquery/jquery.min.js"
        int barra = sin.indexOf('/');
        if (barra < 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato: /wj/{webjar}/{recurso}");
            return;
        }
        String webjar  = sin.substring(0, barra);           // "jquery"
        String recurso = sin.substring(barra + 1);           // "jquery.min.js"

        // Resolver la versión: path("jquery","jquery.min.js") → "3.7.1/jquery.min.js"
        String rutaVersionada = locator.path(webjar, recurso);
        if (rutaVersionada == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "WebJar no encontrado: " + webjar + " / " + recurso);
            return;
        }

        // path() devuelve "{webjar}/{version}/{recurso}", p.ej. "bootstrap/5.3.3/css/bootstrap.min.css".
        // Tomcat 7 no sirve META-INF/resources de JARs via forward(); redirect es la vía fiable.
        String rutaFinal = req.getContextPath() + "/webjars/" + rutaVersionada;
        resp.sendRedirect(rutaFinal);
    }
}
