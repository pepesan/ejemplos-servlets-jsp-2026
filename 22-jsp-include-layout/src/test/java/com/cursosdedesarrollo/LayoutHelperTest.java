package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class LayoutHelperTest {

    @Test public void activoCoincide()         { assertEquals("activo", LayoutHelper.activo("inicio", "inicio")); }
    @Test public void activoNoCoinicide()       { assertEquals("",       LayoutHelper.activo("inicio", "demos")); }
    @Test public void activoNullPagina()        { assertEquals("",       LayoutHelper.activo(null, "inicio")); }
    @Test public void activoNullItem()          { assertEquals("",       LayoutHelper.activo("inicio", null)); }

    @Test public void migasUnaEntraada()        { assertEquals("Inicio",            LayoutHelper.migas("Inicio")); }
    @Test public void migasDosEntradas()        { assertEquals("Inicio › Demos",    LayoutHelper.migas("Inicio", "Demos")); }
    @Test public void migasTresEntradas()       { assertEquals("Inicio › A › B",    LayoutHelper.migas("Inicio", "A", "B")); }
    @Test public void migasArrayVacio()         { assertEquals("",                   LayoutHelper.migas()); }
    @Test public void migasNull()               { assertEquals("",                   LayoutHelper.migas((String[]) null)); }
}
