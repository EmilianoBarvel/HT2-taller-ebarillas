package com.auratheria.auratheriaformulario.auratheriagames.model;

/**
 * Clase POJO que representa un videojuego dentro del catálogo de la tienda
 * Auratheria Games (equivalente al "Catálogo de Productos" del escenario Retail).
 */
public class Videojuego {

    private int id;
    private String codigo;       // Código / SKU único (equivalente a codigo de barras)
    private String titulo;       // Nombre comercial del videojuego
    private String plataforma;   // PS5, Xbox Series X, Nintendo Switch, PC, etc.
    private String categoria;    // Género: Accion, Aventura, Deportes, RPG, etc.
    private double precioCosto;
    private double precioVenta;
    private int stock;

    public Videojuego() {
    }

    public Videojuego(String codigo, String titulo, String plataforma, String categoria,
                       double precioCosto, double precioVenta, int stock) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.categoria = categoria;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    public Videojuego(int id, String codigo, String titulo, String plataforma, String categoria,
                       double precioCosto, double precioVenta, int stock) {
        this(codigo, titulo, plataforma, categoria, precioCosto, precioVenta, stock);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return titulo + " (" + codigo + ")";
    }
}
