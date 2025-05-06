package org.example.examssystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.ResourceBundle;

public class Friststagecontroller implements Initializable {
    @FXML
    Button Programming,Electronics,Workshop,Probability,Tecnicalwritting,Logic;

    public void initialize(URL url, ResourceBundle resourceBundle) {

   }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ImageView background = new ImageView();
        try {
            String imagePath = "C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg";
            System.out.println("Loading background from: " + imagePath);

            Image bgImage = new Image(new File(imagePath).toURI().toString());
            background.setImage(bgImage);
            background.setPreserveRatio(false);
            background.setSmooth(true);

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

    public void switchSceneWithData(ActionEvent event, String fxmlFile, String buttonText) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            StageQuationcontroller controller = loader.getController();
            controller.setReceivedButtonText(buttonText);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        switchSceneWithData(event, "/org/example/examssystem/stage_Quation.fxml", buttonText);
    }




}
