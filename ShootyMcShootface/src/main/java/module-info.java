module com.shootymcshootface.shootymcshootface {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.shootymcshootface.shootymcshootface to javafx.fxml;
    exports com.shootymcshootface.shootymcshootface;
}