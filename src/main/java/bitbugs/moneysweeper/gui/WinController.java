package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.backend.ScoreboardDataHandling;
import bitbugs.moneysweeper.gui.dto.FinishDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WinController {
    @FXML
    private Button time;

    @FXML
    private TextField username;

    private FinishDto sceneData;

    @FXML
    private void handleBackToMenuButtonClick(ActionEvent event) {
        System.out.println(username.getText() + " " + time.getText());
        ScoreboardDataHandling.save(username.getText(), time.getText(), sceneData.difficulty());
        SceneManager.getInstance().setScene("menu.fxml");
    }

    @FXML
    private void initialize() {
        sceneData = (FinishDto) SceneManager.getInstance().getSceneData().data();

        time.setText(sceneData.time());
    }
}
