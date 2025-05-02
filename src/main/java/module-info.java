module org.example.examssystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires jdk.jdi;
    requires java.desktop;

    opens org.example.examssystem to javafx.fxml;
    exports org.example.examssystem;

}