import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class GameOfLife extends Application {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int BOARD_SIZE = 320;

    private Map<String, StackPane> boardMap = new HashMap<>();
    private Board board = new Board(BOARD_SIZE / 10);

    @Override
    public void start(Stage primaryStage) {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, (EventHandler) event -> iterateBoard()), new KeyFrame(Duration.millis(100)));

        timeline.setCycleCount(Timeline.INDEFINITE);

        board.initBoard(0.4);

        Pane root = new Pane();
        Scene scene = new Scene(root, BOARD_SIZE, BOARD_SIZE);
        scene.getStylesheets().addAll("gameStyles.css");

        for (int x = 0; x < BOARD_SIZE; x = x + WIDTH) {
            for (int y = 0; y < BOARD_SIZE; y = y + HEIGHT) {
                StackPane cell = StackPaneBuilder.create().layoutX(x).layoutY(y).prefHeight(HEIGHT).prefWidth(WIDTH).styleClass("dead-cell").build();
                root.getChildren().add(cell);
                boardMap.put(x + " " + y, cell);
            }
        }

        primaryStage.setTitle("Game of Life ting");
        primaryStage.setScene(scene);
        primaryStage.show();

        timeline.play();

    }

    private void iterateBoard() {
        board.nextPopulation();
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                StackPane pane = boardMap.get(x * WIDTH + " " + y * HEIGHT);
                pane.getStyleClass().clear();
                if (board.getField(x, y) == 1) pane.getStyleClass().add("alive-cell");
                else pane.getStyleClass().add("dead-cell");
            }
        }
    }

    public static void main(String[] args) { launch(args); }

}
