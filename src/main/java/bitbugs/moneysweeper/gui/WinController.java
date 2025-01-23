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
        //Wenn man gewonnen hat, wird man nach Name gefragt. Der name mit der completion time wird dann fuer die Verarbeitung weitergegeben.
        ScoreboardDataHandling.save(username.getText(), time.getText(), sceneData.difficulty());
        SceneManager.getInstance().setScene("menu.fxml");
    }

    @FXML
    private void initialize() {
        sceneData = (FinishDto) SceneManager.getInstance().getSceneData().data();

        time.setText(sceneData.time());
    }
}
