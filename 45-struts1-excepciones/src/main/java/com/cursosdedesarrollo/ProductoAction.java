package com.cursosdedesarrollo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action que lanza distintas excepciones según el parámetro "tipo".
 * No contiene ningún try/catch: Struts intercepta las excepciones
 * usando la configuración de &lt;global-exceptions&gt; y &lt;exception&gt;
 * del struts-config.xml.
 */
public class ProductoAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        String tipo = request.getParameter("tipo");

        if ("notfound".equals(tipo)) {
            // Interceptada por: <global-exceptions> → RecursoNoEncontradoException
            // Handler: LoggingExceptionHandler → noEncontrado.jsp
            throw new RecursoNoEncontradoException("Producto con id=99 no existe en el catálogo");
        }

        if ("division".equals(tipo)) {
            // Interceptada por: <exception> local en /producto → ArithmeticException
            // → errorCalculo.jsp  (sobrescribe la excepción global RuntimeException)
            int resultado = 10 / 0;
        }

        if ("general".equals(tipo)) {
            // Interceptada por: <global-exceptions> → RuntimeException
            // Handler: ExceptionHandler por defecto → error.jsp
            throw new RuntimeException("Fallo inesperado procesando la petición");
        }

        // Camino feliz
        request.setAttribute("mensaje", "Operación ejecutada sin excepciones.");
        return mapping.findForward("ok");
    }
}
