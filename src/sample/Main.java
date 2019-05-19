//Prepared by Linda Wallace
//Day 6 Exercise
//Winter OOSD
//May 20, 2019
//Program initializes stage, scene and controller

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Controller controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Update Agent Info");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
        controller = loader.getController();
        controller.setMain(this);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
