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

public class Admin_Page extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Admin_Page.fxml"));
        ImageView background = new ImageView();
        try {
            String imagePath = "C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg";
            System.out.println("Loading background from: " + imagePath);

            Image bgImage = new Image(new File(imagePath).toURI().toString());
            background.setImage(bgImage);
            background.setPreserveRatio(false);
            background.setSmooth(false);

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
        stage.setTitle("Admin Panel");
        stage.setScene(scene);
        stage.show();

    }
}