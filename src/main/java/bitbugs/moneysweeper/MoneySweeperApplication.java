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

        // Prüfen, ob der Pfad zur Symboldatei gültig ist
        if (iconPath != null) {
            stage.getIcons().add(new Image(iconPath.toString()));

            // Dock-Symbol für macOS setzen, falls unterstützt
            if (Taskbar.isTaskbarSupported()) {
                try {
                    final Taskbar taskbar = Taskbar.getTaskbar();

                    // Bild für das Dock-Symbol aus der Symboldatei erstellen
                    var image = new ImageIcon(iconPath).getImage();

                    // Prüfen, ob das Ändern des Dock-Symbols unterstützt wird
                    if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                        taskbar.setIconImage(image); // Dock-Symbol setzen
                    }
                } catch (UnsupportedOperationException | SecurityException e) {
                    // Fehler beim Ändern des Dock-Symbols ausgeben
                    System.err.println("Taskbar icon setting not supported: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}