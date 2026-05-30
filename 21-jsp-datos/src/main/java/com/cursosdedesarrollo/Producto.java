package com.cursosdedesarrollo;

/**
 * JavaBean que representa un producto de un catálogo.
 * Sigue la convención JavaBean: constructor vacío + getters/setters.
 * EL accede a las propiedades mediante los getters:
 *   ${producto.nombre}  →  producto.getNombre()
 *   ${producto.precio}  →  producto.getPrecio()
 */
public class Producto {

    private int    id;
    private String nombre;
    private double precio;
    private String categoria;
    private boolean disponible;

    public Producto() {}

    public Producto(int id, String nombre, double precio, String categoria, boolean disponible) {
        this.id         = id;
        this.nombre     = nombre;
        this.precio     = precio;
        this.categoria  = categoria;
        this.disponible = disponible;
    }

    public int     getId()          { return id; }
    public String  getNombre()      { return nombre; }
    public double  getPrecio()      { return precio; }
    public String  getCategoria()   { return categoria; }
    public boolean isDisponible()   { return disponible; }

    public void setId(int id)                   { this.id         = id; }
    public void setNombre(String nombre)        { this.nombre     = nombre; }
    public void setPrecio(double precio)        { this.precio     = precio; }
    public void setCategoria(String categoria)  { this.categoria  = categoria; }
    public void setDisponible(boolean d)        { this.disponible = d; }

    @Override
    public String toString() {
        return "Producto{id=" + id + ", nombre='" + nombre + "', precio=" + precio + "}";
    }
}
