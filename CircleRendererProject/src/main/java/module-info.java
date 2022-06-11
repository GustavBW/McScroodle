module gbw.circlerenderer {
    requires javafx.controls;
    requires javafx.fxml;


    opens gbw.circlerenderer to javafx.fxml;
    exports gbw.circlerenderer;
}