package bitbugs.moneysweeper.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InGameController {

    @FXML
    private void handleBackToMenuButtonClick(ActionEvent event) {
        SceneManager.getInstance().setScene("menu.fxml");
    }
}
