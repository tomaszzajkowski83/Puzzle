package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {
    //static FXMLLoader loader;

    public static int wynik =0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        primaryStage.setTitle(" SLIDE PUZZLE");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
