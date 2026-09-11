package com.auratheria.auratheriaformulario.auratheriagames.model;

/**
 * Clase POJO que representa a un usuario de Auratheria Games.
 */
public class Usuario {

    private int id;
    private String nombreCompleto;
    private String usuario;
    private String password;
    private String email;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String usuario, String password, String email) {
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.password = password;
        this.email = email;
    }

    public Usuario(int id, String nombreCompleto, String usuario, String password, String email) {
        this(nombreCompleto, usuario, password, email);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombreCompleto='" + nombreCompleto + "', usuario='" + usuario
                + "', email='" + email + "'}";
    }
}
