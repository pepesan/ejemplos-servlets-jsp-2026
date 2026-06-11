package com.cursosdedesarrollo;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;

/**
 * ActionForm para subida de ficheros.
 * El campo FormFile es mapeado automáticamente por Struts desde la parte
 * multipart del request; no requiere @MultipartConfig ni código adicional.
 */
public class SubirFicheroForm extends ActionForm {

    private FormFile archivo;
    private String descripcion = "";

    public FormFile getArchivo() { return archivo; }
    public void setArchivo(FormFile archivo) { this.archivo = archivo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        archivo = null;
        descripcion = "";
    }
}
