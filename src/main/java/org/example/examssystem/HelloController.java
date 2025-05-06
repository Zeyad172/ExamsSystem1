package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML private Label Alert;
    @FXML private ComboBox<String> CategoryComboBox;
    @FXML private Label welcomeText;
    @FXML private TextField nameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private AnchorPane mainPane;

    private final String IMAGE_PATH = "C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg";

    @FXML
    public void initializeinfo() throws SQLException {
        DatabaseConn DB = new DatabaseConn();
        DB.createConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the ComboBox here
        CategoryComboBox.getItems().addAll("Professor", "Student", "Admin");
        CategoryComboBox.setValue("Enter Your Category");

        try {
            // Use File to properly construct URI for local file
            File file = new File(IMAGE_PATH);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());

                BackgroundImage backgroundImage = new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(1.0, 1.0, true, true, false, false)
                );

                Background background = new Background(backgroundImage);
                mainPane.setBackground(background);
                System.out.println("Background set successfully with image: " + file.getAbsolutePath());
            } else {
                System.err.println("Image file not found: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Failed to set background image:");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCreateButtonsubmeter() throws SQLException {
        DatabaseConn DB = new DatabaseConn();
        DB.createConnection();
        String name = nameTextField.getText();
        String password = passwordTextField.getText();
        DB.getTable(name,password);
    }

    @FXML
    public void Submit_Button(javafx.event.ActionEvent actionEvent) {
        if (CategoryComboBox.getValue().equals("Professor" )) {
            loadNewScene(actionEvent, "/org/example/examssystem/Set_Exam.fxml");
        }
        else if (CategoryComboBox.getValue().equals("Admin" )) {
            loadNewScene(actionEvent, "/org/example/examssystem/Admin_Page.fxml");
        }
        else if(CategoryComboBox.getValue().equals("Student" )){
            loadNewScene(actionEvent, "/org/example/examssystem/frist_stage.fxml");
        }
        else {
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("CHOOSE YOUR CATEGORY!!!!!");
            alert.showAndWait();
        }
    }

    // Refactored to eliminate duplicate code and use the common helper from SceneController
    private void loadNewScene(javafx.event.ActionEvent actionEvent, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Use the common helper method to set up the scene with background
            SceneController.setupSceneWithBackground(stage, root);

            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}