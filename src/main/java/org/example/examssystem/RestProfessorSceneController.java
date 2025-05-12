package org.example.examssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RestProfessorSceneController {
    public static String studentName;
    public static int studentID;

    @GetMapping("/users/professorAuth/{username}/{password}")
    public boolean professorAuth(@PathVariable String username, @PathVariable String password) throws ClassNotFoundException, SQLException {
        username = username.replaceAll(","," ");
        password = password.replaceAll(","," ");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
        Statement statement = connection.createStatement();
        ResultSet professorsData = statement.executeQuery("SELECT * FROM professors");
        while (professorsData.next()) {
            if (Objects.equals(professorsData.getString(1), username) && Objects.equals(professorsData.getString(2), password)) {
                professorsData.close();
                statement.close();
                return true;
            }
        }
        professorsData.close();
        statement.close();
        return false;
    }

    @GetMapping("/users/studentAuth/{username}/{password}")
    public boolean studentAuth(@PathVariable String username, @PathVariable String password) throws ClassNotFoundException, SQLException {
        username = username.replaceAll(","," ");
        password = password.replaceAll(","," ");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
        Statement statement = connection.createStatement();
        ResultSet studentsData = statement.executeQuery("SELECT * FROM students");
        while (studentsData.next()) {
            if (Objects.equals(studentsData.getString(1), username) && Objects.equals(studentsData.getString(2), password)) {
                studentsData.close();
                statement.close();
                return true;
            }
        }
        studentsData.close();
        statement.close();
        return false;
    }

    @GetMapping("/users/setButtons")
    public ArrayList<String> getButtonsNames() throws ClassNotFoundException, SQLException {
        ArrayList<String> ButtonNames = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
        Statement statement = connection.createStatement();
        statement.execute("USE subjectpastresults");
        ResultSet rs = statement.executeQuery("SHOW TABLES");
        while (rs.next()) {
            String buttonName = new String();
            buttonName = rs.getString(1);
            ButtonNames.add(buttonName);
        }
        rs.close();
        statement.close();
        return ButtonNames;
    }

    @GetMapping("/users/setButtonAction/{b}")
    public ArrayList<Result> getResults(@PathVariable String b) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
        Statement statement2 = connection2.createStatement();
        statement2.execute("USE subjectpastresults");
        ResultSet rs2 = statement2.executeQuery("SELECT * FROM " + b);
        ArrayList<Result> resultArrayList = new ArrayList<>();
        while (rs2.next()) {
            Result result = new Result();
            result.studentName = rs2.getString(1);
            result.studentID = rs2.getString(2);
            result.studentScore = rs2.getString(3);
            resultArrayList.add(result);
        }
        rs2.close();
        statement2.close();
        return resultArrayList;
    }

    @GetMapping("/admin/addStudent/{id}/{name}/{password}")
    public void addStudent(@PathVariable int id, @PathVariable String name, @PathVariable String password) {
        name = name.replaceAll(",", " ");
        password = password.replaceAll(",", " ");
        System.out.println(name);
        System.out.println(password);
        String sql = "INSERT INTO nourdb.students (id, name, department) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            System.out.println("This student already exists.");
        }
    }

    @GetMapping("/admin/addProfessor/{id}/{name}/{password}/{s1}/{s2}/{s3}")
    public void addProfessor(@PathVariable int id, @PathVariable String name, @PathVariable String password, @PathVariable String s1, @PathVariable String s2, @PathVariable String s3) {
        System.out.println("ibrahim is here");
        name = name.replaceAll(","," ");
        password = password.replaceAll(","," ");
        s1 = s1.replaceAll(","," ");
        s2 = s2.replaceAll(","," ");
        s3 = s3.replaceAll(","," ");
        System.out.println(s3);
        String sql = "INSERT INTO nourdb.professors VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setString(4, s1);
            pstmt.setString(5, s2);
            pstmt.setString(6, s3);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Professor added successfully.");
        } catch (SQLException e) {

        }
    }
    @GetMapping("professor/setExamInformation/{examName}/{id}/{examTime}/{examType}/{examDate}")
    public void setExamInformation(@PathVariable String examName,@PathVariable String id,@PathVariable String examTime,@PathVariable String examDate,@PathVariable String examType){
        try {
            System.out.println("i am inside api");
            examName=examName.replaceAll(","," ");
            id=id.replaceAll(","," ");
            examTime=examTime.replaceAll(","," ");
            examType=examType.replaceAll(","," ");
            examDate=examDate.replaceAll(","," ");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");

            System.out.println("Database connection established successfully!");
            String sql = "INSERT INTO exams_info VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, examName);
            pstmt.setString(2, id);
            pstmt.setString(3, examTime);
            pstmt.setString(4, examType);
            pstmt.setString(5, examDate);

            pstmt.executeUpdate();



            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found", ex);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "Database connection failed", ex);

        }
    }
    @PostMapping("professor/createExam/{id}")
    public boolean createExam(@PathVariable String id, @RequestBody ArrayList<Questions>Question_Arr){
        System.out.println("iam inside new request");
        System.out.println(Question_Arr.get(1).Question);
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS `%s` (" +
                        "Question VARCHAR(150) NULL, " +
                        "AnswerA VARCHAR(150) NULL, " +
                        "AnswerB VARCHAR(150) NULL, " +
                        "AnswerC VARCHAR(150) NULL, " +
                        "AnswerD VARCHAR(150) NULL, " +
                        "Right_Answer VARCHAR(150) NULL, " +
                        "Type VARCHAR(150) NULL" +
                        ")", id);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully: " + id);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
            System.out.println("Database connection established successfully!");

            String sql1 = "INSERT INTO `" + id + "` VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sql2 = "INSERT INTO questions  VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt1 = con.prepareStatement(sql1);
            PreparedStatement pstmt2 = con.prepareStatement(sql2);

            for (Questions obj : Question_Arr) {
                //created table
                pstmt1.setString(1, obj.Question);
                pstmt1.setString(2, obj.Answer1);
                pstmt1.setString(3, obj.Answer2);
                pstmt1.setString(4, obj.Answer3);
                pstmt1.setString(5, obj.Answer4);
                pstmt1.setString(6, obj.Right_Answer);
                pstmt1.setString(7, obj.Type);
                pstmt1.executeUpdate();
                //Question bank table
                pstmt2.setString(1, obj.Question);
                pstmt2.setString(2, obj.Answer1);
                pstmt2.setString(3, obj.Answer2);
                pstmt2.setString(4, obj.Answer3);
                pstmt2.setString(5, obj.Answer4);
                pstmt2.setString(6, obj.Right_Answer);
                pstmt2.setString(7, obj.Type);
                pstmt2.executeUpdate();
            }

            con.close();
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    @GetMapping("student/SelectExam")
    public ArrayList<String> getButtons() throws SQLException {
        System.out.println("iam inside api");
        ArrayList<String>buttons = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
        Statement statement = con.createStatement();
        statement.execute("USE subjectexams");
        ResultSet rs = statement.executeQuery("SHOW TABLES");
        while(rs.next()){
            String buttonName = rs.getString(1);
            buttons.add(buttonName);
        }
        return buttons;
    }
    @GetMapping("student/loadQuestions/{dbName}")
    public ArrayList<Question> loadQuestions(@PathVariable String dbName) {
        System.out.println("iam inside api pt2");
        ArrayList<Question>restQuestionsArray = new ArrayList<>(50);
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/nourdb", "root", "Elnaggar2@");
            Statement statement = connection.createStatement();
            statement.execute("USE subjectexams");
            ResultSet rs = statement.executeQuery("SELECT * FROM " + dbName);
            while (rs.next()) {
                Question question = null;
                if (Objects.equals(rs.getString("questionType"), "MCQ")) {
                    question = new MCQ();
                    question.question = rs.getString("question");
                    question.questionType = rs.getString("questionType");
                    question.Right_Answer = rs.getString("correctAnswer");
                    ((MCQ) question).answerA = rs.getString("answerA");
                    ((MCQ) question).answerB = rs.getString("answerB");
                    ((MCQ) question).answerC = rs.getString("answerC");
                    ((MCQ) question).answerD = rs.getString("answerD");
                } else if (Objects.equals(rs.getString("questionType"), "TF")) {
                    question = new TF();
                    question.question = rs.getString("question");
                    question.questionType = rs.getString("questionType");
                    question.Right_Answer = rs.getString("correctAnswer");
                    ((TF) question).answerA = rs.getString("answerA");
                    ((TF) question).answerB = rs.getString("answerB");
                }
                System.out.println(question.question);
                restQuestionsArray.add(question);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(restQuestionsArray.get(1).questionType);
        return restQuestionsArray;
    }
    @GetMapping("student/gradeExam/{score}")
    public void setStudentResult(@PathVariable int score) throws SQLException {
        System.out.println("iam here pt3");
        String tempApi = StageQuationcontroller.tempdbname+"results";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
        PreparedStatement statement = con.prepareStatement("INSERT INTO subjectpastresults."+StageQuationcontroller.tempdbname+"results VALUES(?,?,?)");

        statement.setString(1,studentName);
        statement.setInt(2,studentID);
        statement.setInt(3,score);
        statement.executeUpdate();
        statement.close();
        System.out.println("iam finished");
    }
}

