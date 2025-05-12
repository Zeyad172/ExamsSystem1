package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class chooseSubjectController implements Initializable {
    @FXML
    Button subject1;
    @FXML
    Button subject2;
    @FXML
    Button subject3;
    @FXML
    Button subject4;
    @FXML
    Button subject5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.252.15:3306/nourdb","root","Elnaggar2@");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
