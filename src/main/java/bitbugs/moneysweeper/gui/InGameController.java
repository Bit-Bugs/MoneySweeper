package bitbugs.moneysweeper.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class InGameController {

    @FXML
    private StackPane gameboardContainer;

    @FXML
    public void initialize() {
        var gameboard = new GridPane();
        gameboardContainer.getChildren().add(gameboard);
        System.out.println(SceneManager.getInstance().getSceneData());
    }

    @FXML
    private void handleBackToMenuButtonClick(ActionEvent event) {
        SceneManager.getInstance().setScene("menu.fxml");
    }
}
