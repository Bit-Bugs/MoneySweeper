module bitbugs.moneysweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens bitbugs.moneysweeper.gui to javafx.fxml;
    exports bitbugs.moneysweeper;
}