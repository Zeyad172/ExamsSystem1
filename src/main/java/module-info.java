module org.example.examssystem {
    requires MaterialFX;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens org.example.examssystem to javafx.fxml;
    exports org.example.examssystem;
}