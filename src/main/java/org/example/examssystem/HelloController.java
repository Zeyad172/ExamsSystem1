package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML private ComboBox<String> CategoryComboBox;
    @FXML
    private Label welcomeText;
    protected TextField nameTextField;
    private PasswordField passwordTextField;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    public void initializeinfo() throws SQLException {
        DatabaseConn DB = new DatabaseConn();
        DB.createConnection();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the ComboBox here
        CategoryComboBox.getItems().addAll("Professor", "Student", "Admin");
        CategoryComboBox.setValue("Enter Your Category");
    }
    @FXML
    protected void onCreateButtonsubmeter() throws SQLException {
        DatabaseConn DB = new DatabaseConn();
        DB.createConnection();
        String name = nameTextField.getText();
        String password = passwordTextField.getText();
        DB.getTable(name,password);

    }
}
