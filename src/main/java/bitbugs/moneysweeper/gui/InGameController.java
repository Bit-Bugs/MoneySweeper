package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.backend.Playground;
import bitbugs.moneysweeper.gui.dto.FinishDto;
import bitbugs.moneysweeper.gui.dto.MenuDto;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalTime;

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

        int[] playgroundSize = new int[]{sceneData.fieldWidth(), sceneData.fieldHeight()};
        Playground playground = sceneData.difficulty() == Difficulty.CUSTOM ? new Playground(sceneData.difficulty(), playgroundSize) : new Playground(sceneData.difficulty());

        // generate gameboard
        for (int x = 0; x < playground.getDifficultySize()[0]; x++) {
            for (int y = 0; y < playground.getDifficultySize()[1]; y++) {
                var fieldText = new Text("");
                fieldText.setFont(new Font("Helvetica", 15));
                fieldText.setWrappingWidth(20);
                fieldText.setTextAlignment(TextAlignment.CENTER);

                var field = new Button();
                field.setId(x + y + "");
                field.getStyleClass().add("field-button");
                field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                var finalX = x;
                var finalY = y;
                field.onMouseClickedProperty().addListener((observable, oldValue, newValue) -> {
                    var values = playground.calculateUncoverFields(finalX, finalY);

                });

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

        flags.setText(sceneData.bombs() + "");
        flags.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.parseInt(flags.getText()) == 0) {
                var hasWon = playground.checkIfWon();

                if (hasWon) {
                    var finishDto = new FinishDto(time.getText());
                    SceneManager.getInstance().setScene("win.fxml", new SceneData<>(finishDto));
                }
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
            flags.setText(Integer.parseInt(flags.getText()) - 1 + "");
        } else {
            fieldText.setText("");
            flags.setText(Integer.parseInt(flags.getText()) + 1 + "");
        }
    }
}
