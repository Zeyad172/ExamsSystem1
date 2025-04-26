package org.example.examssystem;

//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}

//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.net.URL;
//
//public class HelloApplication extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            // ✅ Corrected FXML filename
//            URL fxmlUrl = getClass().getResource("testmotion.fxml");
//            if (fxmlUrl == null) {
//                throw new RuntimeException("FXML file not found. Expected location: "
//                        + getClass().getResource("").getPath() + "testmotion.fxml");
//            }
//
//            System.out.println("Loading FXML from: " + fxmlUrl);
//            Parent root = FXMLLoader.load(fxmlUrl);
//
//            Scene scene = new Scene(root, 600, 400);
//            primaryStage.setTitle("Exam System Application");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//
//        } catch (Exception e) {
//            System.err.println("\n--- APPLICATION FAILED TO START ---");
//            System.err.println("Possible solutions:");
//            System.err.println("1. Ensure testmotion.fxml is in your resources folder");
//            System.err.println("2. Verify filename spelling (case-sensitive)");
//            System.err.println("3. Clean and rebuild your project\n");
//
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }
//
//    public static void main(String[] args) {
//        System.out.println("Starting application...");
//        System.out.println("Working directory: " + System.getProperty("user.dir"));
//
//        launch(args);
//    }
//}
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
                Image bgImage = new Image(new File("D:\\EL ZOOZ JAVA\\Exams System\\src\\main\\java\\org\\example\\examssystem\\unnamed.jpg").toURI().toString());
                background.setImage(bgImage);
                background.setPreserveRatio(false);
                background.setSmooth(true);
            } catch (Exception e) {
                System.err.println("Error loading background image: " + e.getMessage());
            }

            // 3. Set application icon
            try {
                Image appIcon = new Image(new File("D:\\EL ZOOZ JAVA\\Exams System\\src\\main\\java\\org\\example\\examssystem\\images.png").toURI().toString());
                primaryStage.getIcons().add(appIcon);
            } catch (Exception e) {
                System.err.println("Error loading application icon: " + e.getMessage());
            }

            // 4. Create layered interface
            StackPane layeredPane = new StackPane();
            layeredPane.getChildren().addAll(background, root);

            // 5. Initialize scene (global variable)
            scene = new Scene(layeredPane, 700, 500);

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