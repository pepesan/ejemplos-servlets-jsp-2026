package com.cursosdedesarrollo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * GET  /login → muestra el formulario de login
 * POST /login → autentica al usuario (credenciales admin/admin)
 *
 * Si el login es correcto guarda el nombre de usuario en la sesión
 * con la clave "usuario" y redirige a /protegido/area (patrón PRG).
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        mostrarLogin(req, resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String usuario = req.getParameter("usuario");
        String clave   = req.getParameter("clave");

        if ("admin".equals(usuario) && "admin".equals(clave)) {
            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuario", usuario);
            resp.sendRedirect(req.getContextPath() + "/protegido/area");
        } else {
            mostrarLogin(req, resp, "Credenciales incorrectas. Prueba admin / admin.");
        }
    }

    private void mostrarLogin(HttpServletRequest req, HttpServletResponse resp, String error)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        Html.cabecera(out, "Login");
        out.println("<h1>Acceso</h1>");
        out.println("<p class='sub'>AuthFilter redirige aquí cuando intentas acceder a <code>/protegido/*</code> sin sesión.</p>");

        if (error != null) {
            out.println("<p class='error'>&#9888; " + Html.esc(error) + "</p>");
        }

        out.println("<form method='post' action='login' style='max-width:320px'>");
        out.println("<label for='usuario'>Usuario</label>");
        out.println("<input type='text' id='usuario' name='usuario' value='admin' required>");
        out.println("<label for='clave'>Contraseña</label>");
        out.println("<input type='password' id='clave' name='clave' value='admin' required>");
        out.println("<button type='submit'>Entrar</button>");
        out.println("</form>");

        out.println("<p class='hint' style='margin-top:1.5em'>Credenciales de prueba: <code>admin</code> / <code>admin</code></p>");
        out.println("<p><a href='/'>← Inicio</a></p>");
        Html.pie(out);
    }
}
