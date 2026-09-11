package com.auratheria.auratheriaformulario.auratheriagames.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static Connection connection;

    private ConnectionDB() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    CredentialsDB.URL_DB,
                    CredentialsDB.USER_DB,
                    CredentialsDB.PASS_DB
            );
        }
        return connection;
    }

    public static boolean testConnection() {
        try {
            getConnection();
            System.out.println("Conexión a MySQL exitosa.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al conectar a MySQL: " + e.getMessage());
            return false;
        }
    }
}
