package org.example.examssystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class ProfessorSceneController {
    protected Parent root;
    protected Scene scene;
    protected Stage stage;
    @FXML
    TextField idTextField;
    @FXML
    MFXPasswordField mfxPasswordField;
    @FXML
    Button login;


    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Login Failed. Wrong ID or Password");
        alert.showAndWait();
    }
    public void showProfessorPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/ProfessorPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void auth(ActionEvent event) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
        String id = this.idTextField.getText();
        String password = this.mfxPasswordField.getText();
        boolean authSuccessfull = false;
        System.out.println(id);
        System.out.println(password);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users/professorAuth"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ProfessorData>retrievedProfessorsData = mapper.readValue(response.body(), new TypeReference<ArrayList<ProfessorData>>(){});
        System.out.println(retrievedProfessorsData.get(0).professorPassword);
        for(ProfessorData p : retrievedProfessorsData){
            if(Objects.equals(id,p.professorID)&&Objects.equals(password,p.professorPassword)){
                showProfessorPage(event);
                authSuccessfull=true;
            }
        }
        if(!authSuccessfull)showAlert();
    }
}
/*while(professorsData.next()){
            if(Objects.equals(id, professorsData.getString("professorID"))&& Objects.equals(password, professorsData.getString("password"))){
                showProfessorPage(event);
                authSuccessfull=true;
            }
        }*/