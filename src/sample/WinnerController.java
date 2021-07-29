package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;

public class WinnerController {

    int wynikTimer;

    @FXML
    private TextField nick;

    @FXML
    private Label gratulacje;

    @FXML
    void initialize() {
        nick.getText();
        gratulacje.setText("GRATULACJE");
        wynikTimer = Main.wynik;
    }

    @FXML
    private void WinnerButton(ActionEvent event) throws IOException {
        int count = 0;
        while (nick.getText() == null || count < 3) {
            gratulacje.setText("Nie Podales Nicka");
            count++;
           // event.consume();
        }
        if (nick.getText() == null && count == 3) {
            gratulacje.setText("NIE To NIE FRAJERZE");
            count++;
          //  event.consume();
        } else if (nick.getText() != null || count == 4) {
            zapisz(new Winners(nick.getText(),wynikTimer).toString());
            System.out.println("Wracam do Menu  ");
            Parent menuAnchor = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene playScene = new Scene(menuAnchor);
            Stage okno = (Stage) ((Node) event.getSource()).getScene().getWindow();
            okno.setScene(playScene);
            okno.show();
        }else gratulacje.setText("asdsa");
    }

    public void zapisz(String winner) {
        try (FileOutputStream strumien = new FileOutputStream("E:\\Java JDK\\Puzzle\\Winners.txt", true)) {
            byte[] tb = winner.getBytes();
            strumien.write(tb);
        } catch (IOException e) {
        }
    }

}