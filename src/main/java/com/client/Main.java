package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Greatest stock exchange");
        primaryStage.setScene(new Scene(root, 350, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() throws Exception {
        Controller.closeConnections();
        super.stop();
    }
}
