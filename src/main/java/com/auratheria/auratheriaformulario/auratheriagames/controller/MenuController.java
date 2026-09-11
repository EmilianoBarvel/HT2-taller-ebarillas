package com.auratheria.auratheriaformulario.auratheriagames.controller;

import com.auratheria.auratheriaformulario.auratheriagames.model.Usuario;
import com.auratheria.auratheriaformulario.auratheriagames.util.SceneManager;
import com.auratheria.auratheriaformulario.auratheriagames.util.Session;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class MenuController implements Initializable {

    @FXML
    private Label lblUsuarioActivo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario activo = Session.getUsuarioActivo();
        if (activo != null) {
            lblUsuarioActivo.setText(activo.getNombreCompleto());
        }
    }

    @FXML
    private void handleInicio(ActionEvent event) {
        SceneManager.showAlert(AlertType.INFORMATION, "Inicio", "Bienvenido",
                "Este es el panel de inicio de Auratheria Games.");
    }

    @FXML
    private void handleCatalogo(ActionEvent event) {
        try {
            SceneManager.switchScene("/view/CatalogoView.fxml");
        } catch (Exception e) {
            SceneManager.showAlert(AlertType.ERROR, "Error",
                    "No se pudo abrir el catálogo", e.getMessage());
        }
    }

    @FXML
    private void handlePerfil(ActionEvent event) {
        SceneManager.showAlert(AlertType.INFORMATION, "Perfil",
                "Módulo en construcción", "Aquí se mostrará el perfil del usuario.");
    }

    @FXML
    private void handleConfiguracion(ActionEvent event) {
        SceneManager.showAlert(AlertType.INFORMATION, "Configuración",
                "Módulo en construcción", "Aquí irán las preferencias de la cuenta.");
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        Session.cerrarSesion();
        try {
            SceneManager.switchScene("/view/LoginView.fxml");
        } catch (Exception e) {
            SceneManager.showAlert(AlertType.ERROR, "Error",
                    "No se pudo cerrar sesión correctamente", e.getMessage());
        }
    }
}
