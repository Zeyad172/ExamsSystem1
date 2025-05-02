package org.example.examssystem;
// The Project Will bee Perfect Enshaa Allaah;
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

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Load FXML interface
           Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/GUI.fxml"));
           //Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/SetQuestions.fxml"));
           // Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/Set_Exam.fxml"));
           // 2. Set up background image (absolute path)
            ImageView background = new ImageView();
            try {
                String imagePath = "D:\\EL ZOOZ JAVA\\Exams System\\src\\main\\java\\org\\example\\examssystem\\unnamed.jpg";
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
            // 3. Create layered interface
            StackPane layeredPane = new StackPane();
            layeredPane.getChildren().addAll(background, root);

            // 4. Create scene with responsive background
            Scene scene = new Scene(layeredPane, 800, 600);
            background.fitWidthProperty().bind(scene.widthProperty());
            background.fitHeightProperty().bind(scene.heightProperty());
            
            // 5. Set application icon
            try {
                String iconPath = "D:\\EL ZOOZ JAVA\\Exams System\\src\\main\\java\\org\\example\\examssystem\\images.png";
                Image appIcon = new Image(new File(iconPath).toURI().toString());
                primaryStage.getIcons().add(appIcon);
                System.out.println("Application icon set successfully from: " + iconPath);
            } catch (Exception e) {
                System.err.println("Failed to load application icon:");
                e.printStackTrace();
            }
            // 6. Configure and show stage
            primaryStage.setTitle("Helwan's Exams System");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Application startup failed:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}