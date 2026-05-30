package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gestiona el ciclo completo de un formulario de registro con patrón PRG.
 *
 * GET  /registro          → muestra el formulario vacío
 * POST /registro          → valida; si errores: forward al formulario con errores;
 *                           si ok: guarda en sesión y redirect a /registro?ok=1 (PRG)
 * GET  /registro?ok=1     → muestra la página de éxito
 */
public class RegistroServlet extends HttpServlet {

    static final String VISTA_FORM  = "/WEB-INF/vistas/formulario.jsp";
    static final String VISTA_EXITO = "/WEB-INF/vistas/exito.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("1".equals(req.getParameter("ok"))) {
            req.getRequestDispatcher(VISTA_EXITO).forward(req, resp);
            return;
        }
        req.setAttribute("bean", new RegistroBean());
        req.getRequestDispatcher(VISTA_FORM).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        RegistroBean bean = new RegistroBean(
            req.getParameter("nombre"),
            req.getParameter("email"),
            req.getParameter("password"),
            req.getParameter("edad")
        );

        Map<String, String> errores = validar(bean);

        if (!errores.isEmpty()) {
            // Hay errores: volvemos al formulario (sin redirect → no PRG)
            req.setAttribute("bean",    bean);
            req.setAttribute("errores", errores);
            req.getRequestDispatcher(VISTA_FORM).forward(req, resp);
            return;
        }

        // Éxito: guardar nombre en sesión y redirigir (PRG)
        req.getSession(true).setAttribute("usuarioRegistrado", bean.getNombre());
        resp.sendRedirect(req.getContextPath() + "/registro?ok=1");
    }

    // ── Validación ────────────────────────────────────────────────────────────

    static Map<String, String> validar(RegistroBean bean) {
        Map<String, String> errores = new LinkedHashMap<>();
        String e;
        if ((e = validarNombre(bean.getNombre()))     != null) errores.put("nombre",   e);
        if ((e = validarEmail(bean.getEmail()))       != null) errores.put("email",    e);
        if ((e = validarPassword(bean.getPassword())) != null) errores.put("password", e);
        if ((e = validarEdad(bean.getEdad()))         != null) errores.put("edad",     e);
        return errores;
    }

    static String validarNombre(String v) {
        if (v == null || v.trim().isEmpty()) return "El nombre es obligatorio.";
        if (v.trim().length() < 3)          return "El nombre debe tener al menos 3 caracteres.";
        return null;
    }

    static String validarEmail(String v) {
        if (v == null || v.trim().isEmpty()) return "El correo es obligatorio.";
        if (!v.contains("@"))               return "El correo debe contener @.";
        return null;
    }

    static String validarPassword(String v) {
        if (v == null || v.trim().isEmpty()) return "La contraseña es obligatoria.";
        if (v.length() < 6)                 return "La contraseña debe tener al menos 6 caracteres.";
        return null;
    }

    static String validarEdad(String v) {
        if (v == null || v.trim().isEmpty()) return "La edad es obligatoria.";
        try {
            int n = Integer.parseInt(v.trim());
            if (n < 18)  return "Debes ser mayor de 18 años.";
            if (n > 120) return "Edad no válida.";
        } catch (NumberFormatException e) {
            return "La edad debe ser un número entero.";
        }
        return null;
    }
}
