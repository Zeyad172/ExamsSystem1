package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SceneController {

    @FXML
    private Button backButton;

    private final String IMAGE_PATH = "C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg";

    @FXML
    public void handleBackButton(javafx.event.ActionEvent event) {
        try {
            // Load the main FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/examssystem/hello-view.fxml"));
            Parent root = loader.load();

            // Get the controller to ensure it's properly initialized
            HelloController controller = loader.getController();

            // Set up the background
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            setupSceneWithBackground(stage, root);

        } catch (IOException e) {
            System.err.println("Error returning to main scene: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to setup scene with background
    public static void setupSceneWithBackground(Stage stage, Parent root) {
        try {
            // Create and setup background
            ImageView background = new ImageView();
            File file = new File("C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg");

            if (file.exists()) {
                Image bgImage = new Image(file.toURI().toString());
                background.setImage(bgImage);
                background.setPreserveRatio(false);
                background.setSmooth(true);
                background.setOpacity(0.9);

                // Create layered pane for background + content
                StackPane layeredPane = new StackPane();
                layeredPane.getChildren().addAll(background, root);

                // Create scene and bind background size to scene size
                Scene scene = new Scene(layeredPane, 800, 600);
                background.fitWidthProperty().bind(scene.widthProperty());
                background.fitHeightProperty().bind(scene.heightProperty());

                // Set up stage
                stage.setTitle("Helwan's Exams System");
                stage.setScene(scene);
                stage.setMinWidth(600);
                stage.setMinHeight(400);

                System.out.println("Background set successfully on back navigation");
            } else {
                System.err.println("Background image file not found: " + file.getAbsolutePath());
                // Fallback to just showing the scene without background
                Scene scene = new Scene(root, 800, 600);
                stage.setScene(scene);
            }

        } catch (Exception e) {
            System.err.println("Failed to set up background during back navigation:");
            e.printStackTrace();

            // Fallback to just showing the scene without background
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
        }
    }
}