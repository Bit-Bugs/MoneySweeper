package bitbugs.moneysweeper.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance;
    private static SceneData<?> sceneData;
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

    public void setScene(String fxmlFile){
        this.setScene(fxmlFile, null);
    }

    public void setScene(String fxmlFile, SceneData<?> data) {
        try {
            sceneData = data;
            var fxmlLoader = new FXMLLoader(getClass().getResource("/bitbugs/moneysweeper/views/" + fxmlFile));
            var scene = new Scene(fxmlLoader.load(), 320, 240);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SceneData<?> getSceneData() {
        return sceneData;
    }
}
