package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HighScore {
    ArrayList<Winners> list;

    @FXML
    private AnchorPane wyniki;

    @FXML
    void initialize(){
        GridPane gPane = new GridPane();
        list = wczytaj();
        for (Winners win : list){
            int row =0;
            gPane.addColumn(0,new Label(win.id));
            gPane.addColumn(1,new Label("-------------------------------------------------"));
            gPane.addColumn(2,new Label(String.valueOf(win.wynik)));
            //gPane.add(new Label(win.id),0,row);
            //gPane.add(new Label(String.valueOf(win.wynik)),1,row);
            /*
            GridPane.setConstraints(new Label(win.id),0,row);
            GridPane.setConstraints(new Label(String.valueOf(win.wynik)),1,row);

             */
            row++;
        }
        wyniki.getChildren().add(gPane);
    }

    @FXML
    private void Scoreback(ActionEvent event) throws IOException {
        System.out.println("Wracam do Menu  ");
        Parent menuAnchor = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene playScene = new Scene(menuAnchor);
        Stage okno = (Stage) ((Node) event.getSource()).getScene().getWindow();
        okno.setScene(playScene);
        okno.show();
    }

    public ArrayList<Winners> wczytaj() {
        ArrayList<Winners> winList = new ArrayList<>();
        try (BufferedReader bRe = new BufferedReader(new FileReader("E:\\Java JDK\\Puzzle\\Winners.txt"));) {
            String linia = null;

            while ((linia = bRe.readLine()) != null) {
                String[] tabS = linia.split(" ");

                String id = tabS[0];
                int wynik = (int)Double.parseDouble(tabS[1]);

                winList.add(new Winners(id,wynik));

            }

        } catch (IOException e) {
        }
        return winList;
    }
}
