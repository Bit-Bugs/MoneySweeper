package bitbugs.moneysweeper.gui;

import bitbugs.moneysweeper.gui.dto.LoseDto;
import bitbugs.moneysweeper.gui.dto.MenuDto;
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
        var sceneData = (LoseDto) SceneManager.getInstance().getSceneData().data();

        time.setText(sceneData.time());
    }
}
