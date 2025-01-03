package bitbugs.moneysweeper;

import bitbugs.moneysweeper.gui.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MoneySweeperApplication extends Application {
    @Override
    public void start(Stage stage) {
        var sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(stage, "Money Swe3§€$eper");
        sceneManager.setScene("menu.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}