package bitbugs.moneysweeper.gui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;

    // Private constructor for Singleton pattern
    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage, String title) {
        stage.setTitle(title);
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setWidth(1100);
        stage.setHeight(700);

        this.primaryStage = stage;
    }

    public void setScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bitbugs/moneysweeper/views/" + fxmlFile));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
