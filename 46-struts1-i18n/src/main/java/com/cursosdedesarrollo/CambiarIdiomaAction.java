package com.cursosdedesarrollo;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Cambia el Locale activo guardándolo en sesión bajo Globals.LOCALE_KEY.
 * Las taglibs de Struts (<html:message>, <bean:message>) leen ese atributo
 * para seleccionar el fichero de mensajes correcto (ApplicationResources_es,
 * ApplicationResources_en, etc.).
 */
public class CambiarIdiomaAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        String lang = request.getParameter("lang");
        if ("es".equals(lang) || "en".equals(lang)) {
            // Globals.LOCALE_KEY = "org.apache.struts.action.LOCALE"
            request.getSession().setAttribute(Globals.LOCALE_KEY, new Locale(lang));
        }
        return mapping.findForward("inicio");
    }
}
