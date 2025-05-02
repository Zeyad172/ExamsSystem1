package org.example.examssystem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

public class StageQuationcontroller implements Initializable {
  protected ArrayList<Question> currentExam = new ArrayList<>(30);
  protected ArrayList<Button> buttons = new ArrayList<>(Collections.nCopies(30, null));
  protected String currentQuestionType;

  @FXML
  Label l1;
  @FXML
  Label answerA;
  @FXML
  Label answerB;
  @FXML
  Label answerC;
  @FXML
  Label answerD;
  @FXML
  RadioButton r1;
  @FXML
  RadioButton r2;
  @FXML
  RadioButton r3;
  @FXML
  RadioButton r4;
  @FXML
  Button b1 = buttons.getFirst();
  @FXML
  Button b2 = buttons.get(1);
  @FXML
  Button b3 = buttons.get(2);
  @FXML
  Button b4 = buttons.get(3);
  @FXML
  Button b5 = buttons.get(4);
  @FXML
  Button b6= buttons.get(5);
  @FXML
  Button b7 = buttons.get(6);
  @FXML
  Button b8= buttons.get(7);
  @FXML
  Button b9 = buttons.get(8);
  @FXML
  Button b10= buttons.get(9);
  @FXML
  Button b11 = buttons.get(10);
  @FXML
  Button b12= buttons.get(11);
  @FXML
  Button b13 = buttons.get(12);
  @FXML
  Button b14= buttons.get(13);
  @FXML
  Button b15 = buttons.get(14);
  @FXML
  Button b16= buttons.get(15);
  @FXML
  Button b17 = buttons.get(16);
  @FXML
  Button b18= buttons.get(17);
  @FXML
  Button b19 = buttons.get(18);
  @FXML
  Button b20= buttons.get(19);
  @FXML
  Button b21 = buttons.get(20);
  @FXML
  Button b22= buttons.get(21);
  @FXML
  Button b23 = buttons.get(22);
  @FXML
  Button b24= buttons.get(23);
  @FXML
  Button b25 = buttons.get(24);
  @FXML
  Button b26= buttons.get(25);
  @FXML
  Button b27 = buttons.get(26);
  @FXML
  Button b28= buttons.get(27);
  @FXML
  Button b29 = buttons.get(28);
  @FXML
  Button b30= buttons.get(29);

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    for (Button b : buttons) {
      if (b != null) {
        b.setVisible(false);
      }
    }

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    try {
      Connection connection = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/project", "root", "mohamed2005");
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM question");

      while (rs.next()) {
        Question question = null;
        if (Objects.equals(rs.getString("questionType"), "MCQ")) {
          question = new MCQ();
          question.question = rs.getString("question");
          question.questionType = rs.getString("questionType");
          ((MCQ) question).answerA = rs.getString("answerA");
          ((MCQ) question).answerB = rs.getString("answerB");
          ((MCQ) question).answerC = rs.getString("answerC");
          ((MCQ) question).answerD = rs.getString("answerD");
        } else if (Objects.equals(rs.getString("questionType"), "TF")) {
          question = new TF();
          question.question = rs.getString("question");
          question.questionType = rs.getString("questionType");
          ((TF) question).answerA = rs.getString("answerA"); // "T"
          ((TF) question).answerB = rs.getString("answerB"); // "F"
        }
        currentExam.add(question);
      }

      for (int i = 0; i < currentExam.size(); i++) {
        if (buttons.get(i) != null) {
          buttons.get(i).setVisible(true);
          int index = i;
          buttons.get(i).setOnAction(e -> showQuestion(index));
        }
      }

      if (!currentExam.isEmpty()) {
        showQuestion(0);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void showQuestion(int index) {
    Question q = currentExam.get(index);
    l1.setText(q.question);
    if (q instanceof MCQ) {
      answerA.setText(((MCQ) q).answerA);
      answerB.setText(((MCQ) q).answerB);
      answerC.setText(((MCQ) q).answerC);
      answerD.setText(((MCQ) q).answerD);
      r1.setVisible(true);
      r2.setVisible(true);
      r3.setVisible(true);
      r4.setVisible(true);
    } else if (q instanceof TF) {
      answerA.setText(((TF) q).answerA); // "T"
      answerB.setText(((TF) q).answerB); // "F"
      r1.setVisible(true);
      r2.setVisible(true);
      r3.setVisible(false);
      r4.setVisible(false);
    }
  }
}
