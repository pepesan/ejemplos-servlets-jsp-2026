package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class PathHelperTest {

    @Test
    public void rutaConContextoRaiz() {
        // contextPath="" cuando la app está desplegada en /
        assertEquals("/css/estilos.css", PathHelper.estatico("", "css/estilos.css"));
    }

    @Test
    public void rutaConContextoSubpath() {
        assertEquals("/miapp/css/estilos.css", PathHelper.estatico("/miapp", "css/estilos.css"));
    }

    @Test
    public void rutaConContextoNullTrataComoRaiz() {
        assertEquals("/js/demo.js", PathHelper.estatico(null, "js/demo.js"));
    }

    @Test
    public void rutaConRecursoConBarra() {
        assertEquals("/css/estilos.css", PathHelper.estatico("", "/css/estilos.css"));
    }

    @Test
    public void esWebInfInaccesible() {
        assertTrue(PathHelper.esWebInf("/WEB-INF/privado.html"));
        assertTrue(PathHelper.esWebInf("/WEB-INF/vistas/algo.jsp"));
        assertFalse(PathHelper.esWebInf("/css/estilos.css"));
        assertFalse(PathHelper.esWebInf("/index.html"));
    }
}
