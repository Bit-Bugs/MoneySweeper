package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.backend.ScoreboardDataHandling;
import bitbugs.moneysweeper.gui.dto.MenuDto;
import bitbugs.moneysweeper.gui.dto.ScoreboardEntry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Screen;

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
        difficultyEasy.setUserData(10);
        difficultyMid.setUserData(40);
        difficultyHard.setUserData(99);
//        difficultyEasy.setUserData(new GamemodeToggleDto(8,8,10));

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
            playButton.setDisable((newValue).isEmpty() || Integer.parseInt(newValue) < 1);

        });

        fieldHeight.textProperty().addListener((observable, oldValue, newValue) -> {
            playButton.setDisable((newValue).isEmpty() || Integer.parseInt(newValue) < 1);
        });

        difficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (difficulty.getSelectedToggle() != customDifficulty) {
                bombs.setText("");
                fieldWidth.setDisable(true);
                fieldHeight.setDisable(true);
                playButton.setDisable(false);
            }

//            fieldWidth.textProperty().bind();

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

            fieldHeight.textProperty().set("9");
        });

        bombs.setTextFormatter(getNumberRangeFilter(1, 668, bombs));
        fieldWidth.setTextFormatter(getNumberRangeFilter(2, 30, fieldWidth));
        fieldHeight.setTextFormatter(getNumberRangeFilter(2, 24, fieldHeight));
    }

    @FXML
    public void handlePlayButtonClick(ActionEvent event)
    {

        String difficultyValue = ((ToggleButton) difficulty.getSelectedToggle()).getText();
        if (difficultyValue =="")
        {
            difficultyValue ="CUSTOM";
        }
        Difficulty selectedDifficulty = (Difficulty) Difficulty.valueOf(difficultyValue.toUpperCase());

        var highscore = scoreboardItems.isEmpty() ? "0" : scoreboardItems.getFirst().time();
        var menuDto = new MenuDto(getBombs(), Integer.parseInt(fieldWidth.getText()),
                Integer.parseInt(fieldHeight.getText()), highscore, selectedDifficulty);
        SceneManager.getInstance().setScene("in-game.fxml", new SceneData<>(menuDto));

    }

    private void loadScoreboard(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) {
            ScoreboardEntry[] entries = ScoreboardDataHandling.loadScoreboard(Difficulty.EASY);
            scoreboardItems.addAll(Arrays.asList(entries));
        } else if (difficulty == Difficulty.MID) {
            ScoreboardEntry[] entries = ScoreboardDataHandling.loadScoreboard(Difficulty.MID);
            scoreboardItems.addAll(Arrays.asList(entries));
        } else if (difficulty == Difficulty.HARD) {
            ScoreboardEntry[] entries = ScoreboardDataHandling.loadScoreboard(Difficulty.HARD);
            scoreboardItems.addAll(Arrays.asList(entries));
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