module org.example.examssystem {
    requires MaterialFX;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;


    opens org.example.examssystem to javafx.fxml;
    exports org.example.examssystem;
}