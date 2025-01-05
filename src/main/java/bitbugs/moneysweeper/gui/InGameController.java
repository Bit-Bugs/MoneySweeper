package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.gui.dto.LoseDto;
import bitbugs.moneysweeper.gui.dto.MenuDto;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalTime;
import java.util.Stack;

public class InGameController {

    @FXML
    private Button highscore;

    @FXML
    private Button time;

    @FXML
    private Button flags;

    @FXML
    private StackPane gameboardContainer;

    @FXML
    public void initialize() {
        var sceneData = (MenuDto) SceneManager.getInstance().getSceneData().data();
        highscore.setText(String.valueOf(sceneData.highscore()));

        GridPane gameboard = new GridPane();
        gameboard.getStyleClass().add("gameboard");
        gameboardContainer.getChildren().add(gameboard);
        gameboard.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gameboard.setVgap(4);
        gameboard.setHgap(4);

        for (int x = 0; x < sceneData.fieldWidth(); x++) {
            for (int y = 0; y < sceneData.fieldHeight(); y++) {
                var fieldText = new Text("");
                fieldText.setFont(new Font("Helvetica", 15));
                fieldText.setWrappingWidth(20);
                fieldText.setTextAlignment(TextAlignment.CENTER);

                var field = new Button();
                field.setId(x + y + "");
                field.getStyleClass().add("field-button");
                field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                field.setGraphic(fieldText);
                field.setOnContextMenuRequested(event -> placeFlag(fieldText));

                GridPane.setHgrow(field, Priority.ALWAYS);
                GridPane.setVgrow(field, Priority.ALWAYS);

                gameboard.add(field, x, y);
            }
        }

        //Make columns & rows inside gridpane fill its parent
        for (int column = 0; column < sceneData.fieldWidth(); column++) {
            var columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            gameboard.getColumnConstraints().add(columnConstraints);
        }

        for (int row = 0; row < sceneData.fieldHeight(); row++) {
            var rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            gameboard.getRowConstraints().add(rowConstraints);
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

        flags.textProperty().addListener((observable, oldValue, newValue) -> {
            if(Integer.parseInt(flags.getText()) == sceneData.fieldHeight() * sceneData.fieldWidth()) {
                var loseDto = new LoseDto(time.getText());
                SceneManager.getInstance().setScene("lose.fxml", new SceneData<>(loseDto));
            }
        });
    }

    @FXML
    private void handleBackToMenuButtonClick(ActionEvent event) {
        SceneManager.getInstance().setScene("menu.fxml");
    }

    private String formatTime(long elapsedNanoSeconds) {
        LocalTime time = LocalTime.ofNanoOfDay(elapsedNanoSeconds);

        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }

    /**
     * Handle setting flag
     *
     * @param fieldText
     */
    private void placeFlag(Text fieldText) {
        if (fieldText.getText().isEmpty()) {
            fieldText.setText("🚨");
            flags.setText(Integer.parseInt(flags.getText()) + 1 + "");
        } else {
            fieldText.setText("");
            flags.setText(Integer.parseInt(flags.getText()) - 1 + "");
        }
    }
}
