package org.example.examssystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class HelloController {
    protected Parent root;
    protected Scene scene;
    protected  Stage stage;
    @FXML private ComboBox<String> courseComboBox;
    @FXML
    protected TextField idTextField;
    @FXML
    protected TextField passwordField;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Login Failed. Wrong ID or Password");
        alert.showAndWait();
    }
    public void showProfessorPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/ProfessorPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void auth(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        String id = this.idTextField.getText();
        String password = this.passwordField.getText();
        boolean authSuccessfull = false;
        System.out.println(id);
        System.out.println(password);
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
        Statement statement = connection.createStatement();
        ResultSet professorsData = statement.executeQuery("SELECT * FROM professors");
        while(professorsData.next()){
            if(Objects.equals(id, professorsData.getString("professorID"))&& Objects.equals(password, professorsData.getString("password"))){
                showProfessorPage(event);
                authSuccessfull=true;
            }
        }
        if(!authSuccessfull)showAlert();
    }
}


