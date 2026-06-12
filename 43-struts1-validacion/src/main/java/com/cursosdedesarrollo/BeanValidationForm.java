package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Clase base que conecta Bean Validation (JSR-380 / Hibernate Validator) con Struts 1.x.
 *
 * Las subclases solo necesitan añadir anotaciones de validación en sus campos
 * (@NotBlank, @Email, @Size, @Pattern…). Este validate() las ejecuta y convierte
 * cada ConstraintViolation en un ActionMessage con el texto de la anotación.
 *
 * Se usa ParameterMessageInterpolator para evitar la dependencia de EL 3.0,
 * ya que Tomcat 7 solo incluye EL 2.2 (sin javax.el.ELManager).
 */
public abstract class BeanValidationForm extends ActionForm {

    private static final ValidatorFactory FACTORY = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory();

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        Set<ConstraintViolation<BeanValidationForm>> violations =
                FACTORY.getValidator().validate(this);
        ActionErrors errors = new ActionErrors();
        for (ConstraintViolation<?> v : violations) {
            String field = v.getPropertyPath().toString();
            // false → el mensaje es texto literal, no clave del .properties
            errors.add(field, new ActionMessage(v.getMessage(), false));
        }
        return errors;
    }
}
