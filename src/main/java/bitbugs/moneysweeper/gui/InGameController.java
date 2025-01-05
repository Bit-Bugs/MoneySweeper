package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.gui.dto.MenuDto;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.time.LocalTime;

public class InGameController {

    @FXML
    private Button highscore;

    @FXML
    private Button time;

    @FXML
    private StackPane gameboardContainer;

    @FXML
    public void initialize() {
        var sceneData = (MenuDto) SceneManager.getInstance().getSceneData().data();
        highscore.setText(String.valueOf(sceneData.highscore()));

        GridPane gameboard = new GridPane();
        gameboard.getStyleClass().add("gameboard");
        gameboardContainer.getChildren().add(gameboard);
        gameboard.setGridLinesVisible(true);
        gameboard.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameboard.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gameboard.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        for (int x = 0; x < sceneData.fieldWidth(); x++) {
            for (int y = 0; y < sceneData.fieldHeight(); y++) {
                var field = new Button();
                field.setText("");
                field.setAlignment(Pos.CENTER);
                field.getStyleClass().add("field-button");

                gameboard.add(field, x, y);


            }
        }

        // init timer
        var startTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedNanoSeconds = (now - startTime);
                time.setText(formatTime(elapsedNanoSeconds));
            }
        };
        timer.start();
    }

    @FXML
    private void handleBackToMenuButtonClick(ActionEvent event) {
        SceneManager.getInstance().setScene("menu.fxml");
    }

    private String formatTime(long elapsedNanoSeconds) {
        LocalTime time = LocalTime.ofNanoOfDay(elapsedNanoSeconds);

        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }
}
