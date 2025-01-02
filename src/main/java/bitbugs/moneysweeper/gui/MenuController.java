package bitbugs.moneysweeper.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class MenuController {
    @FXML
    private ToggleGroup difficulty;

    @FXML
    private ToggleButton customDifficulty;

    @FXML
    private TextField bombs;

    @FXML
    private TextField fieldWidth;

    @FXML
    private TextField fieldHeight;

    @FXML
    private Button playButton;

    @FXML
    public void initialize() {
        // Automatically select the ToggleButton when bombs (custom difficulty) are entered
        bombs.focusedProperty().addListener((observable, oldValue, newValue) -> {
            customDifficulty.setSelected(true);
        });

        difficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(difficulty.getSelectedToggle() != customDifficulty) {
                bombs.setText("");
            }

            //Update Scoreboard here
        });
    }
}