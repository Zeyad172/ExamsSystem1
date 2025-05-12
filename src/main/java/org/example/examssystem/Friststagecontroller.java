package org.example.examssystem;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Friststagecontroller implements Initializable {
    @FXML
    private VBox vBox;
    @FXML
    Button Programming,Electronics,Workshop,Probability,Tecnicalwritting,Logic;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://192.168.0.100:8080/student/SelectExam")).GET().build();
        try {
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<String>buttonNames = mapper.readValue(response.body(), new TypeReference<ArrayList<String>>(){});
            System.out.println(buttonNames.get(1));
            for(int i=0 ; i<buttonNames.size();i++){
                Button b = new Button(buttonNames.get(i));
                b.setOnAction( e ->{
                    handleButtonClick(e);
               });
                vBox.getChildren().add(b);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void switchSceneWithData(ActionEvent event, String fxmlFile, String buttonText) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            StageQuationcontroller controller = loader.getController();
            controller.setReceivedButtonText(buttonText);
            StageQuationcontroller.tempdbname = buttonText;

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
