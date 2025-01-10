package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.backend.ScoreboardDataHandling;
import bitbugs.moneysweeper.gui.dto.FinishDto;
import bitbugs.moneysweeper.gui.dto.ScoreboardEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WinController {
    @FXML
    private Button time;

    @FXML
    private TextField username;

    @FXML
    private void handleBackToMenuButtonClick(ActionEvent event) {
        var scoreboardEntry = new ScoreboardEntry(username.getText(), time.getText());
        ScoreboardDataHandling.save(scoreboardEntry);
        SceneManager.getInstance().setScene("menu.fxml");
    }

    @FXML
    private void initialize() {
        var sceneData = (FinishDto) SceneManager.getInstance().getSceneData().data();

        time.setText(sceneData.time());
    }
}
