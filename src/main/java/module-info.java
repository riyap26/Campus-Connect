module com.example.campusconnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.campusconnect to javafx.fxml;
    exports com.example.campusconnect;
}