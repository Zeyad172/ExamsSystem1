package org.example.examssystem;

//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.ComboBox;
//import javafx.event.ActionEvent;
//
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.*;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class SetExam_controller implements Initializable{
//    @FXML private TextField Exam_Time,ID;
//    @FXML private TextField Exam_Date;
//    @FXML private ComboBox<String> Exam_Type;
//    @FXML private ComboBox<String> Exam_Name;
//    @FXML private Label Alarm;
//    @FXML private Button Set_Exam_Button;
//
//    private String Exam_info;
//    public static String id;
//
//    static protected String url = "jdbc:mysql://localhost:3306/mydb";
//    static protected String user = "root";
//    static protected String password = "Elzooz3050@#";
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        //Set_Exam_Button.setVisible(false);
//        Exam_Type.getItems().addAll( "Midterm","Final", "Quiz");
//        Exam_Type.setValue("Quiz");
////        Exam_Name.getItems().addAll( "2","3", "4");
////        Exam_Name.setValue("2");
//    }
//    public void Set_Questions(ActionEvent event) {
//        try {
//            // Load MySQL JDBC driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection(url, user, "Elzooz3050@");
//
//            System.out.println("Database connection established successfully!");
//            Statement stmt = con.createStatement();
//            String sql = "INSERT INTO exams_info VALUES (?, ?, ?, ?, ?)";
//            // Prepare statement
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            // Loop through all questions in the array
//
//                // Set parameter values
//                pstmt.setString(1, Exam_Name.getValue());
//                pstmt.setString(2, ID.getText());
//                pstmt.setString(3, Exam_Time.getText());
//                pstmt.setString(4, Exam_Type.getValue());
//                pstmt.setString(5, Exam_Date.getText());
//
//                pstmt.executeUpdate();
//                System.out.println("Exam Information added successfully! : "+Exam_info );
//
//            System.out.println("All Information saved successfully to database");
//            Alarm.setText("Exam Information added successfully! : "+Exam_info);
//            con.close();
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found", ex);
//            Alarm.setText("Failed to load database driver:");
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
//            Alarm.setText("Database error");
//        }
//        if(!Exam_Date.getText().isEmpty()  && !Exam_Time.getText().isEmpty()) {
//            Set_Exam_info();
//            try {
//                Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/SetQuestions.fxml"));
//
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//                ImageView background = new ImageView();
//                try {
//                    String imagePath = "D:\\EL ZOOZ JAVA\\Exams System\\src\\main\\java\\org\\example\\examssystem\\unnamed.jpg";
//                    System.out.println("Loading background from: " + imagePath);
//
//                    Image bgImage = new Image(new File(imagePath).toURI().toString());
//                    background.setImage(bgImage);
//                    background.setPreserveRatio(false);
//                    background.setSmooth(true);
//                    background.setOpacity(0.9);
//
//                    System.out.println("Background loaded successfully. Dimensions: " +
//                            bgImage.getWidth() + "x" + bgImage.getHeight());
//                } catch (Exception e) {
//                    System.err.println("Failed to load background:");
//                    e.printStackTrace();
//                }
//                StackPane layeredPane = new StackPane();
//                layeredPane.getChildren().addAll(background, root);
//                Scene scene = new Scene(layeredPane, 800, 600);
//                background.fitWidthProperty().bind(scene.widthProperty());
//                background.fitHeightProperty().bind(scene.heightProperty());
//
//                stage.setTitle("Helwan's Exams System");
//                stage.setScene(scene);
//                stage.setMinWidth(600);
//                stage.setMinHeight(400);
//                stage.show();
//            } catch (IOException e) {
//                // Handle any errors loading the FXML
//                System.err.println("Error loading Scene2.fxml: " + e.getMessage());
//                e.printStackTrace();
//
//            }
//        }else{Alarm.setText("You Must Fill All Fields");}
//    }
//    public void Set_Exam_info() {
//        id = ID.getText();  // static value set here
//        this.Exam_info = Exam_Name.getValue() + ";" + Exam_Type.getValue() + ";" +
//                Exam_Date.getText() + ";" + Exam_Time.getText() + ";" + id;
//        Alarm.setText(Exam_info);
//        System.out.println(Exam_info);
//    }
//    public String getExam_info() {
//        return Exam_info;
//    }
//
//
//}
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetExam_controller implements Initializable {
    @FXML private TextField Exam_Time, ID;
    @FXML private TextField Exam_Date;
    @FXML private ComboBox<String> Exam_Type;
    @FXML private ComboBox<String> Exam_Name;
    @FXML private Label Alarm;
    @FXML private Button Set_Exam_Button;

    private String Exam_info;
    public static String id;

    static protected String url = "jdbc:mysql://localhost:3306/mydb";
    static protected String user = "root";
    static protected String password = "Elzooz3050@#";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Exam_Type.getItems().addAll("Midterm", "Final", "Quiz");
        Exam_Type.setValue("Quiz");
    }

    public void Set_Questions(ActionEvent event) {
        // Show confirmation popup before continuing
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exam ID Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("This exam ID is: " + ID.getText());

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        // Wait for user to click OK
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, user, "Elzooz3050@");

                    System.out.println("Database connection established successfully!");
                    Statement stmt = con.createStatement();
                    String sql = "INSERT INTO exams_info VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(sql);

                    pstmt.setString(1, Exam_Name.getValue());
                    pstmt.setString(2, ID.getText());
                    pstmt.setString(3, Exam_Time.getText());
                    pstmt.setString(4, Exam_Type.getValue());
                    pstmt.setString(5, Exam_Date.getText());

                    pstmt.executeUpdate();
                    System.out.println("Exam Information added successfully! : " + Exam_info);

                    Alarm.setText("Exam Information added successfully!");
                    con.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found", ex);
                    Alarm.setText("Failed to load database driver.");
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
                    Alarm.setText("Database error");
                }

                if (!Exam_Date.getText().isEmpty() && !Exam_Time.getText().isEmpty()) {
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
                        System.err.println("Error loading FXML: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    Alarm.setText("You Must Fill All Fields");
                }
            }
        });
    }

    public void Set_Exam_info() {
        id = ID.getText();  // static value set here
        this.Exam_info = Exam_Name.getValue() + ";" + Exam_Type.getValue() + ";" +
                Exam_Date.getText() + ";" + Exam_Time.getText() + ";" + id;
        Alarm.setText(Exam_info);
        System.out.println(Exam_info);
    }

    public String getExam_info() {
        return Exam_info;
    }
}

