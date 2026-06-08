package com.cursosdedesarrollo;

import javax.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    @Column(length = 60)
    private String categoria;

    public Producto() {}
    public Producto(String nombre, double precio, String categoria) {
        this.nombre    = nombre;
        this.precio    = precio;
        this.categoria = categoria;
    }

    public Long   getId()                        { return id; }
    public void   setId(Long id)                 { this.id = id; }
    public String getNombre()                    { return nombre; }
    public void   setNombre(String n)            { this.nombre = n; }
    public double getPrecio()                    { return precio; }
    public void   setPrecio(double p)            { this.precio = p; }
    public String getCategoria()                 { return categoria; }
    public void   setCategoria(String c)         { this.categoria = c; }
}
