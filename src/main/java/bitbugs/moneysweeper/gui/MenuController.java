package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.gui.dto.MenuDto;
import bitbugs.moneysweeper.gui.dto.ScoreboardEntry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MenuController {
    @FXML
    private ToggleGroup difficulty;

    @FXML
    private ToggleButton customDifficulty;

    @FXML
    private ToggleButton difficultyEasy;

    @FXML
    private ToggleButton difficultyMid;

    @FXML
    private ToggleButton difficultyHard;

    @FXML
    private TextField bombs;

    @FXML
    private TextField fieldWidth;

    @FXML
    private TextField fieldHeight;

    @FXML
    private Button playButton;

    @FXML
    private ListView<ScoreboardEntry> scoreboard;

    private final ObservableList<ScoreboardEntry> scoreboardItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        difficultyEasy.setUserData(10);
        difficultyMid.setUserData(40);
        difficultyHard.setUserData(99);

        scoreboard.setItems(scoreboardItems);
        //set scoreboard for default selected gamemode (easy)
        loadScoreboard(Difficulty.EASY);

        // Automatically select the ToggleButton when bombs (custom difficulty) are entered
        bombs.focusedProperty().addListener((observable, oldValue, newValue) -> {
            customDifficulty.setSelected(true);
            fieldWidth.setDisable(false);
            fieldHeight.setDisable(false);
            playButton.setDisable(bombs.getText().isEmpty());
        });

        bombs.textProperty().addListener((observable, oldValue, newValue) -> {
            playButton.setDisable((newValue).isEmpty());
        });

        fieldWidth.textProperty().addListener((observable, oldValue, newValue) -> {
            playButton.setDisable((newValue).isEmpty());

        });

        fieldHeight.textProperty().addListener((observable, oldValue, newValue) -> {
            playButton.setDisable((newValue).isEmpty());
        });

        difficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (difficulty.getSelectedToggle() != customDifficulty) {
                bombs.setText("");
                fieldWidth.setDisable(true);
                fieldHeight.setDisable(true);
            }

            scoreboardItems.clear();
            if (difficulty.getSelectedToggle() == difficultyEasy) {
                fieldWidth.setText("8");
                fieldHeight.setText("8");

                loadScoreboard(Difficulty.EASY);
            } else if (difficulty.getSelectedToggle() == difficultyMid) {
                fieldWidth.setText("16");
                fieldHeight.setText("16");

                loadScoreboard(Difficulty.MID);
            } else if (difficulty.getSelectedToggle() == difficultyHard) {
                fieldWidth.setText("30");
                fieldHeight.setText("16");

                loadScoreboard(Difficulty.HARD);
            } else {
                fieldWidth.setText("8");
                fieldHeight.setText("8");

                loadScoreboard(Difficulty.CUSTOM);
            }

        });


        bombs.setTextFormatter(getNumberRangeFilter(0, 668, bombs));
        fieldWidth.setTextFormatter(getNumberRangeFilter(0, 30, fieldWidth));
        fieldHeight.setTextFormatter(getNumberRangeFilter(0, 24, fieldHeight));
    }

    @FXML
    public void handlePlayButtonClick(ActionEvent event) {
        var highscore = scoreboardItems.isEmpty() ? 0 : scoreboardItems.getFirst().score();
        var menuDto = new MenuDto(getBombs(), Integer.parseInt(fieldWidth.getText()),
                Integer.parseInt(fieldHeight.getText()), highscore);
        SceneManager.getInstance().setScene("in-game.fxml", new SceneData<>(menuDto));
    }

    private void loadScoreboard(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) {
            scoreboardItems.add(new ScoreboardEntry("ByteMe 🤖", 1));
            scoreboardItems.add(new ScoreboardEntry("MemoryLeakMaster5000", 2));
        } else if (difficulty == Difficulty.MID) {

        } else if (difficulty == Difficulty.HARD) {

        } else if (difficulty == Difficulty.CUSTOM) {
        }

    }

    private TextFormatter<String> getNumberRangeFilter(int from, int to, TextField currentValue) {
        return new TextFormatter<>(change -> {
            if (!change.getText().isEmpty() && change.getText().matches("[0-9]*")) {
                var number = Integer.parseInt(currentValue.getText() + change.getText());
                if (number >= from && number <= to) {
                    return change;
                }
            } else if (change.getText().matches("")) {
                return change;
            }
            return null;
        });
    }

    private int getBombs() {
        if (bombs.getText().isEmpty()) {
            return (int) difficulty.getSelectedToggle().getUserData();
        }
        return Integer.parseInt(bombs.getText());
    }
}