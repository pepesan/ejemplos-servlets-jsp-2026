package com.cursosdedesarrollo;

public class ConfiguracionApp {

    private final String entorno;
    private final String dbUrl;
    private final String dbUsuario;
    private final String logNivel;

    public ConfiguracionApp() {
        this.entorno   = System.getProperty("entorno",   "dev");
        this.dbUrl     = System.getProperty("db.url",    "jdbc:h2:mem:devdb");
        this.dbUsuario = System.getProperty("db.usuario","sa");
        this.logNivel  = System.getProperty("log.nivel", "DEBUG");
    }

    public String getEntorno()   { return entorno; }
    public String getDbUrl()     { return dbUrl; }
    public String getDbUsuario() { return dbUsuario; }
    public String getLogNivel()  { return logNivel; }

    @Override
    public String toString() {
        return String.format(
            "Entorno: %s | DB: %s | Usuario: %s | Log: %s",
            entorno, dbUrl, dbUsuario, logNivel
        );
    }
}
