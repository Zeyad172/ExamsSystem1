package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;

import java.util.ArrayList;

import java.net.URL;
import java.sql.*;
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
    @FXML private Label Answer_C;
    @FXML private Label Answer_D;
    @FXML private Label Alarm;
    @FXML private ComboBox<String> Number_of_Answers;

    static protected String url = "jdbc:mysql://localhost:3306/mydb";
    static protected String user = "root";
    static protected String password = "Elzooz3050@#";
    protected Connection con;

    ArrayList<Questions>Question_Arr=new ArrayList<>();

    private int totalQuestions;
    private int currentQuestion = 1;

private int index = 0;
    @FXML
    private void save() {
        Questions obj = new Questions();
        obj.Question = set_Question.getText();
        obj.Answer1 = set_Answer_A.getText();
        obj.Answer2 = set_Answer_B.getText();
        obj.Answer3 = set_Answer_C.getText();
        obj.Answer4 = set_Answer_D.getText();
        obj.Right_Answer = set_Right_Answer.getText();
        Question_Arr.add(obj);
        Alarm.setText("Question added: " + obj.Question);
        // Validate required fields
        if (obj.Question.isEmpty()) {
            Alarm.setText("Error: Question cannot be empty!");
            return;
        }

        if (obj.Right_Answer.isEmpty()) {
            Alarm.setText("You must enter the right answer");
            return;
        }
//        for (int i = 1; i < Question_Arr.size(); i++) {
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
    }

    @FXML
    public void Next_Button() {
        set_Answer_C.setVisible(false);
        set_Answer_D.setVisible(false);
        Answer_C.setText(" ");
        Answer_D.setText(" ");
        Number_of_Answers.setValue("2");
        back.setVisible(true);
        Alarm.setText(" ");

        if (currentQuestion < totalQuestions) {
            currentQuestion++;
            clearFields();
            Number_of_Questions.setText("Question " + currentQuestion);

            if (currentQuestion == totalQuestions) {
                finished.setVisible(true);
                next_question.setVisible(false);
            } else {
                finished.setVisible(false);
            }
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
    }
    private void clearFields() {
        set_Answer_A.clear();
        set_Answer_B.clear();
        set_Answer_C.clear();
        set_Answer_D.clear();
        set_Question.clear();
        set_Right_Answer.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        finished.setVisible(false);
        back.setVisible(false);
        set_Answer_C.setVisible(false);
        set_Answer_D.setVisible(false);
        Answer_C.setText(" ");
        Answer_D.setText(" ");
        Number_of_Answers.getItems().addAll( "2","3", "4");
        Number_of_Answers.setValue("2");
    }

    public void Button_ok(ActionEvent actionEvent) {
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
   /* public void Button_Finish(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        for(int i=0;i<Question_Arr.size();i++) {
            Questions obj = Question_Arr.get(i);
            String sql = "INSERT INTO questions (ID, Name, Password) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, obj.Question);
                pstmt.setString(2, name);
                pstmt.setString(3, password);
                pstmt.executeUpdate();
                System.out.println("Student added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }*/
   public void Button_Finish(ActionEvent actionEvent) {
       try {
           // Load database driver
           Class.forName("com.mysql.cj.jdbc.Driver");

           // Establish database connection
           try (Connection conn = DriverManager.getConnection(url, user, password)) {
               // SQL insert data
               String sql = "INSERT INTO questions (Question, AnswerA, AnswerB, AnswerC, AnswerD, Right_Answer) VALUES (?, ?, ?, ?, ?, ?)";
               // Prepare statement
               PreparedStatement pstmt = conn.prepareStatement(sql);
               // Loop through all questions in the array
               for (Questions obj : Question_Arr) {
                   // Set parameter values
                   pstmt.setString(1, obj.Question);
                   pstmt.setString(2, obj.Answer1);
                   pstmt.setString(3, obj.Answer2);
                   pstmt.setString(4, obj.Answer3);
                   pstmt.setString(5, obj.Answer4);
                   pstmt.setString(6, obj.Right_Answer);
                   // Execute insert
                   pstmt.executeUpdate();
                   System.out.println("Question added: " + obj.Question);
               }
               System.out.println("All questions saved successfully to database");
               Alarm.setText("All questions saved successfully to database");
           } catch (SQLException e) {
               System.err.println("Database error:");
               e.printStackTrace();
               Alarm.setText("Database error");
           }

       } catch (ClassNotFoundException e) {
           System.err.println("Failed to load database driver:");
           Alarm.setText("Failed to load database driver:");
           e.printStackTrace();
       }
   }
}


