module gbw.tdg.towerdefensegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens gbw.tdg.towerdefensegame to javafx.fxml;
    exports gbw.tdg.towerdefensegame;
}