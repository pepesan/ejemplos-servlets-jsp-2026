package com.cursosdedesarrollo;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Ejemplo C: formulario de alta de empleado validado con Bean Validation (JSR-380).
 *
 * No contiene ningún método validate(): la lógica de validación está
 * declarada en las anotaciones. BeanValidationForm.validate() las ejecuta
 * y convierte cada violación en un ActionError de Struts.
 */
public class EmpleadoForm extends BeanValidationForm {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 80, message = "El nombre debe tener entre 2 y 80 caracteres.")
    private String nombre = "";

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    private String email = "";

    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "[0-9]{9,15}", message = "El teléfono debe tener entre 9 y 15 dígitos numéricos.")
    private String telefono = "";

    @NotBlank(message = "El NIF es obligatorio.")
    @Pattern(regexp = "\\d{8}[A-Z]", message = "El NIF debe tener 8 dígitos seguidos de una letra mayúscula (p. ej. 12345678Z).")
    private String nif = "";

    public String getNombre()   { return nombre; }
    public String getEmail()    { return email; }
    public String getTelefono() { return telefono; }
    public String getNif()      { return nif; }

    public void setNombre(String nombre)     { this.nombre = nombre; }
    public void setEmail(String email)       { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setNif(String nif)           { this.nif = nif; }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        nombre   = "";
        email    = "";
        telefono = "";
        nif      = "";
    }
}
