package com.example.sistemagasolinera;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/sistemagasolinera/hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 500, 400);
    stage.setTitle("Sistema Gasolinera");
    stage.setScene(scene);
    stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}