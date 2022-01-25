module gbw.winnions.winnions {
    requires javafx.controls;
    requires javafx.fxml;


    opens gbw.winnions.winnions to javafx.fxml;
    exports gbw.winnions.winnions;
}