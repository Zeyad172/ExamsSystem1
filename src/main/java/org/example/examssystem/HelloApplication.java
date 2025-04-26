package org.example.examssystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;

public class HelloApplication extends Application {

    private ImageView background;
    private Scene scene; // Declare scene as global variable

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Load FXML interface
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/GUI.fxml"));

            // 2. Set up responsive background image
            background = new ImageView();
            try {
                Image bgImage = new Image(new File("C:\\Users\\Noor\\IdeaProjects\\ExamsSystem2\\src\\main\\java\\org\\example\\examssystem\\unnamed.jpg").toURI().toString());
                background.setImage(bgImage);
                background.setPreserveRatio(false);
                background.setSmooth(true);
            } catch (Exception e) {
                System.err.println("Error loading background image: " + e.getMessage());
            }

            // 3. Set application icon
            try {
                Image appIcon = new Image(new File("C:\\Users\\Noor\\IdeaProjects\\ExamsSystem2\\src\\main\\java\\org\\example\\examssystem\\images.png").toURI().toString());
                primaryStage.getIcons().add(appIcon);
            } catch (Exception e) {
                System.err.println("Error loading application icon: " + e.getMessage());
            }

            // 4. Create layered interface
            StackPane layeredPane = new StackPane();
            layeredPane.getChildren().addAll(background, root);

            // 5. Initialize scene (global variable)
            scene = new Scene(layeredPane, 700, 500);
            System.out.println("NOUR WAS HERE");
            System.out.println("NOUR WAS HERE2");
            // 6. Load CSS
            try {
                String css = getClass().getResource("/org/example/examssystem/csstest.css").toExternalForm();
                scene.getStylesheets().add(css);
            } catch (Exception e) {
                System.err.println("Error loading CSS file: " + e.getMessage());
            }

            // 7. Set up responsive behavior
            scene.widthProperty().addListener((obs, oldVal, newVal) ->
                    resizeBackground(newVal.doubleValue(), scene.getHeight()));
            scene.heightProperty().addListener((obs, oldVal, newVal) ->
                    resizeBackground(scene.getWidth(), newVal.doubleValue()));

            // 8. Configure and show stage
            primaryStage.setTitle("Helwan Exams System");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setFullScreenExitHint("Press ESC to exit fullscreen");
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Application startup failed:");
            e.printStackTrace();
        }
    }

    private void resizeBackground(double width, double height) {
        if (background != null && background.getImage() != null) {
            background.setFitWidth(width);
            background.setFitHeight(height);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
