module gbw.tdg.towerdefensegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens gbw.tdg.towerdefensegame to javafx.fxml;
    exports gbw.tdg.towerdefensegame;
    exports gbw.tdg.towerdefensegame.UI;
    opens gbw.tdg.towerdefensegame.UI to javafx.fxml;
    exports gbw.tdg.towerdefensegame.UI.buttons;
    opens gbw.tdg.towerdefensegame.UI.buttons to javafx.fxml;
    exports gbw.tdg.towerdefensegame.UI.Screens;
    opens gbw.tdg.towerdefensegame.UI.Screens to javafx.fxml;
    exports gbw.tdg.towerdefensegame.handlers;
    opens gbw.tdg.towerdefensegame.handlers to javafx.fxml;
    exports gbw.tdg.towerdefensegame.enemies;
    opens gbw.tdg.towerdefensegame.enemies to javafx.fxml;
}