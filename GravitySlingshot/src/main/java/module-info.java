module gbw.gravityslingshot.gravityslingshot {
    requires javafx.controls;
    requires javafx.fxml;


    opens gbw.gravityslingshot.gravityslingshot to javafx.fxml;
    exports gbw.gravityslingshot.gravityslingshot;
}