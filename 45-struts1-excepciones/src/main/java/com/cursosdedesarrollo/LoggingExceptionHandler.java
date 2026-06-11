package com.cursosdedesarrollo;

import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handler personalizado que registra la excepción antes de delegar al
 * comportamiento estándar de Struts (guardar el mensaje en request y
 * hacer forward a la vista configurada en struts-config.xml).
 */
public class LoggingExceptionHandler extends ExceptionHandler {

    private static final Logger LOG = Logger.getLogger(LoggingExceptionHandler.class.getName());

    @Override
    public ActionForward execute(Exception ex,
                                 ExceptionConfig ae,
                                 ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException {

        LOG.log(Level.WARNING,
            "[LoggingExceptionHandler] Excepción interceptada: "
                + ex.getClass().getSimpleName() + " — " + ex.getMessage(), ex);

        // Añadimos el stacktrace al request para que la vista pueda mostrarlo
        request.setAttribute("stacktrace", stacktrace(ex));

        return super.execute(ex, ae, mapping, form, request, response);
    }

    private static String stacktrace(Exception ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(ex.toString()).append("\n");
        for (StackTraceElement el : ex.getStackTrace()) {
            sb.append("    at ").append(el).append("\n");
        }
        return sb.toString();
    }
}
