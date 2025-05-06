package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class HelloController implements Initializable {
    @FXML private Label Alert;
    @FXML private ComboBox<String> CategoryComboBox;
    @FXML
    private Label welcomeText;
    protected TextField nameTextField;
    private PasswordField passwordTextField;

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
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/Professor.fxml"));

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                ImageView background = new ImageView();
                try {
                    String imagePath = "C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg";
                    System.out.println("Loading background from: " + imagePath);

                    Image bgImage = new Image(new File(imagePath).toURI().toString());
                    background.setImage(bgImage);
                    background.setPreserveRatio(false);
                    background.setSmooth(true);
                    background.setOpacity(0.9);

                    System.out.println("Background loaded successfully. Dimensions: " +
                            bgImage.getWidth() + "x" + bgImage.getHeight());
                } catch (Exception e) {
                    System.err.println("Failed to load background:");
                    e.printStackTrace();
                }
                StackPane layeredPane = new StackPane();
                layeredPane.getChildren().addAll(background, root);
                Scene scene = new Scene(layeredPane, 800, 600);
                background.fitWidthProperty().bind(scene.widthProperty());
                background.fitHeightProperty().bind(scene.heightProperty());

                stage.setTitle("Helwan's Exams System");
                stage.setScene(scene);
                stage.setMinWidth(600);
                stage.setMinHeight(400);
                stage.show();
            } catch (IOException e) {
                // Handle any errors loading the FXML
                System.err.println("Error loading Scene2.fxml: " + e.getMessage());
                e.printStackTrace();

            }
            if (CategoryComboBox.getValue().equals("Professor" )) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/Set_Exam.fxml"));

                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                    ImageView background = new ImageView();
                    try {
                        String imagePath = "C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg";
                        System.out.println("Loading background from: " + imagePath);

                        Image bgImage = new Image(new File(imagePath).toURI().toString());
                        background.setImage(bgImage);
                        background.setPreserveRatio(false);
                        background.setSmooth(true);
                        background.setOpacity(0.9);

                        System.out.println("Background loaded successfully. Dimensions: " +
                                bgImage.getWidth() + "x" + bgImage.getHeight());
                    } catch (Exception e) {
                        System.err.println("Failed to load background:");
                        e.printStackTrace();
                    }
                    StackPane layeredPane = new StackPane();
                    layeredPane.getChildren().addAll(background, root);
                    Scene scene = new Scene(layeredPane, 800, 600);
                    background.fitWidthProperty().bind(scene.widthProperty());
                    background.fitHeightProperty().bind(scene.heightProperty());

                    stage.setTitle("Helwan's Exams System");
                    stage.setScene(scene);
                    stage.setMinWidth(600);
                    stage.setMinHeight(400);
                    stage.show();
                } catch (IOException e) {
                    // Handle any errors loading the FXML
                    System.err.println("Error loading Scene2.fxml: " + e.getMessage());
                    e.printStackTrace();

                }
            }
        }
        else if (CategoryComboBox.getValue().equals("Admin" )) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/Admin_Page.fxml"));

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                ImageView background = new ImageView();
                try {
                    String imagePath = "C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg";
                    System.out.println("Loading background from: " + imagePath);

                    Image bgImage = new Image(new File(imagePath).toURI().toString());
                    background.setImage(bgImage);
                    background.setPreserveRatio(false);
                    background.setSmooth(true);
                    background.setOpacity(0.9);

                    System.out.println("Background loaded successfully. Dimensions: " +
                            bgImage.getWidth() + "x" + bgImage.getHeight());
                } catch (Exception e) {
                    System.err.println("Failed to load background:");
                    e.printStackTrace();
                }
                StackPane layeredPane = new StackPane();
                layeredPane.getChildren().addAll(background, root);
                Scene scene = new Scene(layeredPane, 800, 600);
                background.fitWidthProperty().bind(scene.widthProperty());
                background.fitHeightProperty().bind(scene.heightProperty());

                stage.setTitle("Helwan's Exams System");
                stage.setScene(scene);
                stage.setMinWidth(600);
                stage.setMinHeight(400);
                stage.show();
            } catch (IOException e) {
                // Handle any errors loading the FXML
                System.err.println("Error loading Scene2.fxml: " + e.getMessage());
                e.printStackTrace();

            }
        }
        else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/frist_stage.fxml"));

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                ImageView background = new ImageView();
                try {
                    String imagePath = "C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg";
                    System.out.println("Loading background from: " + imagePath);

                    Image bgImage = new Image(new File(imagePath).toURI().toString());
                    background.setImage(bgImage);
                    background.setPreserveRatio(false);
                    background.setSmooth(true);
                    background.setOpacity(0.9);

                    System.out.println("Background loaded successfully. Dimensions: " +
                            bgImage.getWidth() + "x" + bgImage.getHeight());
                } catch (Exception e) {
                    System.err.println("Failed to load background:");
                    e.printStackTrace();
                }
                StackPane layeredPane = new StackPane();
                layeredPane.getChildren().addAll(background, root);
                Scene scene = new Scene(layeredPane, 800, 600);
                background.fitWidthProperty().bind(scene.widthProperty());
                background.fitHeightProperty().bind(scene.heightProperty());

                stage.setTitle("Helwan's Exams System");
                stage.setScene(scene);
                stage.setMinWidth(600);
                stage.setMinHeight(400);
                stage.show();
            } catch (IOException e) {
                // Handle any errors loading the FXML
                System.err.println("Error loading Scene2.fxml: " + e.getMessage());
                e.printStackTrace();

            }
        }
    }


}
