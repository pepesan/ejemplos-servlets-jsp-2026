package com.cursosdedesarrollo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gestiona el formulario de edición de un perfil existente.
 *
 * GET  /editar?id=X       → carga el perfil X de la "BD" en memoria y muestra editar.jsp
 * POST /editar?id=X       → valida; si errores: forward con errores y valores enviados;
 *                           si ok: actualiza la "BD" y redirect a /editar?id=X&ok=1 (PRG)
 * GET  /editar?id=X&ok=1  → mismo editar.jsp con banner de éxito
 */
public class EditarPerfilServlet extends HttpServlet {

    static final String VISTA_EDITAR = "/WEB-INF/vistas/editar.jsp";

    static final Map<Integer, PerfilBean> BD = new ConcurrentHashMap<>();

    static {
        BD.put(1, new PerfilBean(1, "Ana García",    "ana@ejemplo.com",    "612345678", "Madrid",    "admin",   true));
        BD.put(2, new PerfilBean(2, "Luis Martínez", "luis@ejemplo.com",   "698765432", "Barcelona", "editor",  true));
        BD.put(3, new PerfilBean(3, "Carmen López",  "carmen@ejemplo.com", "655443322", "Sevilla",   "usuario", false));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PerfilBean bean = cargarPerfil(req);
        if (bean == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            return;
        }
        req.setAttribute("bean",    bean);
        req.setAttribute("guardado", "1".equals(req.getParameter("ok")));
        req.getRequestDispatcher(VISTA_EDITAR).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        PerfilBean original = cargarPerfil(req);
        if (original == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            return;
        }

        PerfilBean editado = new PerfilBean(
            original.getId(),
            req.getParameter("nombre"),
            req.getParameter("email"),
            req.getParameter("telefono"),
            req.getParameter("ciudad"),
            req.getParameter("rol"),
            "true".equals(req.getParameter("activo"))
        );

        Map<String, String> errores = validar(editado);

        if (!errores.isEmpty()) {
            req.setAttribute("bean",    editado);
            req.setAttribute("errores", errores);
            req.getRequestDispatcher(VISTA_EDITAR).forward(req, resp);
            return;
        }

        BD.put(editado.getId(), editado);
        resp.sendRedirect(req.getContextPath() + "/editar?id=" + editado.getId() + "&ok=1");
    }

    private PerfilBean cargarPerfil(HttpServletRequest req) {
        String idParam = req.getParameter("id");
        if (idParam == null) return BD.get(1);
        try {
            return BD.get(Integer.parseInt(idParam));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Map<String, String> validar(PerfilBean bean) {
        Map<String, String> errores = new LinkedHashMap<>();
        String e;
        if ((e = validarNombre(bean.getNombre()))     != null) errores.put("nombre",   e);
        if ((e = validarEmail(bean.getEmail()))       != null) errores.put("email",    e);
        if ((e = validarTelefono(bean.getTelefono())) != null) errores.put("telefono", e);
        if ((e = validarCiudad(bean.getCiudad()))     != null) errores.put("ciudad",   e);
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

    static String validarTelefono(String v) {
        if (v == null || v.trim().isEmpty()) return null;
        if (!v.trim().matches("\\d{9}"))    return "El teléfono debe tener 9 dígitos numéricos.";
        return null;
    }

    static String validarCiudad(String v) {
        if (v == null || v.trim().isEmpty()) return "La ciudad es obligatoria.";
        return null;
    }
}
