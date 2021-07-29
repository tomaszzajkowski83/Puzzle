package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuController {


    @FXML
    void newGameClicked(ActionEvent event) throws IOException {
        System.out.println("Ustawiam scene rozgrywki");
        Parent playAnchor = FXMLLoader.load(getClass().getResource("PlayGround.fxml"));
        Scene playScene = new Scene(playAnchor);
        Stage okno = (Stage) ((Node) event.getSource()).getScene().getWindow();
        okno.setScene(playScene);
        okno.show();
    }

    @FXML
    void exitClicked(ActionEvent event) throws IOException {
        System.out.println("Koniec Gry");
        Stage okno = (Stage) ((Node) event.getSource()).getScene().getWindow();
        okno.close();
    }

    @FXML
    void HighScoresClicked(ActionEvent event) throws IOException {
        System.out.println("Wczytuje wyniki");
        Parent highScore = FXMLLoader.load(getClass().getResource("HighScore.fxml"));
        Scene playScene = new Scene(highScore);
        Stage okno = (Stage) ((Node) event.getSource()).getScene().getWindow();
        okno.setScene(playScene);
        okno.show();
    }

}
