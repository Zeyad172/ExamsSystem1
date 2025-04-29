module org.example.examssystem {
    requires MaterialFX;
    requires java.sql;
    requires mysql.connector.java;


    opens org.example.examssystem to javafx.fxml;
    exports org.example.examssystem;
}