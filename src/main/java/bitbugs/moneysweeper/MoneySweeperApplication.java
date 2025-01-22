package bitbugs.moneysweeper;

import bitbugs.moneysweeper.backend.ScoreboardDataHandling;
import bitbugs.moneysweeper.gui.SceneManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.ImageIcon;
import java.awt.Taskbar;

public class MoneySweeperApplication extends Application {
    @Override
    public void start(Stage stage) {
        ScoreboardDataHandling.loadFromFile();
        var sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(stage, "Money Swe3§€$eper");
        sceneManager.setScene("menu.fxml");
        var iconPath = getClass().getResource("/bitbugs/moneysweeper/icon.png");

        // check if icon path is valid
        if (iconPath != null) {
            stage.getIcons().add(new Image(iconPath.toString()));

            // check if taskbar api is supported
            if (Taskbar.isTaskbarSupported()) {
                try {
                    final Taskbar taskbar = Taskbar.getTaskbar();
                    var image = new ImageIcon(iconPath).getImage();

                    if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                        taskbar.setIconImage(image);
                    }
                } catch (UnsupportedOperationException | SecurityException e) {
                    System.err.println("Taskbar icon setting not supported: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}