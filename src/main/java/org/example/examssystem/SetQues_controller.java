package org.example.examssystem;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetQues_controller implements Initializable {
    @FXML private TextField  set_QuestionNumber;
    @FXML private Label Number_of_Questions;
    @FXML private TextField set_Question;
    @FXML private TextField set_Answer_A;
    @FXML private TextField set_Answer_B;
    @FXML private TextField set_Answer_C;
    @FXML private TextField set_Answer_D;
    @FXML private TextField set_Right_Answer;
    @FXML private Button finished;
    @FXML private Button next_question;
    @FXML private Button back;
    @FXML private Button Exit;
    @FXML private Label Answer_A;
    @FXML private Label Answer_B;
    @FXML private Label Answer_C;
    @FXML private Label Answer_D;
    @FXML private Label Alarm;
    @FXML private Label NumberOfAnswers_lable;
    @FXML private Label Right_Answer;
    @FXML private TextArea set_Written_Question;
    @FXML private ComboBox<String> Number_of_Answers;
    @FXML private ComboBox<String> Question_Type;

    static protected String Url = "jdbc:mysql://192.168.252.15:3306/nourdb";
    static protected String user = "root";
    static protected String password = "Elnaggar2@";
    protected Connection con;
    private boolean test=HelloProfessor_controller.test;
    ArrayList<Questions>Question_Arr=new ArrayList<>(Collections.nCopies(50, null));
    ArrayList<Questions>get_Question_Arr=new ArrayList<>(50);

    private int totalQuestions=1;
    private int currentQuestion = 1;

    private int index = 0;

    @FXML
    private void Save_Question() {
        Questions obj = new Questions();
        if(Question_Type.getValue().equals("Written" )){
            obj.Question = set_Written_Question.getText();
            obj.Right_Answer = set_Right_Answer.getText();
            obj.Type = Question_Type.getValue();
        }else if(Question_Type.getValue().equals("MCQ")) {
            obj.Question = set_Question.getText();
            obj.Answer1 = set_Answer_A.getText();
            obj.Answer2 = set_Answer_B.getText();
            obj.Type = Question_Type.getValue();
            if(Number_of_Answers.getValue().equals("2")){
                obj.Answer3 = null;
                obj.Answer4 = null;
            }else if(Number_of_Answers.getValue().equals("3")){
                obj.Answer3 = set_Answer_C.getText();
                obj.Answer4 = null;
            }else if(Number_of_Answers.getValue().equals("4")){
                obj.Answer3 = set_Answer_C.getText();
                obj.Answer4 = set_Answer_D.getText();
            }
            obj.Type = Question_Type.getValue();
            obj.Right_Answer = set_Right_Answer.getText();
        }
        Question_Arr.set(currentQuestion-1, obj);
        Alarm.setText("Question added: " + obj.Question);
        System.out.println(Question_Arr.get(currentQuestion-1));
        // Validate required fields
        if (obj.Question.isEmpty()) {
            Alarm.setText("Error: Question cannot be empty!");
            return;
        }

        if (obj.Right_Answer.isEmpty()) {
            Alarm.setText("You must enter the right answer");
            return;
        }
//        for (int i = 0; i < Question_Arr.size(); i++) {
//            if (obj.Question.equals(Question_Arr.get(i).Question)) {
//                System.out.println("Error: Question '" + obj.Question + "' already exists!");
//            } else {
//                Question_Arr.add(obj);
//                System.out.println("Question added successfully!");
//            }
//        }
    }

    @FXML
    public void Save_number_of_questions() {
        try {
            totalQuestions = Integer.parseInt(set_QuestionNumber.getText());
            currentQuestion = 1;
            Number_of_Questions.setText("Question " + currentQuestion);
            finished.setVisible(false);
        } catch (NumberFormatException e) {
            set_QuestionNumber.setText("Invalid number");
        }
        if (totalQuestions <= 0) {
            Alarm.setText("Number must be positive");
            return;
        }

    }

    @FXML
    public void Next_Button() {
        if (currentQuestion < totalQuestions) {
            currentQuestion++;
            Number_of_Questions.setText("Question " + currentQuestion);

            if (currentQuestion == totalQuestions) {
                finished.setVisible(true);
                Exit.setVisible(true);
                next_question.setVisible(false);
            } else {
                finished.setVisible(false);
                Exit.setVisible(false);
            }
        }

        back.setVisible(true);
        Alarm.setText("");

        Questions obj = Question_Arr.get(currentQuestion - 1);
        if (obj == null) {
            clearFields();
            Number_of_Answers.setValue("2");
            Question_Type.setValue("MCQ");
            return;
        }

        clearFields();

        if (obj.Type.equals("Written")) {
            set_Written_Question.setText(obj.Question);
            set_Right_Answer.setText(obj.Right_Answer);

            set_Written_Question.setVisible(true);
            Number_of_Answers.setVisible(false);
            NumberOfAnswers_lable.setVisible(false);
            Question_Type.setValue("Written");
            set_Question.setVisible(false);
            set_Answer_A.setVisible(false);
            set_Answer_B.setVisible(false);
            set_Answer_C.setVisible(false);
            set_Answer_D.setVisible(false);
            Answer_A.setText("");
            Answer_B.setText("");
            Answer_C.setText("");
            Answer_D.setText("");
            Right_Answer.setText("Correction keys:");
        } else if (obj.Type.equals("MCQ")) {
            set_Question.setText(obj.Question);
            set_Answer_A.setText(obj.Answer1);
            set_Answer_B.setText(obj.Answer2);
            set_Answer_C.setText(obj.Answer3);
            set_Answer_D.setText(obj.Answer4);
            Question_Type.setValue("MCQ");
            set_Right_Answer.setText(obj.Right_Answer);

            set_Question.setVisible(true);
            set_Answer_A.setVisible(true);
            set_Answer_B.setVisible(true);

            // Show/Hide fields C and D based on actual saved answers
            if (obj.Answer3 != null && !obj.Answer3.trim().isEmpty()) {
                set_Answer_C.setVisible(true);
                Answer_C.setVisible(true);
                Number_of_Answers.setValue("3");
            } else {
                set_Answer_C.setVisible(false);
                Answer_C.setVisible(false);
                set_Answer_C.clear();
                Number_of_Answers.setValue("2");
            }

            if (obj.Answer4 != null && !obj.Answer4.trim().isEmpty()) {
                set_Answer_D.setVisible(true);
                Answer_D.setVisible(true);
                Number_of_Answers.setValue("4");
            } else {
                set_Answer_D.setVisible(false);
                Answer_D.setVisible(false);
                set_Answer_D.clear();
                if (obj.Answer3 != null && !obj.Answer3.trim().isEmpty()) {
                    set_Answer_C.setVisible(true);
                    Answer_C.setVisible(true);
                    Number_of_Answers.setValue("3");
                } else {
                    set_Answer_C.setVisible(false);
                    Answer_C.setVisible(false);
                    set_Answer_C.clear();
                    Number_of_Answers.setValue("2");
                }
            }
            Number_of_Answers.setVisible(true);
            NumberOfAnswers_lable.setVisible(true);

            Answer_A.setText("Answer A:");
            Answer_B.setText("Answer B:");
            Right_Answer.setText("Right Answer:");
            Number_of_Answer_Box();
        }
    }

    @FXML
    public void Question_Type_Box() {
        if (Question_Type.getValue().equals("Written" )) {
            set_Written_Question.setVisible(true);
            Number_of_Answers.setVisible(false);
            NumberOfAnswers_lable.setVisible(false);
            set_Question.setVisible(false);
            set_Answer_A.setVisible(false);
            set_Answer_B.setVisible(false);
            set_Answer_C.setVisible(false);
            set_Answer_D.setVisible(false);
            Answer_A.setText("");
            Answer_B.setText("");
            Answer_C.setText("");
            Answer_D.setText("");
            Right_Answer.setText("Correction keys:");
        }else if (Question_Type.getValue().equals("MCQ")) {
            Number_of_Answers.setVisible(true);
            NumberOfAnswers_lable.setVisible(true);
            set_Written_Question.setVisible(false);
            set_Question.setVisible(true);
            set_Answer_A.setVisible(true);
            set_Answer_B.setVisible(true);
            Answer_A.setText("Answer A:");
            Answer_B.setText("Answer B:");
            Right_Answer.setText("Right Answer:");
            Number_of_Answer_Box();
        }
    }

    @FXML
    public void Back_Button(ActionEvent event) {
        if (currentQuestion > 1) {
            currentQuestion--;
            Number_of_Questions.setText("Question " + currentQuestion);
            finished.setVisible(false);
        }
        if (currentQuestion < totalQuestions) {
            next_question.setVisible(true);
        }
        if (currentQuestion == 1) {
            back.setVisible(false);
        }
        Questions obj = Question_Arr.get(currentQuestion - 1);
        if(obj.Type.equals("Written" )) {
            set_Written_Question.setText(obj.Question);
            set_Right_Answer.setText(obj.Right_Answer);
            set_Written_Question.setVisible(true);
            Number_of_Answers.setVisible(false);
            NumberOfAnswers_lable.setVisible(false);
            set_Question.setVisible(false);
            set_Answer_A.setVisible(false);
            set_Answer_B.setVisible(false);
            set_Answer_C.setVisible(false);
            set_Answer_D.setVisible(false);
            Answer_A.setText("");
            Answer_B.setText("");
            Answer_C.setText("");
            Answer_D.setText("");
            Right_Answer.setText("Correction keys:");
        } else if(obj.Type.equals("MCQ")){
            set_Question.setText(obj.Question);
            set_Answer_A.setText(obj.Answer1);
            set_Answer_B.setText(obj.Answer2);
            set_Answer_C.setText(obj.Answer3);
            set_Answer_D.setText(obj.Answer4);
            set_Right_Answer.setText(obj.Right_Answer);
            Number_of_Answers.setVisible(true);
            NumberOfAnswers_lable.setVisible(true);
            set_Written_Question.setVisible(false);
            if (obj.Answer3 != null && !obj.Answer3.trim().isEmpty()) {
                set_Answer_C.setVisible(true);
                Answer_C.setVisible(true);
                Number_of_Answers.setValue("3");
            } else {
                set_Answer_C.setVisible(false);
                Answer_C.setVisible(false);
                set_Answer_C.clear();
                Number_of_Answers.setValue("2");
            }

            if (obj.Answer4 != null && !obj.Answer4.trim().isEmpty()) {
                set_Answer_D.setVisible(true);
                Answer_D.setVisible(true);
                Number_of_Answers.setValue("4");
            } else {
                set_Answer_D.setVisible(false);
                Answer_D.setVisible(false);
                set_Answer_D.clear();
                if (obj.Answer3 != null && !obj.Answer3.trim().isEmpty()) {
                    set_Answer_C.setVisible(true);
                    Answer_C.setVisible(true);
                    Number_of_Answers.setValue("3");
                } else {
                    set_Answer_C.setVisible(false);
                    Answer_C.setVisible(false);
                    set_Answer_C.clear();
                    Number_of_Answers.setValue("2");
                }

            }
            set_Question.setVisible(true);
            set_Answer_A.setVisible(true);
            set_Answer_B.setVisible(true);
            Answer_A.setText("Answer A:");
            Answer_B.setText("Answer B:");
            Right_Answer.setText("Right Answer:");
            Number_of_Answer_Box();
        }
    }
    private void clearFields() {
        set_Answer_A.clear();
        set_Answer_B.clear();
        set_Answer_C.clear();
        set_Answer_D.clear();
        set_Question.clear();
        set_Right_Answer.clear();
        set_Written_Question.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!test){
        finished.setVisible(false);
        back.setVisible(false);
        Exit.setVisible(false);
        set_Answer_C.setVisible(false);
        set_Answer_D.setVisible(false);
        Answer_C.setText("");
        Answer_D.setText("");
        Number_of_Questions.setText("Question " + currentQuestion);
        Number_of_Answers.getItems().addAll( "2","3", "4");
        Number_of_Answers.setValue("2");
        set_Written_Question.setVisible(false);
        Question_Type.getItems().addAll( "MCQ", "Written" );
        Question_Type.setValue("MCQ");}
        else{
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(Url, user, "Elnaggar2@");
                System.out.println("Database connection established successfully!");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM questions");
                while(rs.next()) {
                   Questions obj = new Questions();
                   obj.Question = rs.getString("question");
                   obj.Right_Answer = rs.getString("correctChoice");
                   obj.Answer1 = rs.getString("choiceA");
                   obj.Answer2 = rs.getString("choiceB");
                   obj.Answer3 = rs.getString("choiceC");
                   obj.Answer4 = rs.getString("choiceD");
                   obj.Type = rs.getString("Type");
                }
                con.close();
                stmt.close();
                Alarm.setText("All questions saved successfully to Array");
                con.close();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, null, ex);
                Alarm.setText("Database error");
            }
        }
    }

    public void Number_of_Answer_Box() {
        if (Number_of_Answers.getValue().equals("3")) {
            set_Answer_C.setVisible(true);
            Answer_C.setVisible(true);
            Answer_C.setText("Answer C:");
            Answer_D.setText(" ");
            set_Answer_D.setVisible(false);
        }else if (Number_of_Answers.getValue().equals("4")) {
            set_Answer_C.setVisible(true);
            set_Answer_D.setVisible(true);
            Answer_C.setText("Answer C:");
            Answer_D.setText("Answer D:");
        }else if (Number_of_Answers.getValue().equals("2")) {
            set_Answer_C.setVisible(false);
            set_Answer_D.setVisible(false);
            Answer_C.setText(" ");
            Answer_D.setText(" ");
        }
    }

//    public void createQuestionsTable(String tableName) {
//        String sql = String.format(
//                "CREATE TABLE IF NOT EXISTS `%s` (" +
//                        "Question VARCHAR(150) NULL, " +
//                        "AnswerA VARCHAR(150) NULL, " +
//                        "AnswerB VARCHAR(150) NULL, " +
//                        "AnswerC VARCHAR(150) NULL, " +
//                        "AnswerD VARCHAR(150) NULL, " +
//                        "Right_Answer VARCHAR(150) NULL, " +
//                        "Type VARCHAR(150) NULL" +
//                        ")", tableName);
//
//        try (Connection conn = DriverManager.getConnection(Url, user, "Elnaggar2@");
//             Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate(sql);
//            System.out.println("Table created successfully: " + tableName);
//        } catch (SQLException e) {
//            System.err.println("Error creating table: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public void Finish_Button(ActionEvent actionEvent) throws IOException, InterruptedException {
        String id = SetExam_controller.id;
        id = id.replaceAll(" ",",");
        System.out.println(Question_Arr.get(1).Question);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Question_Arr);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://192.168.252.15:8080/professor/createExam/"+id)).header("Content-Type","application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse response = client.send(request,HttpResponse.BodyHandlers.ofString());
        if(Objects.equals((String) response.body(), "true")){
            Alarm.setText("Exam added successfully");
        }
        else {
            Alarm.setText("Database Error");
        }



//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection(Url, user, "Elnaggar2@");
//            System.out.println("Database connection established successfully!");
//
//            String sql1 = "INSERT INTO `" + id + "` VALUES (?, ?, ?, ?, ?, ?, ?)";
//            String sql2 = "INSERT INTO questions  VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//            PreparedStatement pstmt1 = con.prepareStatement(sql1);
//            PreparedStatement pstmt2 = con.prepareStatement(sql2);
//
//            for (Questions obj : Question_Arr) {
//                //created table
//                pstmt1.setString(1, obj.Question);
//                pstmt1.setString(2, obj.Answer1);
//                pstmt1.setString(3, obj.Answer2);
//                pstmt1.setString(4, obj.Answer3);
//                pstmt1.setString(5, obj.Answer4);
//                pstmt1.setString(6, obj.Right_Answer);
//                pstmt1.setString(7, obj.Type);
//                pstmt1.executeUpdate();
//                //Question bank table
//                pstmt2.setString(1, obj.Question);
//                pstmt2.setString(2, obj.Answer1);
//                pstmt2.setString(3, obj.Answer2);
//                pstmt2.setString(4, obj.Answer3);
//                pstmt2.setString(5, obj.Answer4);
//                pstmt2.setString(6, obj.Right_Answer);
//                pstmt2.setString(7, obj.Type);
//                pstmt2.executeUpdate();
//            }




    }

    public void Delete_Button(ActionEvent actionEvent) {
        Questions obj = Question_Arr.get(currentQuestion);
        set_Written_Question.setText("");
        set_Right_Answer.setText("");
        set_Answer_A.setText("");
        set_Answer_B.setText("");
        set_Answer_C.setText("");
        set_Answer_D.setText("");
        set_Question.setText("");
        Alarm.setText("Question deleted successfully(Please Enter another one to continue)!!)");
    }
    public void Exit_Button(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/Hello_Professor.fxml"));

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            ImageView background = new ImageView();
            try {
                String imagePath = "org/example/examssystem/unnamed.jpg";
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
    }
}