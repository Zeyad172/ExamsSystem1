module org.example.examssystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.examssystem to javafx.fxml;
    exports org.example.examssystem;
}