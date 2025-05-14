package org.example.examssystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class autoExamController {
    @FXML
    private javafx.scene.control.TextField examName;
    @FXML
    private Button generate;
    @FXML
    private javafx.scene.control.TextField easy;
    @FXML
    private javafx.scene.control.TextField medium;
    @FXML
    private javafx.scene.control.TextField hard;
    public void generateClicked(ActionEvent event) throws IOException, InterruptedException {
        String tempExamName = examName.getText().replaceAll(" ",",");
        int noEasy = Integer.parseInt(easy.getText());
        int noMedium = Integer.parseInt(medium.getText());
        int noHard = Integer.parseInt(hard.getText());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://192.168.252.15:8080/"+tempExamName+"/"+noEasy+"/"+noMedium+"/"+noHard)).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
