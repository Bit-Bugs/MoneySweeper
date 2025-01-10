package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.gui.dto.FinishDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoseController {
    @FXML
    private Button time;

    @FXML
    private void handleBackToMenuButtonClick(ActionEvent event) {
        SceneManager.getInstance().setScene("menu.fxml");
    }

    @FXML
    private void initialize() {
        var sceneData = (FinishDto) SceneManager.getInstance().getSceneData().data();

        time.setText(sceneData.time());
    }
}
