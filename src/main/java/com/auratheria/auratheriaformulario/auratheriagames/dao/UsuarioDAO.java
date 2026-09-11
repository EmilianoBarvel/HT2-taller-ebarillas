package com.auratheria.auratheriaformulario.auratheriagames.dao;

import com.auratheria.auratheriaformulario.auratheriagames.config.ConnectionDB;
import com.auratheria.auratheriaformulario.auratheriagames.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO {

   
    public Usuario validarCredenciales(String usuario, String password) throws SQLException {
        String sql = "SELECT id, nombre_completo, usuario, password, email "
                + "FROM usuarios WHERE usuario = ? AND password = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre_completo"),
                            rs.getString("usuario"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

   
    public boolean existeUsuario(String usuario, String email) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE usuario = ? OR email = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    
    public boolean registrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_completo, usuario, password, email) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getEmail());

            return ps.executeUpdate() > 0;
        }
    }
}
