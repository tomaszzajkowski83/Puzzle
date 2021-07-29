package sample;

import javafx.animation.PathTransition;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javax.xml.stream.EventFilter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class PlayGroundController {

    FXMLLoader loader = new FXMLLoader();
    int czas = 0;
    Label zegar = new Label(String.valueOf(czas));
    // IntegerProperty czas = new SimpleIntegerProperty(0);
    Timer myTimer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //czas++;
            Main.wynik++;
            System.out.println(Main.wynik);
            // zegar.setText(String.valueOf(czas));
        }
    };


    private Image image = new Image("sample\\Slayer.jpg", 800, 600, false, true);

    //private static double SCENE_WIDTH = 950;
    //private static double SCENE_HEIGHT = 600;

    public static int TILE_ROW_COUNT = 2;
    public static int TILE_COLUMN_COUNT = 2;
    public static int TILE_SIZE_X;
    public static int TILE_SIZE_Y;
    // public static double TILE_SIZE = 120;

    //public static double offsetX = (SCENE_WIDTH - TILE_ROW_COUNT * TILE_SIZE) / 2;
    //public static double offsetY = (SCENE_HEIGHT - TILE_COLUMN_COUNT * TILE_SIZE) / 2;

    List<Cell> cells = new ArrayList<>();
    @FXML
    private ImageView gra;

    @FXML
    private void Gameback(ActionEvent event) throws IOException {
        System.out.println("Wracam do Menu  ");
        Parent menuAnchor = loader.load(getClass().getResource("Menu.fxml"));
        Scene playScene = new Scene(menuAnchor);
        Stage okno = (Stage) ((Node) event.getSource()).getScene().getWindow();
        okno.setScene(playScene);
        okno.show();

    }

    @FXML
    void play(ActionEvent event) throws IOException {

        VBox vBox = new VBox();
        AnchorPane timerShow = new AnchorPane();
        // Label zegar = new Label(String.valueOf(czas));
        zegar.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(zegar, 0.0);
        timerShow.getChildren().add(zegar);
        //timer.textProperty().bind(czas.asString());
        zegar.fontProperty().setValue(new Font(24));

      /*  czas.addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //timer.setText(newValue.toString());
            }
        });

       */
        // odliczanie();
        myTimer.scheduleAtFixedRate(task, 1000, 1000);
        // create grid
        TILE_SIZE_X = 950 / TILE_ROW_COUNT;
        TILE_SIZE_Y = 600 / TILE_COLUMN_COUNT;

        for (int x = 0; x < TILE_ROW_COUNT; x++) {
            for (int y = 0; y < TILE_COLUMN_COUNT; y++) {

                // create tile
                ImageView tile = new ImageView(image);
                Rectangle2D rect = new Rectangle2D(TILE_SIZE_X * x, TILE_SIZE_Y * y, TILE_SIZE_X, TILE_SIZE_Y);
                tile.setViewport(rect);

                // consider empty cell, let it remain empty
                if (x == (TILE_ROW_COUNT - 1) && y == (TILE_COLUMN_COUNT - 1)) {
                    tile = null;
                }

                cells.add(new Cell(x, y, tile));
            }
        }

        // shuffle cells
        shuffle();

        // create playfield

        AnchorPane pane = new AnchorPane();

        // put tiles on playfield, assign event handler
        for (int i = 0; i < cells.size(); i++) {

            Cell cell = cells.get(i);

            ImageView imageView = cell.getImageView();

            // consider empty cell
            if (imageView == null)
                continue;

            // click-handler: swap tiles, check if puzzle is solved
            imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

                moveCell((ImageView) mouseEvent.getSource());

            });

            // position images on scene
            imageView.relocate(cell.getLayoutX(), cell.getLayoutY());

            pane.getChildren().add(cell.getImageView());
        }
        vBox.getChildren().addAll(zegar, pane);

        Scene scene = new Scene(vBox);
        Stage okno = (Stage) ((Node) event.getSource()).getScene().getWindow();
        okno.setScene(scene);
        okno.show();
        EventHandler<WindowEvent> windowFilter = e-> { myTimer.cancel();};
        okno.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,windowFilter);
        //okno.setOnCloseRequest(windowHandler);
    }


    /**
     * Swap images of cells randomly
     */
    public void shuffle() {

        Random rnd = new Random();

        for (int i = 0; i < 1000; i++) {

            int a = rnd.nextInt(cells.size());
            int b = rnd.nextInt(cells.size());

            if (a == b)
                continue;

            // skip bottom right cell swap, we want the empty cell to remain there
            if (cells.get(a).isEmpty() || cells.get(b).isEmpty())
                continue;

            swap(cells.get(a), cells.get(b));

        }

    }

    public void swap(Cell cellA, Cell cellB) {

        ImageView tmp = cellA.getImageView();
        cellA.setImageView(cellB.getImageView());
        cellB.setImageView(tmp);

    }

    public boolean checkSolved() {

        boolean allSolved = true;

        for (Cell cell : cells) {

            if (!cell.isSolved()) {
                allSolved = false;
                break;
            }
        }

        System.out.println("Solved: " + allSolved);

        return allSolved;
    }

    public void moveCell(Node node) {

        // get current cell using the selected node (imageview)
        Cell currentCell = null;
        for (Cell tmpCell : cells) {
            if (tmpCell.getImageView() == node) {
                currentCell = tmpCell;
                break;
            }
        }

        if (currentCell == null)
            return;

        // get empty cell
        Cell emptyCell = null;

        for (Cell tmpCell : cells) {
            if (tmpCell.isEmpty()) {
                emptyCell = tmpCell;
                break;
            }
        }

        if (emptyCell == null)
            return;

        // check if cells are swappable: neighbor distance either x or y must be 1 for a valid move
        int steps = Math.abs(currentCell.x - emptyCell.x) + Math.abs(currentCell.y - emptyCell.y);
        if (steps != 1)
            return;

        System.out.println("Transition: " + currentCell + " -> " + emptyCell);

        // cells are swappable => create path transition
        Path path = new Path();
        path.getElements().add(new MoveToAbs(currentCell.getImageView(), currentCell.getLayoutX(), currentCell.getLayoutY()));
        path.getElements().add(new LineToAbs(currentCell.getImageView(), emptyCell.getLayoutX(), emptyCell.getLayoutY()));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(50));
        pathTransition.setNode(currentCell.getImageView());
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        final Cell cellA = currentCell;
        final Cell cellB = emptyCell;
        pathTransition.setOnFinished(actionEvent -> {

            swap(cellA, cellB);

            if (checkSolved()) {
                myTimer.cancel();
                //Main.wynik = czas;
                Parent WinnerAnchor = null;
                try {
                    WinnerAnchor = loader.load(getClass().getResource("Winner.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene playScene = new Scene(WinnerAnchor);
                Stage okno = (Stage) node.getScene().getWindow();
                //Stage okno = (Stage) ((Node) event.getSource()).getScene().getWindow();
                okno.setScene(playScene);
                okno.show();


            }
            //checkSolved();

        });

        pathTransition.play();

    }

    static class Cell {

        int x;
        int y;

        ImageView initialImageView;
        ImageView currentImageView;

        public Cell(int x, int y, ImageView initialImageView) {
            //super();
            this.x = x;
            this.y = y;
            this.initialImageView = initialImageView;
            this.currentImageView = initialImageView;
        }

        public double getLayoutX() {
            return x * TILE_SIZE_X; //+ offsetX;
        }

        public double getLayoutY() {
            return y * TILE_SIZE_Y; // + offsetY;
        }

        public ImageView getImageView() {
            return currentImageView;
        }

        public void setImageView(ImageView imageView) {
            this.currentImageView = imageView;
        }

        public boolean isEmpty() {
            return currentImageView == null;
        }

        public boolean isSolved() {
            return this.initialImageView == currentImageView;
        }

        public String toString() {
            return "[" + x + "," + y + "]";
        }

    }

    // absolute (layoutX/Y) transitions using the pathtransition for MoveTo
    public static class MoveToAbs extends MoveTo {

        public MoveToAbs(Node node) {
            super(node.getLayoutBounds().getWidth() / 2, node.getLayoutBounds().getHeight() / 2);
        }

        public MoveToAbs(Node node, double x, double y) {
            super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2, y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
        }

    }

    // absolute (layoutX/Y) transitions using the pathtransition for LineTo
    public static class LineToAbs extends LineTo {

        public LineToAbs(Node node, double x, double y) {
            super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2, y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
        }
    }

  /*  public void odliczanie() {
        Platform.runLater(() -> {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    czas.set(czas.intValue() + 1);
                    System.out.println(czas.toString());
                }
            }, 1000, 1000);
        });

    }

   */
}




