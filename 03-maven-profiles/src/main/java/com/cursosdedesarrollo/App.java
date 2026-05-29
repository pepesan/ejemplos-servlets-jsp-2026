package com.cursosdedesarrollo;

public class App {

    public static void main(String[] args) {
        ConfiguracionApp config = new ConfiguracionApp();

        String sep = "─".repeat(50);
        System.out.println(sep);
        System.out.println("  Demo Maven Profiles");
        System.out.println(sep);
        System.out.printf("  Entorno  : %s%n", config.getEntorno());
        System.out.printf("  DB URL   : %s%n", config.getDbUrl());
        System.out.printf("  Usuario  : %s%n", config.getDbUsuario());
        System.out.printf("  Log nivel: %s%n", config.getLogNivel());
        System.out.println(sep);
    }
}
