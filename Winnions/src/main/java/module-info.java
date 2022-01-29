module gbw.winnions.winnions {
    requires javafx.controls;
    requires javafx.fxml;


    opens gbw.winnions to javafx.fxml;
    exports gbw.winnions;
    exports gbw.winnions.domain;
    opens gbw.winnions.domain to javafx.fxml;
    exports gbw.winnions.presentation;
    opens gbw.winnions.presentation to javafx.fxml;
}