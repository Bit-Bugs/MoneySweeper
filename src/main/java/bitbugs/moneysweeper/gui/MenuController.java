package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.backend.Playground;
import bitbugs.moneysweeper.backend.ScoreboardDataHandling;
import bitbugs.moneysweeper.gui.dto.MenuDto;
import bitbugs.moneysweeper.gui.dto.ScoreboardEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;

public class MenuController {
    @FXML
    private ToggleGroup difficulty;

    @FXML
    private ToggleButton difficultyEasy, difficultyMid, difficultyHard, customDifficulty;

    @FXML
    private TextField bombs;

    @FXML
    private TextField fieldWidth, fieldHeight;

    @FXML
    private Button playButton;

    @FXML
    private ListView<ScoreboardEntry> scoreboard;

    private final ObservableList<ScoreboardEntry> scoreboardItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ScoreboardDataHandling.loadFromFile();
        difficultyEasy.setUserData(Difficulty.EASY);
        difficultyMid.setUserData(Difficulty.MID);
        difficultyHard.setUserData(Difficulty.HARD);
        customDifficulty.setUserData(Difficulty.CUSTOM);
        fieldWidth.setUserData(8);
        fieldHeight.setUserData(8);

        scoreboard.setItems(scoreboardItems);
        //set scoreboard for default selected gamemode (easy)
        loadScoreboard(Difficulty.EASY);

        // Automatically select the ToggleButton when bombs (custom difficulty) are entered
        bombs.focusedProperty().addListener((observable, oldValue, newValue) -> {
            customDifficulty.setSelected(true);
            fieldWidth.setDisable(false);
            fieldHeight.setDisable(false);
            handlePlayButtonAvailability();
        });

        bombs.textProperty().addListener((observable, oldValue, newValue) -> {
            handlePlayButtonAvailability();
        });

        fieldWidth.textProperty().addListener((observable, oldValue, newValue) -> {
            handlePlayButtonAvailability();
        });

        fieldHeight.textProperty().addListener((observable, oldValue, newValue) -> {
            handlePlayButtonAvailability();
        });

        difficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (difficulty.getSelectedToggle() != customDifficulty) {
                bombs.setText("");
                fieldWidth.setDisable(true);
                fieldHeight.setDisable(true);
                playButton.setDisable(false);
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
            } else if (difficulty.getSelectedToggle() == customDifficulty) {
                loadScoreboard(Difficulty.CUSTOM);
            }
        });

        bombs.setTextFormatter(getNumberRangeFilter(1, 668, bombs));
        fieldWidth.setTextFormatter(getNumberRangeFilter(1, 30, fieldWidth));
        fieldHeight.setTextFormatter(getNumberRangeFilter(1, 24, fieldHeight));
    }

    @FXML
    public void handlePlayButtonClick(ActionEvent event) {
        var highscore = scoreboardItems.isEmpty() ? "0" : scoreboardItems.getFirst().time();
        var selectedDifficulty = (Difficulty) difficulty.getSelectedToggle().getUserData();
        var playground = new Playground();

        if (selectedDifficulty == Difficulty.CUSTOM) {
            playground = new Playground(
                    selectedDifficulty,
                    new int[]{Integer.parseInt(fieldWidth.getText()), Integer.parseInt(fieldHeight.getText())},
                    (int) bombs.getUserData()
            );
        } else {
            playground = new Playground(selectedDifficulty);
        }

        var menuDto = new MenuDto(playground, highscore);
        SceneManager.getInstance().setScene("in-game.fxml", new SceneData<>(menuDto));
    }

    private void loadScoreboard(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) {
            ScoreboardEntry[] entries = ScoreboardDataHandling.loadScoreboard(Difficulty.EASY);
            scoreboardItems.clear();
            scoreboardItems.addAll(Arrays.asList(entries));
        } else if (difficulty == Difficulty.MID) {
            ScoreboardEntry[] entries = ScoreboardDataHandling.loadScoreboard(Difficulty.MID);
            scoreboardItems.addAll(Arrays.asList(entries));
        } else if (difficulty == Difficulty.HARD) {
            ScoreboardEntry[] entries = ScoreboardDataHandling.loadScoreboard(Difficulty.HARD);
            scoreboardItems.addAll(Arrays.asList(entries));
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

    private void handlePlayButtonAvailability(){
        var disabled = false;

        if (!bombs.getText().isEmpty() && !fieldWidth.getText().isEmpty() && !fieldHeight.getText().isEmpty()) {
            var newBombs = Integer.parseInt(bombs.getText());
            var width = Integer.parseInt(fieldWidth.getText());
            var height = Integer.parseInt(fieldHeight.getText());
            bombs.setUserData(newBombs);
            fieldWidth.setUserData(width);
            fieldHeight.setUserData(height);

            if(newBombs > width * height || width < 2 || height < 2){
                disabled = true;
            }
        } else {
            disabled = true;
        }

        playButton.setDisable(disabled);
    }
}