package org.example.examssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PastResultController implements Initializable {
    @FXML
    protected VBox vbox = new VBox();
    @FXML
    protected TableView tableView = new TableView();
    @FXML
    protected TableColumn<Result,String> nameCol = new TableColumn<>();
    @FXML
    protected TableColumn<Result,String> IDCol = new TableColumn<>();
    @FXML
    protected TableColumn<Result,String> scoreCol = new TableColumn<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        IDCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("studentScore"));
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
            Statement statement = connection.createStatement();
            statement.execute("USE subjectpastresults");
            ResultSet rs = statement.executeQuery("SHOW TABLES");

            while(rs.next()){
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


    }
}
