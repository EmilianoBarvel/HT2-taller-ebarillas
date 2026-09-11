package com.auratheria.auratheriaformulario.auratheriagames;

import com.auratheria.auratheriaformulario.auratheriagames.util.SceneManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
       
        SceneManager.setStage(primaryStage);

        primaryStage.setTitle("Auratheria Games");
        primaryStage.setResizable(false);
       
        SceneManager.switchScene("/view/LoginView.fxml");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
