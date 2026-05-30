package com.cursosdedesarrollo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Comando {

    /**
     * Ejecuta la lógica del comando.
     * @return nombre del fichero JSP en WEB-INF/vistas/ a renderizar,
     *         o null si el comando ya escribió la respuesta (ej: redirect).
     */
    String ejecutar(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
