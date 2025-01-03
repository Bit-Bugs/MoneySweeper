package bitbugs.moneysweeper.gui;

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
    private ListView<String> scoreboard;

    private final ObservableList<String> scoreboardItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        scoreboard.setItems(scoreboardItems);
        //set scoreboard for default selected gamemode (easy)
        scoreboardItems.add("ByteMe 🤖");
        scoreboardItems.add("MemoryLeakMaster5000");

        // Automatically select the ToggleButton when bombs (custom difficulty) are entered
        bombs.focusedProperty().addListener((observable, oldValue, newValue) -> {
            customDifficulty.setSelected(true);
        });

        difficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (difficulty.getSelectedToggle() != customDifficulty) {
                bombs.setText("");
            }

            //Update Scoreboard here
            scoreboardItems.clear();
            if (difficulty.getSelectedToggle() == difficultyEasy) {
                scoreboardItems.add("ByteMe 🤖");
                scoreboardItems.add("MemoryLeakMaster5000");
            }
        });
    }

    @FXML
    public void handlePlayButtonClick(ActionEvent event) {
        SceneManager.getInstance().setScene("in-game.fxml");
    }
}