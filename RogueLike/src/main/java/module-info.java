module gbw.roguelike {
    requires javafx.controls;
    requires javafx.fxml;


    opens gbw.roguelike to javafx.fxml;
    exports gbw.roguelike;
    exports gbw.roguelike.ui;
    opens gbw.roguelike.ui to javafx.fxml;
    exports gbw.roguelike.damagingSystem;
    opens gbw.roguelike.damagingSystem to javafx.fxml;
    exports gbw.roguelike.backend;
    opens gbw.roguelike.backend to javafx.fxml;
    exports gbw.roguelike.handlers;
    opens gbw.roguelike.handlers to javafx.fxml;
    exports gbw.roguelike.interfaces;
    opens gbw.roguelike.interfaces to javafx.fxml;
    exports gbw.roguelike.enums;
    opens gbw.roguelike.enums to javafx.fxml;
}