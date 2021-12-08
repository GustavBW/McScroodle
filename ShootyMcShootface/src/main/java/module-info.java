module com.shootymcshootface.shootymcshootface {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.shootymcshootface.shootymcshootface to javafx.fxml;
    exports com.shootymcshootface.shootymcshootface;
    exports com.shootymcshootface.shootymcshootface.gameloop;
    opens com.shootymcshootface.shootymcshootface.gameloop to javafx.fxml;
    exports com.shootymcshootface.shootymcshootface.gameobject;
    opens com.shootymcshootface.shootymcshootface.gameobject to javafx.fxml;
}