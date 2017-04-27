package ua.training.lab;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Runner extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        Scene scene = new Scene(root, 537, 320);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to FileDataExcluder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

