module bitbugs.moneysweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens bitbugs.moneysweeper to javafx.fxml;
    exports bitbugs.moneysweeper;
    exports bitbugs.moneysweeper.gui;
    opens bitbugs.moneysweeper.gui to javafx.fxml;
}