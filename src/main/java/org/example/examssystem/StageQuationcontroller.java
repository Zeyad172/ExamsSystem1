
package org.example.examssystem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.*;
public class StageQuationcontroller implements Initializable {
  protected ArrayList<Question> currentExam = new ArrayList<>(30);
  protected ArrayList<Question> originalExamOrder = new ArrayList<>(30);
  protected ArrayList<Button> buttons = new ArrayList<>();
  private ArrayList<String> Answers = new ArrayList<>(Collections.nCopies(30, ""));

  private ToggleGroup group = new ToggleGroup();
  private int timeRemaining = 1*60 * 60;
  private Timer timer;
  public static String tempdbname ;
  private String dbName;
  private int currentQuestionIndex,score;
  @FXML
  Label l1,timerlable,finish;

  @FXML
  RadioButton r1,r2,r3,r4;
  @FXML
  Button b1, b2, b3, b4,b5,b6,b7,b8,b9,b10 ;
  @FXML
  Button b11, b12, b13, b14,b15,b16,b17,b18,b19,b20 ;
  @FXML
  Button b21, b22, b23, b24,b25,b26,b27,b28,b29,b30 ;
  @FXML
  Button s,f,back;
  public String getDbName(){
    return this.dbName;
  }
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
      finish.setVisible(false);
    }
  }
  private void loadQuestionsFromDatabase() {
    try {
      String tempDbname = dbName;//to not mess with dbName
      tempDbname = tempDbname.replaceAll(" ",",");
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://192.168.252.15:8080/student/loadQuestions/"+tempDbname)).GET().build();
      HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
      ObjectMapper mapper = new ObjectMapper();
      ArrayList<Question>apiExam = mapper.readValue(response.body(), new TypeReference<ArrayList<Question>>() {});
        System.out.println("first question in api exam "+apiExam.get(1).question);
        for(int i=0;i<apiExam.size();i++){
          currentExam.add(apiExam.get(i));
        }

      originalExamOrder = new ArrayList<>(currentExam);
      Collections.shuffle(currentExam);
      for (int i = 0; i < currentExam.size(); i++) {
        if (buttons.get(i) != null) {
          buttons.get(i).setVisible(true);
          int index = i;
          buttons.get(i).setOnAction(e -> showQuestion(index));
        }}
      if (!currentExam.isEmpty()) {
        showQuestion(0);
      }
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }
  private void showQuestion(int index) {
    currentQuestionIndex = index;
    group.selectToggle(null); // إلغاء التحديد السابق
    Question q = currentExam.get(index);
    l1.setText(q.question);
    if (q instanceof MCQ) {
      r1.setText(((MCQ) q).answerA);
      r2.setText(((MCQ) q).answerB);
      r3.setText(((MCQ) q).answerC);
      r4.setText(((MCQ) q).answerD);
      r1.setVisible(true); r2.setVisible(true);
      r3.setVisible(true); r4.setVisible(true);

      back.setVisible(false);
    } else if (q instanceof TF) {
      r1.setText(((TF) q).answerA);
      r2.setText(((TF) q).answerB);
      r1.setVisible(true); r2.setVisible(true);
      r3.setVisible(false); r4.setVisible(false);

      back.setVisible(false);
    }
    // استرجاع الإجابة القديمة إن وُجدت
    if (index < Answers.size() && Answers.get(index) != null) {
      String savedAnswer = Answers.get(index);
      if (Objects.equals(savedAnswer, r1.getText())) r1.setSelected(true);
      else if (Objects.equals(savedAnswer, r2.getText())) r2.setSelected(true);
      else if (Objects.equals(savedAnswer, r3.getText())) r3.setSelected(true);
      else if (Objects.equals(savedAnswer, r4.getText())) r4.setSelected(true);
    }
  }
  public void saveAnswer() {
    RadioButton selected = (RadioButton) group.getSelectedToggle();
    String answerText = selected != null ? selected.getText() : "";
    // إذا كانت الإجابة محفوظة من قبل، نعدلها، وإذا لم تكن، نضيفها
    if (currentQuestionIndex < Answers.size()) {
      Answers.set(currentQuestionIndex, answerText);
    } else {
      // نملأ الفجوات إذا لازم
      while (Answers.size() < currentQuestionIndex) {
        Answers.add("");
      }
      Answers.add(answerText);
    }

    System.out.println("Saved answer for Q" + (currentQuestionIndex + 1) + ": " + answerText);
  }

  @FXML
  private void save_Answer(ActionEvent event) {
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
              try {
                  finishExam();
              } catch (IOException e) {
                  throw new RuntimeException(e);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
          }
          timeRemaining--;
        });
      }
    }, 0, 1000);
  }
  private void finishExam() throws IOException, InterruptedException {
    System.out.println("Time's up! Submitting exam...");
    finish.setVisible(true);
    back.setVisible(true);
    r1.setVisible(false);
    r2.setVisible(false);
    r3.setVisible(false);
    r4.setVisible(false);
    s.setVisible(false);
    f.setVisible(false);
    l1.setVisible(false);
    timerlable.setVisible(false);
    for (Button btn : buttons) {
      btn.setVisible(false);
    }
    gradeExam();
  }
  @FXML
  private void finish_button (ActionEvent event) throws IOException, InterruptedException {
    finishExam();
  }
  @FXML
  private void Back_button (ActionEvent event) throws IOException {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/frist_stage.fxml"));

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

      ImageView background = new ImageView();
      try {
        String imagePath = "D:\\projects programming\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\صورة واتساب بتاريخ 1446-11-06 في 01.42.33_d98fc2c8.jpg";
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
  private void gradeExam() throws IOException, InterruptedException {
    System.out.println(originalExamOrder.get(0).Right_Answer);
    System.out.println(originalExamOrder.get(1).Right_Answer);
    int correct = 0;
    for (int i = 0; i < currentExam.size(); i++) {
      Question shown = currentExam.get(i);
      String studentAnswer = (i < Answers.size()) ? Answers.get(i) : "";

      for (Question original : originalExamOrder) {
        if (shown.question.equals(original.question)) {
          if (studentAnswer != null && studentAnswer.equals(original.Right_Answer)) {
            correct++;
          }
          break;
        }
      }
    }
    System.out.println(correct);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://192.168.252.15:8080/student/gradeExam/"+score)).GET().build();
    client.send(request,HttpResponse.BodyHandlers.ofString());
  }}
//
//package org.example.examssystem;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//
//import java.net.URL;
//import java.sql.*;
//import java.util.*;
//
//public class StageQuationcontroller implements Initializable {
//  protected ArrayList<Question> currentExam = new ArrayList<>(30);
//  protected ArrayList<Button> buttons = new ArrayList<>();
//  private List<String> Answers = new ArrayList<>();
//  private int currentQuestionIndex = 0;
//  private ToggleGroup group = new ToggleGroup();
//  private int timeRemaining = 1 * 60;
//  private Timer timer;
//  private String dbName;
//
//  @FXML
//  Label l1,timerlable;
//  @FXML
//  Label answerA,AnswerB,AnswerC,AnswerD;
//  @FXML
//  RadioButton r1,r2,r3,r4;
//  @FXML
//  Button b1, b2, b3, b4,b5,b6,b7,b8,b9,b10 ;
//  @FXML
//  Button b11, b12, b13, b14,b15,b16,b17,b18,b19,b20 ;
//  @FXML
//  Button b21, b22, b23, b24,b25,b26,b27,b28,b29,b30 ;
//  @FXML
//  Button save;
//  public void setReceivedButtonText(String text) {
//
//    this.dbName = text;
//    startTimer();
//    loadQuestionsFromDatabase();
//  }
//  @Override
//  public void initialize(URL url, ResourceBundle resourceBundle) {
//    buttons.addAll(Arrays.asList(b1, b2, b3, b4,b5,b6,b7,b8,b9,b10));
//    buttons.addAll(Arrays.asList(b11, b12, b13, b14,b15,b16,b17,b18,b19,b20));
//    buttons.addAll(Arrays.asList(b21, b22, b23, b24,b25,b26,b27,b28,b29,b30));
//
//    for (Button b : buttons) {
//      if (b != null) {
//        b.setVisible(false);
//      }
//      r1.setToggleGroup(group);
//      r2.setToggleGroup(group);
//      r3.setToggleGroup(group);
//      r4.setToggleGroup(group);
//
//    }
//  }
//  private void loadQuestionsFromDatabase() {
//    try {
//      Connection connection = DriverManager.getConnection(
//              "jdbc:mysql://localhost:3306/project", "root", "mohamed2005");
//      Statement statement = connection.createStatement();
//      ResultSet rs = statement.executeQuery("SELECT * FROM `" + dbName + "`");
//      while (rs.next()) {
//        Question question = null;
//        if (Objects.equals(rs.getString("questionType"), "MCQ")) {
//          question = new MCQ();
//          question.question = rs.getString("question");
//          question.questionType = rs.getString("questionType");
//          ((MCQ) question).answerA = rs.getString("AnswerA");
//          ((MCQ) question).answerB = rs.getString("AnswerB");
//          ((MCQ) question).answerC = rs.getString("AnswerC");
//          ((MCQ) question).answerD = rs.getString("AnswerD");
//        } else if (Objects.equals(rs.getString("questionType"), "TF")) {
//          question = new TF();
//          question.question = rs.getString("question");
//          question.questionType = rs.getString("questionType");
//          ((TF) question).answerA = rs.getString("AnswerA");
//          ((TF) question).answerB = rs.getString("AnswerB");
//        }
//        currentExam.add(question);
//      }
//
//      for (int i = 0; i < currentExam.size(); i++) {
//        if (buttons.get(i) != null) {
//          buttons.get(i).setVisible(true);
//          int index = i;
//          buttons.get(i).setOnAction(e -> showQuestion(index));
//        }
//      }
//
//      if (!currentExam.isEmpty()) {
//        showQuestion(0);
//      }
//
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//
//
//
//  private void showQuestion(int index) {
//    group.selectToggle(null);
//    Question q = currentExam.get(index);
//    l1.setText(q.question);
//    if (q instanceof MCQ) {
//      r1.setText(((MCQ) q).answerA);
//      r2.setText(((MCQ) q).answerB);
//      r3.setText(((MCQ) q).answerC);
//      r4.setText(((MCQ) q).answerD);
//      r1.setVisible(true); r2.setVisible(true);
//      r3.setVisible(true); r4.setVisible(true);
//      answerA.setVisible(true); AnswerB.setVisible(true);
//      AnswerC.setVisible(true); AnswerD.setVisible(true);
//    } else if (q instanceof TF) {
//      r1.setText(((TF) q).answerA);
//      r2.setText(((TF) q).answerB);
//      r1.setVisible(true); r2.setVisible(true);
//      r3.setVisible(false); r4.setVisible(false);
//      AnswerC.setVisible(false); AnswerD.setVisible(false);
//    }
//  }
//
//  public void saveAnswer() {
//    RadioButton selected = (RadioButton) group.getSelectedToggle();
//    if (selected != null) {
//      String answerText = selected.getText();
//      Answers.add(answerText);
//      System.out.println("Saved answer: " + answerText);
//    } else {
//      Answers.add("");
//      System.out.println("No answer selected");
//    }
//  }
//  @FXML
//  private void saveAnswer(ActionEvent event) {
//    saveAnswer();
//  }
//
//
//
//  private void startTimer() {
//    timer = new Timer();
//    timer.scheduleAtFixedRate(new TimerTask() {
//      @Override
//      public void run() {
//        Platform.runLater(() -> {
//          int hours = timeRemaining / 3600;
//          int minutes = (timeRemaining % 3600) / 60;
//          int seconds = timeRemaining % 60;
//          timerlable.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));
//
//          if (timeRemaining <= 0) {
//            timer.cancel();
//            timerlable.setText("Time's up!");
//            finishExam();
//          }
//
//          timeRemaining--;
//        });
//      }
//    }, 0, 1000);
//  }
//  private void finishExam() {
//    System.out.println("Time's up! Submitting exam...");
//
//    for (Button btn : buttons) {
//      btn.setDisable(true);
//    }
//  }
//
//
//
//
//  }