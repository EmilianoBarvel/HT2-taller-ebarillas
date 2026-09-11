package com.auratheria.auratheriaformulario.auratheriagames.controller;

import com.auratheria.auratheriaformulario.auratheriagames.dao.UsuarioDAO;
import com.auratheria.auratheriaformulario.auratheriagames.model.Usuario;
import com.auratheria.auratheriaformulario.auratheriagames.util.SceneManager;
import com.auratheria.auratheriaformulario.auratheriagames.util.Session;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleIngresar(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            SceneManager.showAlert(AlertType.WARNING, "Campos incompletos",
                    "Revisa el formulario", "Debes ingresar tu usuario y tu contraseña.");
            return;
        }

        try {
            Usuario usuarioValido = usuarioDAO.validarCredenciales(usuario, password);

            if (usuarioValido != null) {
                Session.setUsuarioActivo(usuarioValido);
                SceneManager.switchScene("/view/MainMenuView.fxml");
            } else {
                SceneManager.showAlert(AlertType.ERROR, "Acceso denegado",
                        "Credenciales incorrectas", "El usuario o la contraseña no son válidos.");
            }

        } catch (SQLException e) {
            SceneManager.showAlert(AlertType.ERROR, "Error de conexión",
                    "No se pudo conectar a la base de datos",
                    "Verifica que MySQL" + e.getMessage());
        } catch (Exception e) {
            SceneManager.showAlert(AlertType.ERROR, "Error inesperado",
                    "Ocurrió un problema al cambiar de pantalla", e.getMessage());
        }
    }

    @FXML
    private void handleCrearCuenta(ActionEvent event) {
        try {
            SceneManager.switchScene("/view/RegistroView.fxml");
        } catch (Exception e) {
            SceneManager.showAlert(AlertType.ERROR, "Error",
                    "No se pudo abrir la pantalla de Registro", e.getMessage());
        }
    }
}
