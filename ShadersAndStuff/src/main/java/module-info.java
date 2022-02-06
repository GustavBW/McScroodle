module com.testing.shadersandstuff {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.testing.shadersandstuff to javafx.fxml;
    exports com.testing.shadersandstuff;
}