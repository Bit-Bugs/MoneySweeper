package bitbugs.moneysweeper;

import bitbugs.moneysweeper.gui.SceneManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.ImageIcon;
import java.awt.Taskbar;

public class MoneySweeperApplication extends Application {
    @Override
    public void start(Stage stage) {
        var sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(stage, "Money Swe3§€$eper");
        sceneManager.setScene("menu.fxml");
        var iconPath = getClass().getResource("/bitbugs/moneysweeper/icon.png");

        if (iconPath != null) {
            stage.getIcons().add(new Image(iconPath.toString()));

            //dock icon for macOS
            final Taskbar taskbar = Taskbar.getTaskbar();
            var image = new ImageIcon(iconPath).getImage();
            taskbar.setIconImage(image);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}