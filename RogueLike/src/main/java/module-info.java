module gbw.roguelike {
    requires javafx.controls;
    requires javafx.fxml;


    opens gbw.roguelike to javafx.fxml;
    exports gbw.roguelike;
}