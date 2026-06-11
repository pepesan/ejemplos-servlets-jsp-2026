package com.cursosdedesarrollo;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Genera un saludo personalizado usando el mensaje "saludo.resultado"
 * del fichero de mensajes correspondiente al Locale activo.
 */
public class SaludoAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        DynaActionForm f = (DynaActionForm) form;
        String nombre = (String) f.get("nombre");

        if (nombre != null && !nombre.trim().isEmpty()) {
            // Obtener los mensajes del módulo para el Locale activo
            MessageResources messages = getResources(request);
            Locale locale = (Locale) request.getSession()
                    .getAttribute(Globals.LOCALE_KEY);
            if (locale == null) locale = request.getLocale();

            String saludo = messages.getMessage(locale, "saludo.resultado", nombre.trim());
            request.setAttribute("saludo", saludo);
        }

        return mapping.findForward("ok");
    }
}
