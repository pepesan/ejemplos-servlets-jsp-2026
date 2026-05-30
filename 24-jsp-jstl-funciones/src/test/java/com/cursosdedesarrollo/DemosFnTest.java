package com.cursosdedesarrollo;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class DemosFnTest {

    @Test public void palabrasCsvTieneCinco() { assertEquals(5, DemosFn.palabrasCsv().size()); }
    @Test public void palabrasCsvContieneJava() { assertTrue(DemosFn.palabrasCsv().contains("java")); }

    @Test public void unirDosElementos()    { assertEquals("a-b",    DemosFn.unir(Arrays.asList("a","b"), "-")); }
    @Test public void unirUnElemento()      { assertEquals("solo",   DemosFn.unir(Arrays.asList("solo"), ",")); }
    @Test public void unirConComa()         { assertEquals("a,b,c",  DemosFn.unir(Arrays.asList("a","b","c"), ",")); }

    @Test public void contarDosApariciones() { assertEquals(2, DemosFn.contar("abcabc", "abc")); }
    @Test public void contarCero()           { assertEquals(0, DemosFn.contar("hello",  "xyz")); }
    @Test public void contarNull()           { assertEquals(0, DemosFn.contar(null,      "x")); }
}
