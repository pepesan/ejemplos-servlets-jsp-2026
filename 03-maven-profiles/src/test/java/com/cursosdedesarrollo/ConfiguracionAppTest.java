package com.cursosdedesarrollo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfiguracionAppTest {

    @Test
    public void valorPorDefectoEntorno() {
        System.clearProperty("entorno");
        ConfiguracionApp config = new ConfiguracionApp();
        assertEquals("dev", config.getEntorno());
    }

    @Test
    public void valorPorDefectoDbUrl() {
        System.clearProperty("db.url");
        ConfiguracionApp config = new ConfiguracionApp();
        assertEquals("jdbc:h2:mem:devdb", config.getDbUrl());
    }

    @Test
    public void sobreescrituraPorSystemProperty() {
        System.setProperty("entorno", "prod");
        System.setProperty("db.url", "jdbc:mysql://host/db");
        System.setProperty("db.usuario", "produser");
        System.setProperty("log.nivel", "WARN");
        try {
            ConfiguracionApp config = new ConfiguracionApp();
            assertEquals("prod",                  config.getEntorno());
            assertEquals("jdbc:mysql://host/db",  config.getDbUrl());
            assertEquals("produser",              config.getDbUsuario());
            assertEquals("WARN",                  config.getLogNivel());
        } finally {
            System.clearProperty("entorno");
            System.clearProperty("db.url");
            System.clearProperty("db.usuario");
            System.clearProperty("log.nivel");
        }
    }

    @Test
    public void toStringContieneEntorno() {
        System.setProperty("entorno", "ci");
        try {
            ConfiguracionApp config = new ConfiguracionApp();
            assertTrue(config.toString().contains("ci"));
        } finally {
            System.clearProperty("entorno");
        }
    }
}
