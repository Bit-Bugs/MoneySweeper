package bitbugs.moneysweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MoneySweeperApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        FXMLLoader fxmlLoader = new FXMLLoader(MoneySweeperApplication.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Money Swe3§€$eper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}