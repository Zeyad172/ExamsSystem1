package org.example.examssystem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PastResultController implements Initializable {
    @FXML
    protected VBox vbox = new VBox();
    @FXML
    protected TableView tableView = new TableView();
    @FXML
    protected TableColumn<Result, String> nameCol = new TableColumn<>();
    @FXML
    protected TableColumn<Result, String> IDCol = new TableColumn<>();
    @FXML
    protected TableColumn<Result, String> scoreCol = new TableColumn<>();
//bh
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        IDCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("studentScore"));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/users/setButtons")).GET().build();
        HttpResponse<String> response;
        ObjectMapper mapper;
        ArrayList<String> ButtonNames;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            mapper = new ObjectMapper();
            ButtonNames = mapper.readValue(response.body(), new TypeReference<ArrayList<String>>() {
            });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        for (String bName : ButtonNames) {
            Button b = new Button(bName);
            b.setOnAction(e -> {
                HttpClient client2 = HttpClient.newHttpClient();
                HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/users/setButtonAction/"+b.getText())).GET().build();
                HttpResponse<String> response2;
                ObjectMapper mapper2;
                ArrayList<Result> results;
                try {
                    response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                    mapper2 = new ObjectMapper();
                    results = mapper2.readValue(response2.body(), new TypeReference<ArrayList<Result>>() {
                    });
                    ObservableList tableResults = FXCollections.observableArrayList(results);
                    tableView.setItems(tableResults);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
            vbox.getChildren().add(b);
        }
    }
}


        /*while(rs.next()){
                Button b = new Button(rs.getString(1));
                b.setOnAction(e->{
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
                        Statement statement2 = connection2.createStatement();
                        statement2.execute("USE subjectpastresults");
                        ResultSet rs2 = statement2.executeQuery("SELECT * FROM "+ b.getText());
                        Result temp = new Result();
                        ObservableList<Result>results = FXCollections.observableArrayList();
                        while(rs2.next()){
                            results.add(new Result(rs2.getString(1),rs2.getString(2),rs2.getString(3)));
                        }
                        rs2.close();
                        statement2.close();
                        tableView.setItems(results);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                vbox.getChildren().add(b);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }*/

