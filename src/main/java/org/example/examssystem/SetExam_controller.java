package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetExam_controller implements Initializable{
    @FXML private TextField Exam_Time;
    @FXML private TextField Exam_Date;
    @FXML private ComboBox<String> Exam_Type;
    @FXML private ComboBox<String> Exam_Name;
    @FXML private Label Alarm;
    @FXML private Button Set_Exam_Button;

    private String Exam_info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set_Exam_Button.setVisible(false);
        Exam_Type.getItems().addAll( "Midterm","Final", "Quiz");
        Exam_Type.setValue("Quiz");
//        Exam_Name.getItems().addAll( "2","3", "4");
//        Exam_Name.setValue("2");
    }
    public void Set_Questions(ActionEvent event) {
        if(!Exam_Date.getText().isEmpty()  && !Exam_Time.getText().isEmpty()) {
            Set_Exam_info();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/SetQuestions.fxml"));

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
                StackPane layeredPane = new StackPane();
                layeredPane.getChildren().addAll(background, root);
                Scene scene = new Scene(layeredPane, 800, 600);
                background.fitWidthProperty().bind(scene.widthProperty());
                background.fitHeightProperty().bind(scene.heightProperty());

                stage.setTitle("Helwan's Exams System");
                stage.setScene(scene);
                stage.setMinWidth(600);
                stage.setMinHeight(400);
                stage.show();
            } catch (IOException e) {
                // Handle any errors loading the FXML
                System.err.println("Error loading Scene2.fxml: " + e.getMessage());
                e.printStackTrace();

            }
        }else{Alarm.setText("You Must Fill All Fields");}
    }
    public void Set_Exam_info() {
        this.Exam_info =Exam_Name.getValue()+";"+Exam_Type.getValue()+";"+Exam_Date.getText()+";"+ Exam_Time.getText();
        Alarm.setText(Exam_info);
        System.out.println(Exam_info);
    }
    public String getExam_info() {
        return Exam_info;
    }


}
