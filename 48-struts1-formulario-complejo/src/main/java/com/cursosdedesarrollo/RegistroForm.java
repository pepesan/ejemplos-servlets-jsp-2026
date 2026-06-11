package com.cursosdedesarrollo;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegistroForm extends ActionForm {

    private String nombre       = "";
    private String apellidos    = "";
    private String email        = "";
    private String fechaNacimiento = "";
    private String genero       = "";
    private String pais         = "";
    private String[] tecnologias = new String[0]; // multibox requiere array
    private String modalidad    = "";
    private String nivel        = "";
    private String comentarios  = "";
    private String fechaDisponible = "";

    // ── getters ────────────────────────────────────────────────

    public String getNombre()          { return nombre; }
    public String getApellidos()       { return apellidos; }
    public String getEmail()           { return email; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getGenero()          { return genero; }
    public String getPais()            { return pais; }
    public String[] getTecnologias()   { return tecnologias; }
    public String getModalidad()       { return modalidad; }
    public String getNivel()           { return nivel; }
    public String getComentarios()     { return comentarios; }
    public String getFechaDisponible() { return fechaDisponible; }

    // ── setters ────────────────────────────────────────────────

    public void setNombre(String v)          { this.nombre = v == null ? "" : v.trim(); }
    public void setApellidos(String v)       { this.apellidos = v == null ? "" : v.trim(); }
    public void setEmail(String v)           { this.email = v == null ? "" : v.trim(); }
    public void setFechaNacimiento(String v) { this.fechaNacimiento = v == null ? "" : v.trim(); }
    public void setGenero(String v)          { this.genero = v == null ? "" : v; }
    public void setPais(String v)            { this.pais = v == null ? "" : v; }
    public void setTecnologias(String[] v)   { this.tecnologias = v == null ? new String[0] : v; }
    public void setModalidad(String v)       { this.modalidad = v == null ? "" : v; }
    public void setNivel(String v)           { this.nivel = v == null ? "" : v; }
    public void setComentarios(String v)     { this.comentarios = v == null ? "" : v; }
    public void setFechaDisponible(String v) { this.fechaDisponible = v == null ? "" : v.trim(); }

    // ── reset ─────────────────────────────────────────────────
    // Los checkboxes desmarcados no envían ningún valor: sin reset,
    // el array mantiene la selección anterior.

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        tecnologias = new String[0];
    }

    // ── validate ───────────────────────────────────────────────

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (nombre.isEmpty())
            errors.add("nombre", new ActionMessage("error.nombre.requerido"));

        if (apellidos.isEmpty())
            errors.add("apellidos", new ActionMessage("error.apellidos.requerido"));

        if (email.isEmpty()) {
            errors.add("email", new ActionMessage("error.email.requerido"));
        } else if (!email.contains("@") || email.indexOf('@') == email.length() - 1) {
            errors.add("email", new ActionMessage("error.email.invalido"));
        }

        if (fechaNacimiento.isEmpty()) {
            errors.add("fechaNacimiento", new ActionMessage("error.fechaNacimiento.requerida"));
        } else {
            try {
                LocalDate fn = LocalDate.parse(fechaNacimiento);
                if (!fn.isBefore(LocalDate.now()))
                    errors.add("fechaNacimiento", new ActionMessage("error.fechaNacimiento.futura"));
            } catch (DateTimeParseException e) {
                errors.add("fechaNacimiento", new ActionMessage("error.fechaNacimiento.invalida"));
            }
        }

        if (genero.isEmpty())
            errors.add("genero", new ActionMessage("error.genero.requerido"));

        if (pais.isEmpty())
            errors.add("pais", new ActionMessage("error.pais.requerido"));

        if (tecnologias.length == 0)
            errors.add("tecnologias", new ActionMessage("error.tecnologias.requeridas"));

        if (modalidad.isEmpty())
            errors.add("modalidad", new ActionMessage("error.modalidad.requerida"));

        if (nivel.isEmpty())
            errors.add("nivel", new ActionMessage("error.nivel.requerido"));

        if (comentarios.length() > 500)
            errors.add("comentarios", new ActionMessage("error.comentarios.longitud"));

        if (!fechaDisponible.isEmpty()) {
            try {
                LocalDate fd = LocalDate.parse(fechaDisponible);
                if (fd.isBefore(LocalDate.now()))
                    errors.add("fechaDisponible", new ActionMessage("error.fechaDisponible.pasada"));
            } catch (DateTimeParseException e) {
                errors.add("fechaDisponible", new ActionMessage("error.fechaDisponible.invalida"));
            }
        }

        return errors;
    }
}
