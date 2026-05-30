package com.cursosdedesarrollo;

import javax.persistence.*;

/** Entidad simple usada para demostrar las consultas HQL y Criteria. */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 60)
    private String categoria;

    @Column
    private double precio;

    @Column
    private int stock;

    public Producto() {}

    public Producto(String nombre, String categoria, double precio, int stock) {
        this.nombre    = nombre;
        this.categoria = categoria;
        this.precio    = precio;
        this.stock     = stock;
    }

    public Long   getId()                         { return id; }
    public void   setId(Long id)                  { this.id = id; }
    public String getNombre()                     { return nombre; }
    public void   setNombre(String nombre)        { this.nombre = nombre; }
    public String getCategoria()                  { return categoria; }
    public void   setCategoria(String categoria)  { this.categoria = categoria; }
    public double getPrecio()                     { return precio; }
    public void   setPrecio(double precio)        { this.precio = precio; }
    public int    getStock()                      { return stock; }
    public void   setStock(int stock)             { this.stock = stock; }
}
