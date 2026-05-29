package com.cursosdedesarrollo;

public class App {

    public static String saludar(Persona persona) {
        String saludo = "Hola, " + persona.getNombre();
        if (!persona.esMayorDeEdad()) {
            saludo += " (menor de edad)";
        }
        return saludo;
    }

    public static void main(String[] args) {
        Persona adulto = new Persona("Ana", 30);
        Persona menor  = new Persona("Luis", 15);

        System.out.println(saludar(adulto));
        System.out.println(saludar(menor));
        System.out.println("Personas: " + adulto + ", " + menor);
    }
}
