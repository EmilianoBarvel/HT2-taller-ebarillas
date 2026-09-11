package com.auratheria.auratheriaformulario.auratheriagames.util;

import com.auratheria.auratheriaformulario.auratheriagames.model.Usuario;


public class Session {

    private static Usuario usuarioActivo;

    private Session() {
    }

    public static Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public static void setUsuarioActivo(Usuario usuario) {
        usuarioActivo = usuario;
    }

    public static void cerrarSesion() {
        usuarioActivo = null;
    }
}
