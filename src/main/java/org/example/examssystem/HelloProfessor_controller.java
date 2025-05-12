package org.example.examssystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class HelloProfessor_controller {
        protected Parent root;
        protected Stage stage;
        protected Scene scene;
        @FXML private Label drNameLabel;
        @FXML private Label usernameLabel;
        @FXML private Label phoneLabel;
        @FXML private Label emailLabel;
        @FXML private Label subjectsLabel;
        public static boolean test = false;

    static protected String url = "jdbc:mysql://192.168.252.15:3306/nourdb";
    static protected String user = "root";
    static protected String password = "Elnaggar2@";

        public void initialize() {
            loadDoctorInfo();
        }

        private void loadDoctorInfo() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, "Elnaggar2@");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM nourdb.professors");

                if (rs.next()) {
                    drNameLabel.setText("Dr. Name: " + rs.getString(2));
                    usernameLabel.setText("Username: " + rs.getString(1));
//                    phoneLabel.setText("Phone Number: " + rs.getString("phone_Number"));
//                    emailLabel.setText("Email: " + rs.getString("E-mail"));
                    subjectsLabel.setText("Subjects: " + rs.getString(4)+" - "+rs.getString(5) +" - "+rs.getString(6));
                }
                rs.close();
                stmt.close();
                conn.close();

            } catch (Exception e) {
                System.out.println("Error loading doctor info: " + e.getMessage());
            }
        }

    public void Set_Exam(javafx.event.ActionEvent actionEvent) {
            this.test = false;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/Set_Exam.fxml"));

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            ImageView background = new ImageView();
            try {
                String imagePath = "/org/example/examssystem/unnamed.jpg";
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

    public void Edit_Exam(ActionEvent actionEvent) {
            this.test = true;

    }

    public void Exam_result(ActionEvent actionEvent) throws IOException {
            root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/PastResult.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setFullScreen(false);
            stage.show();
    }
}

