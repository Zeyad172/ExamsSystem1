package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;

public class SetQues_controller {
    @FXML private TextField Number_of_Questions;
    @FXML private Label QuestionNumber;
    @FXML private TextField Answer_A;
    @FXML private TextField Answer_B;
    @FXML private TextField Answer_C;
    @FXML private TextField Answer_D;
    @FXML private TextField Question;
    @FXML private TextField Right_Answer;
    @FXML private Pane finished;

    private int totalQuestions;
    private int currentQuestion = 1;

    @FXML
    private void save(ActionEvent event) {
        SetExam exam = new SetExam();
        exam.Save_Button();
    }

    @FXML
    public void get_number_of_questions(ActionEvent event) {
        try {
            totalQuestions = Integer.parseInt(Number_of_Questions.getText());
            currentQuestion = 1;
            QuestionNumber.setText("Question " + currentQuestion);
            finished.setVisible(false);
        } catch (NumberFormatException e) {
            QuestionNumber.setText("Invalid number");
        }
    }

    @FXML
    public void Next_Button(ActionEvent event) {
        if (currentQuestion < totalQuestions) {
            currentQuestion++;
            clearFields();
            QuestionNumber.setText("Question " + currentQuestion);

            if (currentQuestion == totalQuestions) {
                finished.setVisible(true);
            } else {
                finished.setVisible(false);
            }
        }
    }

    @FXML
    public void Back_Button(ActionEvent event) {
        if (currentQuestion > 1) {
            currentQuestion--;
            clearFields();
            QuestionNumber.setText("Question " + currentQuestion);
            finished.setVisible(false);
        }
    }

    private void clearFields() {
        Answer_A.clear();
        Answer_B.clear();
        Answer_C.clear();
        Answer_D.clear();
        Question.clear();
        Right_Answer.clear();
    }
}


