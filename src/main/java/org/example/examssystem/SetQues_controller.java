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

    static protected String url = "jdbc:mysql://localhost:3306/mydb";
    static protected String user = "root";
    static protected String password = "Elzooz3050@#";
    protected Connection con;

    ArrayList<Questions>Question_Arr=new ArrayList<>();

    private int totalQuestions=1;
    private int currentQuestion = 1;

private int index = 0;
    @FXML
    private void save() {
        Questions obj = new Questions();
        if(Question_Type.getValue().equals("Written" )){
            obj.Question = set_Question.getText();
            obj.Right_Answer = set_Right_Answer.getText();
        }else if(Question_Type.getValue().equals("MCQ")) {
            obj.Question = set_Question.getText();
            obj.Answer1 = set_Answer_A.getText();
            obj.Answer2 = set_Answer_B.getText();
            obj.Answer3 = set_Answer_C.getText();
            obj.Answer4 = set_Answer_D.getText();
            obj.Right_Answer = set_Right_Answer.getText();
        }
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
        if (totalQuestions <= 0) {
            Alarm.setText("Number must be positive");
            return;
        }
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
        Question_Type.setValue("MCQ");
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
            Answer_A.setText(" ");
            Answer_B.setText(" ");
            Answer_C.setText(" ");
            Answer_D.setText(" ");
            Right_Answer.setText("Correction keys: ");
        }else if (Question_Type.getValue().equals("MCQ")) {
            Number_of_Answers.setVisible(true);
            NumberOfAnswers_lable.setVisible(true);
            set_Written_Question.setVisible(false);
            set_Question.setVisible(true);
            set_Answer_A.setVisible(true);
            set_Answer_B.setVisible(true);
            Answer_A.setText("Answer A:");
            Answer_B.setText("Answer B:");
            Right_Answer.setText("Right Answer: ");
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
        set_Written_Question.setVisible(false);
        Question_Type.getItems().addAll( "MCQ", "Written" );
        Question_Type.setValue("MCQ");
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
   public void Button_Finish(ActionEvent actionEvent) {
       try {
           // Load MySQL JDBC driver
           Class.forName("com.mysql.cj.jdbc.Driver");
           con = DriverManager.getConnection(url, user, "Elzooz3050@");

           System.out.println("Database connection established successfully!");
           Statement stmt = con.createStatement();
           String sql = "INSERT INTO Questions VALUES (?, ?, ?, ?, ?, ?)";
               // Prepare statement
               PreparedStatement pstmt = con.prepareStatement(sql);
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
                   System.out.println("Question added to data base: " + obj.Question);
               }
               System.out.println("All questions saved successfully to database");
               Alarm.setText("All questions saved successfully to database");
           con.close();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found", ex);
           Alarm.setText("Failed to load database driver:");
       } catch (SQLException ex) {
           Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
           Alarm.setText("Database error");
       }
   }
}
// Zeyad3050
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.event.ActionEvent;
//import java.net.URL;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//public class SetQues_controller implements Initializable {
//    // FXML components
//    @FXML private TextField set_QuestionNumber;
//    @FXML private Label Number_of_Questions;
//    @FXML private TextField set_Question;
//    @FXML private TextField set_Answer_A;
//    @FXML private TextField set_Answer_B;
//    @FXML private TextField set_Answer_C;
//    @FXML private TextField set_Answer_D;
//    @FXML private TextField set_Right_Answer;
//    @FXML private Button finished;
//    @FXML private Button next_question;
//    @FXML private Button back;
//    @FXML private Label Answer_C;
//    @FXML private Label Answer_D;
//    @FXML private Label Alarm;
//    @FXML private ComboBox<String> Number_of_Answers;
//
//    // Database configuration
//    static protected String url = "jdbc:mysql://localhost:3306/mydb";
//    static protected String user = "root";
//    static protected String password = "Elzooz3050@#";
//
//    // Application data
//    ArrayList<Questions> Question_Arr = new ArrayList<>();
//    private int totalQuestions;
//    private int currentQuestion = 1;
//
//    @FXML
//    private void save() {
//        // Validate inputs
//        if (set_Question.getText().trim().isEmpty()) {
//            Alarm.setText("Error: Question cannot be empty!");
//            return;
//        }
//
//        if (set_Right_Answer.getText().trim().isEmpty()) {
//            Alarm.setText("You must enter the right answer");
//            return;
//        }
//
//        // Check for duplicate question
//        String newQuestion = set_Question.getText().trim();
//        for (Questions q : Question_Arr) {
//            if (q.Question.equalsIgnoreCase(newQuestion)) {
//                Alarm.setText("Error: Question already exists!");
//                return;
//            }
//        }
//
//        // Create and add new question
//        Questions obj = new Questions();
//        obj.Question = newQuestion;
//        obj.Answer1 = set_Answer_A.getText().trim();
//        obj.Answer2 = set_Answer_B.getText().trim();
//        obj.Answer3 = set_Answer_C.getText().trim();
//        obj.Answer4 = set_Answer_D.getText().trim();
//        obj.Right_Answer = set_Right_Answer.getText().trim();
//
//        Question_Arr.add(obj);
//        Alarm.setText("Question added: " + obj.Question);
//        clearFields();
//    }
//
//    @FXML
//    public void Save_number_of_questions() {
//        if (totalQuestions <= 0) {
//            Alarm.setText("Number must be positive");
//            return;
//        }else {
//            try {
//                totalQuestions = Integer.parseInt(set_QuestionNumber.getText());
//                if (totalQuestions <= 0) {
//                    Alarm.setText("Number must be positive");
//                    return;
//                }
//                currentQuestion = 1;
//                Number_of_Questions.setText("Question " + currentQuestion);
//                finished.setVisible(false);
//                Alarm.setText("");
//            } catch (NumberFormatException e) {
//                Alarm.setText("Invalid number format");
//            }
//        }
//    }
//
//    @FXML
//    public void Next_Button() {
//        save(); // Save current question before moving to next
//
//        if (currentQuestion < totalQuestions) {
//            currentQuestion++;
//            Number_of_Questions.setText("Question " + currentQuestion);
//            updateUIForQuestionCount();
//
//            if (currentQuestion == totalQuestions) {
//                finished.setVisible(true);
//                next_question.setVisible(false);
//            }
//        }
//    }
//
//    @FXML
//    public void Back_Button(ActionEvent event) {
//        if (currentQuestion > 1) {
//            currentQuestion--;
//            Number_of_Questions.setText("Question " + currentQuestion);
//            updateUIForQuestionCount();
//
//            // Load the previous question from the list
//            if (currentQuestion <= Question_Arr.size()) {
//                Questions prevQuestion = Question_Arr.get(currentQuestion - 1);
//                set_Question.setText(prevQuestion.Question);
//                set_Answer_A.setText(prevQuestion.Answer1);
//                set_Answer_B.setText(prevQuestion.Answer2);
//                set_Answer_C.setText(prevQuestion.Answer3 != null ? prevQuestion.Answer3 : "");
//                set_Answer_D.setText(prevQuestion.Answer4 != null ? prevQuestion.Answer4 : "");
//                set_Right_Answer.setText(prevQuestion.Right_Answer);
//
//                // Show or hide C and D answer fields based on content
//                boolean showC = prevQuestion.Answer3 != null && !prevQuestion.Answer3.isEmpty();
//                boolean showD = prevQuestion.Answer4 != null && !prevQuestion.Answer4.isEmpty();
//                set_Answer_C.setVisible(showC);
//                Answer_C.setText(showC ? "Answer C:" : " ");
//                set_Answer_D.setVisible(showD);
//                Answer_D.setText(showD ? "Answer D:" : " ");
//
//                // Update combo box to match visible answers
//                if (showD) {
//                    Number_of_Answers.setValue("4");
//                } else if (showC) {
//                    Number_of_Answers.setValue("3");
//                } else {
//                    Number_of_Answers.setValue("2");
//                }
//            }
//        }
//    }
//
//
//    private void updateUIForQuestionCount() {
//        back.setVisible(currentQuestion > 1);
//        next_question.setVisible(currentQuestion < totalQuestions);
//        finished.setVisible(currentQuestion == totalQuestions);
//    }
//
//    private void clearFields() {
//        set_Question.clear();
//        set_Answer_A.clear();
//        set_Answer_B.clear();
//        set_Answer_C.clear();
//        set_Answer_D.clear();
//        set_Right_Answer.clear();
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        finished.setVisible(false);
//        back.setVisible(false);
//        set_Answer_C.setVisible(false);
//        set_Answer_D.setVisible(false);
//        Answer_C.setText(" ");
//        Answer_D.setText(" ");
//        Number_of_Answers.getItems().addAll("2", "3", "4");
//        Number_of_Answers.setValue("2");
//    }
//
//    public void Button_ok(ActionEvent actionEvent) {
//        String selected = Number_of_Answers.getValue();
//        set_Answer_C.setVisible(selected.equals("3") || selected.equals("4"));
//        set_Answer_D.setVisible(selected.equals("4"));
//        Answer_C.setText(selected.equals("3") || selected.equals("4") ? "Answer C:" : " ");
//        Answer_D.setText(selected.equals("4") ? "Answer D:" : " ");
//    }
//
//    public void Button_Finish(ActionEvent actionEvent) {
//        save(); // Save the last question before finishing
//
//        if (Question_Arr.isEmpty()) {
//            Alarm.setText("No questions to save!");
//            return;
//        }
//
//        try (Connection conn = DriverManager.getConnection(url, user, password)) {
//            // Create table if not exists
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS questions (" +
//                    /*"id INT AUTO_INCREMENT PRIMARY KEY, " +*/
//                    "Question TEXT NOT NULL, " +
//                    "AnswerA TEXT NOT NULL, " +
//                    "AnswerB TEXT NOT NULL, " +
//                    "AnswerC TEXT, " +
//                    "AnswerD TEXT, " +
//                    "Right_Answer TEXT NOT NULL)";
//
//            try (Statement stmt = conn.createStatement()) {
//                stmt.execute(createTableSQL);
//            }
//
//            // Insert questions
//            String insertSQL = "INSERT INTO questions (Question, AnswerA, AnswerB, AnswerC, AnswerD, Right_Answer) " +"VALUES (?, ?, ?, ?, ?, ?)";
//
//            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
//                for (Questions obj : Question_Arr) {
//                    pstmt.setString(1, obj.Question);
//                    pstmt.setString(2, obj.Answer1);
//                    pstmt.setString(3, obj.Answer2);
//                    pstmt.setString(4, obj.Answer3.isEmpty() ? null : obj.Answer3);
//                    pstmt.setString(5, obj.Answer4.isEmpty() ? null : obj.Answer4);
//                    pstmt.setString(6, obj.Right_Answer);
//                    pstmt.executeUpdate();
//                }
//                Alarm.setText("All questions saved successfully!");
//                Question_Arr.clear(); // Clear after successful save
//            }
//        } catch (SQLException e) {
//            Alarm.setText("Database error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//    public void Delete_Button(ActionEvent actionEvent) {
//        if (Question_Arr.isEmpty() || currentQuestion > Question_Arr.size()) {
//            Alarm.setText("Nothing to delete");
//            return;
//        }
//        // Remove the current question
//        Question_Arr.remove(currentQuestion - 1);
//        Alarm.setText("Question " + currentQuestion + " deleted");
//
//        // Adjust currentQuestion if needed
//        if (currentQuestion > Question_Arr.size() && currentQuestion > 1) {
//            currentQuestion--;
//        }
//        Number_of_Questions.setText("Question " + currentQuestion);
//        updateUIForQuestionCount();
//        // Display the next question in that position (or clear fields if none)
//        if (currentQuestion <= Question_Arr.size()) {
//            Questions q = Question_Arr.get(currentQuestion - 1);
//            set_Question.setText(q.Question);
//            set_Answer_A.setText(q.Answer1);
//            set_Answer_B.setText(q.Answer2);
//            set_Answer_C.setText(q.Answer3 != null ? q.Answer3 : "");
//            set_Answer_D.setText(q.Answer4 != null ? q.Answer4 : "");
//            set_Right_Answer.setText(q.Right_Answer);
//
//            boolean showC = q.Answer3 != null && !q.Answer3.isEmpty();
//            boolean showD = q.Answer4 != null && !q.Answer4.isEmpty();
//            set_Answer_C.setVisible(showC);
//            Answer_C.setText(showC ? "Answer C:" : " ");
//            set_Answer_D.setVisible(showD);
//            Answer_D.setText(showD ? "Answer D:" : " ");
//            Number_of_Answers.setValue(showD ? "4" : showC ? "3" : "2");
//        } else {
//            clearFields();
//        }
//    }
//}
