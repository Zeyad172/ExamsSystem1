package org.example.examssystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Friststagecontroller implements Initializable {
    @FXML
    Button Programming,Electronics,Workshop,Probability,Tecnicalwritting,Logic;

    public void initialize(URL url, ResourceBundle resourceBundle) {

   }
    public void BackScenes(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleBackClick(ActionEvent event) {
        BackScenes(event, "/org/example/examssystem/GUI.fxml"); // Use a relative path from resources
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
