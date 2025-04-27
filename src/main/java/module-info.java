module org.example.examssystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens org.example.examssystem to javafx.fxml;
    exports org.example.examssystem;
}