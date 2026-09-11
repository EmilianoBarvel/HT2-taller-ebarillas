package com.auratheria.auratheriaformulario.auratheriagames.controller;

import com.auratheria.auratheriaformulario.auratheriagames.dao.VideojuegoDAO;
import com.auratheria.auratheriaformulario.auratheriagames.model.Videojuego;
import com.auratheria.auratheriaformulario.auratheriagames.util.SceneManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CatalogoController implements Initializable {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtTitulo;
    @FXML private ComboBox<String> cmbPlataforma;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private TextField txtPrecioCosto;
    @FXML private TextField txtPrecioVenta;
    @FXML private TextField txtStock;

    @FXML private TableView<Videojuego> tablaVideojuegos;
    @FXML private TableColumn<Videojuego, Integer> colId;
    @FXML private TableColumn<Videojuego, String> colCodigo;
    @FXML private TableColumn<Videojuego, String> colTitulo;
    @FXML private TableColumn<Videojuego, String> colPlataforma;
    @FXML private TableColumn<Videojuego, String> colCategoria;
    @FXML private TableColumn<Videojuego, Double> colPrecioCosto;
    @FXML private TableColumn<Videojuego, Double> colPrecioVenta;
    @FXML private TableColumn<Videojuego, Integer> colStock;

    private final VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
    private final ObservableList<Videojuego> lista = FXCollections.observableArrayList();
    private Videojuego seleccionado; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecioCosto.setCellValueFactory(new PropertyValueFactory<>("precioCosto"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        cmbPlataforma.setItems(FXCollections.observableArrayList(
                "PS5", "PS4", "Xbox Series X", "Nintendo Switch", "PC"));
        cmbCategoria.setItems(FXCollections.observableArrayList(
                "Acción", "Aventura", "Deportes", "RPG", "Estrategia", "Terror"));

        tablaVideojuegos.setItems(lista);

        tablaVideojuegos.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo != null) {
                seleccionado = nuevo;
                cargarEnFormulario(nuevo);
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        try {
            lista.setAll(videojuegoDAO.listarTodos());
        } catch (SQLException e) {
            SceneManager.showAlert(AlertType.ERROR, "Error al cargar datos",
                    "No se pudo conectar a la base de datos", e.getMessage());
        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        Videojuego nuevo = validarYConstruir();
        if (nuevo == null) return;

        try {
            videojuegoDAO.guardar(nuevo);
            SceneManager.showAlert(AlertType.INFORMATION, "Éxito", "Guardado",
                    "El videojuego se registró correctamente.");
            handleLimpiar(event);
            cargarTabla();
        } catch (SQLException e) {
            SceneManager.showAlert(AlertType.ERROR, "Error al guardar", "No se pudo guardar", e.getMessage());
        }
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
        if (seleccionado == null) {
            SceneManager.showAlert(AlertType.WARNING, "Atención", "Selecciona un registro",
                    "Elige un videojuego de la tabla antes de actualizar.");
            return;
        }

        Videojuego actualizado = validarYConstruir();
        if (actualizado == null) return;
        actualizado.setId(seleccionado.getId());

        try {
            videojuegoDAO.actualizar(actualizado);
            SceneManager.showAlert(AlertType.INFORMATION, "Éxito", "Actualizado",
                    "El videojuego se actualizó correctamente.");
            handleLimpiar(event);
            cargarTabla();
        } catch (SQLException e) {
            SceneManager.showAlert(AlertType.ERROR, "Error al actualizar", "No se pudo actualizar", e.getMessage());
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        if (seleccionado == null) {
            SceneManager.showAlert(AlertType.WARNING, "Atención", "Selecciona un registro",
                    "Elige un videojuego de la tabla antes de eliminar.");
            return;
        }

        Alert confirmacion = new Alert(AlertType.CONFIRMATION,
                "¿Deseas eliminar '" + seleccionado.getTitulo() + "' del catálogo?",
                ButtonType.YES, ButtonType.NO);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.YES) {
                try {
                    videojuegoDAO.eliminar(seleccionado.getId());
                    SceneManager.showAlert(AlertType.INFORMATION, "Éxito", "Eliminado",
                            "El videojuego se eliminó correctamente.");
                    handleLimpiar(event);
                    cargarTabla();
                } catch (SQLException e) {
                    SceneManager.showAlert(AlertType.ERROR, "Error al eliminar", "No se pudo eliminar", e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleLimpiar(ActionEvent event) {
        txtCodigo.clear();
        txtTitulo.clear();
        cmbPlataforma.getSelectionModel().clearSelection();
        cmbPlataforma.getEditor().clear();
        cmbCategoria.getSelectionModel().clearSelection();
        cmbCategoria.getEditor().clear();
        txtPrecioCosto.clear();
        txtPrecioVenta.clear();
        txtStock.clear();
        seleccionado = null;
        tablaVideojuegos.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        try {
            SceneManager.switchScene("/view/MainMenuView.fxml");
        } catch (Exception e) {
            SceneManager.showAlert(AlertType.ERROR, "Error", "No se pudo volver al menú", e.getMessage());
        }
    }


    private void cargarEnFormulario(Videojuego v) {
        txtCodigo.setText(v.getCodigo());
        txtTitulo.setText(v.getTitulo());
        cmbPlataforma.getEditor().setText(v.getPlataforma());
        cmbCategoria.getEditor().setText(v.getCategoria());
        txtPrecioCosto.setText(String.valueOf(v.getPrecioCosto()));
        txtPrecioVenta.setText(String.valueOf(v.getPrecioVenta()));
        txtStock.setText(String.valueOf(v.getStock()));
    }

   
    private Videojuego validarYConstruir() {
        String codigo = txtCodigo.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String plataforma = cmbPlataforma.getEditor().getText().trim();
        String categoria = cmbCategoria.getEditor().getText().trim();
        String costoTexto = txtPrecioCosto.getText().trim();
        String ventaTexto = txtPrecioVenta.getText().trim();
        String stockTexto = txtStock.getText().trim();

        if (codigo.isEmpty() || titulo.isEmpty() || costoTexto.isEmpty()
                || ventaTexto.isEmpty() || stockTexto.isEmpty()) {
            SceneManager.showAlert(AlertType.WARNING, "Datos incompletos", "Revisa el formulario",
                    "Código, Título, Precio Costo, Precio Venta y Stock son obligatorios.");
            return null;
        }

        double costo, venta;
        int stock;
        try {
            costo = Double.parseDouble(costoTexto);
            venta = Double.parseDouble(ventaTexto);
        } catch (NumberFormatException e) {
            SceneManager.showAlert(AlertType.WARNING, "Dato inválido", "Revisa el formulario",
                    "Precio Costo y Precio Venta deben ser valores numéricos (ej: 250.00).");
            return null;
        }

        try {
            stock = Integer.parseInt(stockTexto);
        } catch (NumberFormatException e) {
            SceneManager.showAlert(AlertType.WARNING, "Dato inválido", "Revisa el formulario",
                    "Stock debe ser un número entero (ej: 10).");
            return null;
        }

        if (costo < 0 || venta < 0 || stock < 0) {
            SceneManager.showAlert(AlertType.WARNING, "Dato inválido", "Revisa el formulario",
                    "Los precios y el stock no pueden ser negativos.");
            return null;
        }

        if (venta < costo) {
            SceneManager.showAlert(AlertType.WARNING, "Regla de negocio", "Precio inválido",
                    "El precio de venta no puede ser menor al precio de costo.");
            return null;
        }

        return new Videojuego(codigo, titulo, plataforma, categoria, costo, venta, stock);
    }
}
