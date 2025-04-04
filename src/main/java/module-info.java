module com.example.recepter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires layout;
    requires kernel;


    opens com.example.recepter to javafx.fxml;
    exports com.example.recepter;
}