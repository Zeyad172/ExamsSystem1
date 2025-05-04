
package org.example.examssystem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.*;

public class StageQuationcontroller implements Initializable {
  protected ArrayList<Question> currentExam = new ArrayList<>(30);
  protected ArrayList<Button> buttons = new ArrayList<>();
  private ArrayList<String> Answers = new ArrayList<>(Collections.nCopies(30, null));

  private ToggleGroup group = new ToggleGroup();
  private int timeRemaining = 1 * 60;
  private Timer timer;
  private String dbName;

  @FXML
  Label l1,timerlable,finish;
  @FXML
  Label answerA,AnswerB,AnswerC,AnswerD;
  @FXML
  RadioButton r1,r2,r3,r4;
  @FXML
  Button b1, b2, b3, b4,b5,b6,b7,b8,b9,b10 ;
  @FXML
  Button b11, b12, b13, b14,b15,b16,b17,b18,b19,b20 ;
  @FXML
  Button b21, b22, b23, b24,b25,b26,b27,b28,b29,b30 ;
  @FXML
  Button save =new Button(),f,back;
  public void setReceivedButtonText(String text) {

    this.dbName = text;
    startTimer();
    loadQuestionsFromDatabase();
  }
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    buttons.addAll(Arrays.asList(b1, b2, b3, b4,b5,b6,b7,b8,b9,b10));
    buttons.addAll(Arrays.asList(b11, b12, b13, b14,b15,b16,b17,b18,b19,b20));
    buttons.addAll(Arrays.asList(b21, b22, b23, b24,b25,b26,b27,b28,b29,b30));

    for (Button b : buttons) {
      if (b != null) {
        b.setVisible(false);
      }
      r1.setToggleGroup(group);
      r2.setToggleGroup(group);
      r3.setToggleGroup(group);
      r4.setToggleGroup(group);

    }
  }
  private void loadQuestionsFromDatabase() {
    try {
      Connection connection = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/project", "root", "mohamed2005");
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT * FROM `" + dbName + "`");
      while (rs.next()) {
        Question question = null;
        if (Objects.equals(rs.getString("questionType"), "MCQ")) {
          question = new MCQ();
          question.question = rs.getString("question");
          question.questionType = rs.getString("questionType");
          ((MCQ) question).answerA = rs.getString("AnswerA");
          ((MCQ) question).answerB = rs.getString("AnswerB");
          ((MCQ) question).answerC = rs.getString("AnswerC");
          ((MCQ) question).answerD = rs.getString("AnswerD");
        } else if (Objects.equals(rs.getString("questionType"), "TF")) {
          question = new TF();
          question.question = rs.getString("question");
          question.questionType = rs.getString("questionType");
          ((TF) question).answerA = rs.getString("AnswerA");
          ((TF) question).answerB = rs.getString("AnswerB");
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
    group.selectToggle(null);
    Question q = currentExam.get(index);
    l1.setText(q.question);

    if (q instanceof MCQ) {
      r1.setText(((MCQ) q).answerA);
      r2.setText(((MCQ) q).answerB);
      r3.setText(((MCQ) q).answerC);
      r4.setText(((MCQ) q).answerD);
      r1.setVisible(true); r2.setVisible(true);
      r3.setVisible(true); r4.setVisible(true);
      answerA.setVisible(true); AnswerB.setVisible(true);
      AnswerC.setVisible(true); AnswerD.setVisible(true);
      back.setVisible(false);

    } else if (q instanceof TF) {
      r1.setText(((TF) q).answerA);
      r2.setText(((TF) q).answerB);
      r1.setVisible(true); r2.setVisible(true);
      r3.setVisible(false); r4.setVisible(false);
      AnswerC.setVisible(false); AnswerD.setVisible(false);
      back.setVisible(false);

    }
    if(Answers.get(index)!=null){
        if(Objects.equals(Answers.get(index), r1.getText()))
          r1.setSelected(true);
       else if(Objects.equals(Answers.get(index), r2.getText()))
          r2.setSelected(true);
        else if(Objects.equals(Answers.get(index), r3.getText()))
          r3.setSelected(true);
        else
        r4.setSelected(true);
    }
  }

  public void saveAnswer() {
    RadioButton selected = (RadioButton) group.getSelectedToggle();
    if (selected != null) {
      String answerText = selected.getText();
      Answers.add(answerText);
      System.out.println("Saved answer: " + answerText);
    } else {
      Answers.add("");
      System.out.println("No answer selected");
    }
  }
  @FXML
  private void saveAnswer(ActionEvent event) {
    saveAnswer();
  }



  private void startTimer() {
    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          int hours = timeRemaining / 3600;
          int minutes = (timeRemaining % 3600) / 60;
          int seconds = timeRemaining % 60;
          timerlable.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));

          if (timeRemaining <= 0) {
            timer.cancel();
            timerlable.setText("Time's up!");
            finishExam();
          }

          timeRemaining--;
        });
      }
    }, 0, 1000);
  }
  private void finishExam() {
    System.out.println("Time's up! Submitting exam...");
    finish.setVisible(true);
finish.setText("The Exam Finished");
    back.setVisible(true);
    r1.setVisible(false);
    r2.setVisible(false);
    r3.setVisible(false);
    r4.setVisible(false);
  this.save.setVisible(false);
    answerA.setVisible(false);
    AnswerD.setVisible(false);
    AnswerC.setVisible(false);
    AnswerB.setVisible(false);
    f.setVisible(false);
    l1.setVisible(false);
    timerlable.setVisible(false);
    for (Button btn : buttons) {
      btn.setVisible(false);
    }
  }
  @FXML
  private void finish (ActionEvent event) {
    finishExam();
  }



  }