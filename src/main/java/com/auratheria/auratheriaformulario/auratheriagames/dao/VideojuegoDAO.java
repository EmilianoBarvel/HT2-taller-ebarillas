package com.auratheria.auratheriaformulario.auratheriagames.dao;

import com.auratheria.auratheriaformulario.auratheriagames.config.ConnectionDB;
import com.auratheria.auratheriaformulario.auratheriagames.model.Videojuego;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VideojuegoDAO {

    public boolean guardar(Videojuego v) throws SQLException {
        String sql = "INSERT INTO videojuegos (codigo, titulo, plataforma, categoria, precio_costo, precio_venta, stock) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getCodigo());
            ps.setString(2, v.getTitulo());
            ps.setString(3, v.getPlataforma());
            ps.setString(4, v.getCategoria());
            ps.setDouble(5, v.getPrecioCosto());
            ps.setDouble(6, v.getPrecioVenta());
            ps.setInt(7, v.getStock());

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("Ya existe un videojuego con el código '" + v.getCodigo() + "'.", e);
        }
    }


    public List<Videojuego> listarTodos() throws SQLException {
        List<Videojuego> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, titulo, plataforma, categoria, precio_costo, precio_venta, stock "
                + "FROM videojuegos ORDER BY id";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Videojuego(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getString("plataforma"),
                        rs.getString("categoria"),
                        rs.getDouble("precio_costo"),
                        rs.getDouble("precio_venta"),
                        rs.getInt("stock")
                ));
            }
        }
        return lista;
    }


    public boolean actualizar(Videojuego v) throws SQLException {
        String sql = "UPDATE videojuegos SET codigo = ?, titulo = ?, plataforma = ?, categoria = ?, "
                + "precio_costo = ?, precio_venta = ?, stock = ? WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getCodigo());
            ps.setString(2, v.getTitulo());
            ps.setString(3, v.getPlataforma());
            ps.setString(4, v.getCategoria());
            ps.setDouble(5, v.getPrecioCosto());
            ps.setDouble(6, v.getPrecioVenta());
            ps.setInt(7, v.getStock());
            ps.setInt(8, v.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("Ya existe un videojuego con el código '" + v.getCodigo() + "'.", e);
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM videojuegos WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
