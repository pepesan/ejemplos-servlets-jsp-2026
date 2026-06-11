package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * ActionForm con validación programática: sobreescribir validate() para
 * devolver ActionErrors antes de que el Action se ejecute.
 *
 * Diferencia con el módulo 40: allí la validación estaba en Action.execute().
 * Aquí está en el Form, que es la capa correcta para validación de entrada.
 * Diferencia con RegistroForm: usa código Java en lugar de validation.xml.
 */
public class ContactoForm extends ActionForm {

    private String nombre  = "";
    private String email   = "";
    private String mensaje = "";

    public String getNombre()             { return nombre; }
    public String getEmail()              { return email; }
    public String getMensaje()            { return mensaje; }

    public void setNombre(String nombre)  { this.nombre  = nombre; }
    public void setEmail(String email)    { this.email   = email; }
    public void setMensaje(String mensaje){ this.mensaje = mensaje; }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        nombre  = "";
        email   = "";
        mensaje = "";
    }

    /**
     * Struts llama a este método cuando validate="true" en struts-config.xml.
     * Si devuelve ActionErrors no vacío, reenvía a input= sin llamar al Action.
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (nombre == null || nombre.trim().isEmpty()) {
            errors.add("nombre", new ActionMessage("contacto.error.nombre.requerido"));
        }

        if (email == null || email.trim().isEmpty()) {
            errors.add("email", new ActionMessage("contacto.error.email.requerido"));
        } else if (!email.contains("@") || email.indexOf('@') == email.length() - 1) {
            errors.add("email", new ActionMessage("contacto.error.email.formato"));
        }

        if (mensaje == null || mensaje.trim().isEmpty()) {
            errors.add("mensaje", new ActionMessage("contacto.error.mensaje.requerido"));
        } else if (mensaje.trim().length() < 10) {
            errors.add("mensaje", new ActionMessage("contacto.error.mensaje.corto"));
        }

        return errors;
    }
}
