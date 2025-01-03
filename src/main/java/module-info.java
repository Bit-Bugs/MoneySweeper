module bitbugs.moneysweeper {
    requires javafx.controls;
    requires javafx.fxml;

    opens bitbugs.moneysweeper.gui to javafx.fxml;
    exports bitbugs.moneysweeper;
}