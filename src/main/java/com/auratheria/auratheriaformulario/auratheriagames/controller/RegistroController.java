package com.auratheria.auratheriaformulario.auratheriagames.controller;

import com.auratheria.auratheriaformulario.auratheriagames.dao.UsuarioDAO;
import com.auratheria.auratheriaformulario.auratheriagames.model.Usuario;
import com.auratheria.auratheriaformulario.auratheriagames.util.SceneManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController implements Initializable {

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        String nombreCompleto = txtNombreCompleto.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombreCompleto.isEmpty() || usuario.isEmpty() || password.isEmpty() || email.isEmpty()) {
            SceneManager.showAlert(AlertType.WARNING, "Campos incompletos",
                    "Revisa el formulario", "Todos los campos son obligatorios.");
            return;
        }

        try {
            if (usuarioDAO.existeUsuario(usuario, email)) {
                SceneManager.showAlert(AlertType.WARNING, "Usuario existente",
                        "Ese usuario o correo ya está registrado",
                        "Intenta con otro usuario o correo electrónico.");
                return;
            }

            Usuario nuevoUsuario = new Usuario(nombreCompleto, usuario, password, email);
            boolean guardado = usuarioDAO.registrar(nuevoUsuario);

            if (guardado) {
                SceneManager.showAlert(AlertType.INFORMATION, "Registro exitoso",
                        "¡Cuenta creada!", "Tu cuenta se registró correctamente. Ahora inicia sesión.");
                SceneManager.switchScene("/view/LoginView.fxml");
            } else {
                SceneManager.showAlert(AlertType.ERROR, "Error al guardar",
                        "No se pudo crear la cuenta", "Intenta de nuevo.");
            }

        } catch (SQLException e) {
            SceneManager.showAlert(AlertType.ERROR, "Error de conexión",
                    "No se pudo conectar a la base de datos",
                    "Verifica que MySQL esté corriendo y revisa Credentials.java.\n" + e.getMessage());
        } catch (Exception e) {
            SceneManager.showAlert(AlertType.ERROR, "Error inesperado",
                    "Ocurrió un problema al cambiar de pantalla", e.getMessage());
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        try {
            SceneManager.switchScene("/view/LoginView.fxml");
        } catch (Exception e) {
            SceneManager.showAlert(AlertType.ERROR, "Error",
                    "No se pudo volver al Login", e.getMessage());
        }
    }
}
