package org.example.examssystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

public class Admin_Page extends Application {

    static final String DB_URL = "jdbc:mysql://localhost:3306/UniversityDB";
    static final String USER = "MOMO";
    static final String PASS = "SecurePass123!";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Admin Panel");

        // Inputs
        TextField studentId = new TextField();
        TextField studentName = new TextField();
        PasswordField studentPass = new PasswordField();
        TextField prob = new TextField();
        TextField java = new TextField();
        TextField eng = new TextField();
        TextField logic = new TextField();
        TextField elec = new TextField();
        TextField work = new TextField();

        TextField profId = new TextField();
        TextField profName = new TextField();
        PasswordField profPass = new PasswordField();
        TextField sub1 = new TextField();
        TextField sub2 = new TextField();
        TextField sub3 = new TextField();

        TextField searchField = new TextField();
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        Button addStudentBtn = new Button("Add Student");
        Button addProfBtn = new Button("Add Professor");
        Button searchBtn = new Button("Search Student");

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        // Student
        grid.add(new Label("Student ID:"), 0, 0); grid.add(studentId, 1, 0);
        grid.add(new Label("Name:"), 0, 1); grid.add(studentName, 1, 1);
        grid.add(new Label("Password:"), 0, 2); grid.add(studentPass, 1, 2);
        grid.add(new Label("Probability:"), 0, 3); grid.add(prob, 1, 3);
        grid.add(new Label("Java:"), 0, 4); grid.add(java, 1, 4);
        grid.add(new Label("English Reports:"), 0, 5); grid.add(eng, 1, 5);
        grid.add(new Label("Logic Design:"), 0, 6); grid.add(logic, 1, 6);
        grid.add(new Label("Electronics:"), 0, 7); grid.add(elec, 1, 7);
        grid.add(new Label("Worshops:"), 0, 8); grid.add(work, 1, 8);
        grid.add(addStudentBtn, 1, 9);

        // Professor
        grid.add(new Label("Professor ID:"), 0, 11); grid.add(profId, 1, 11);
        grid.add(new Label("Name:"), 0, 12); grid.add(profName, 1, 12);
        grid.add(new Label("Password:"), 0, 13); grid.add(profPass, 1, 13);
        grid.add(new Label("Subject1:"), 0, 14); grid.add(sub1, 1, 14);
        grid.add(new Label("Subject2:"), 0, 15); grid.add(sub2, 1, 15);
        grid.add(new Label("Subject3:"), 0, 16); grid.add(sub3, 1, 16);
        grid.add(addProfBtn, 1, 17);

        // Search
        grid.add(new Label("Search Student by ID/Name:"), 0, 19); grid.add(searchField, 1, 19);
        grid.add(searchBtn, 1, 20);
        grid.add(resultArea, 0, 21, 2, 3);

        // Button Actions
        addStudentBtn.setOnAction(e -> addStudent(
                Integer.parseInt(studentId.getText()), studentName.getText(), studentPass.getText(),
                prob.getText(), java.getText(), eng.getText(), logic.getText(), elec.getText(), work.getText()
        ));

        addProfBtn.setOnAction(e -> addProfessor(
                Integer.parseInt(profId.getText()), profName.getText(), profPass.getText(),
                sub1.getText(), sub2.getText(), sub3.getText()
        ));

        searchBtn.setOnAction(e -> {
            String info = searchStudent(searchField.getText());
            resultArea.setText(info);
        });

        // Scene
        Scene scene = new Scene(grid, 500, 850);
        stage.setScene(scene);
        stage.show();
    }

    // Database Methods:
    static void addStudent(int id, String name, String password, String prob, String java, String eng, String logic, String elec, String work) {
        String sql = "INSERT INTO Students (ID, Name, Password, Probability, Java, English_Reports, Logic_Design, Electronics, Worshops) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setString(4, prob);
            pstmt.setString(5, java);
            pstmt.setString(6, eng);
            pstmt.setString(7, logic);
            pstmt.setString(8, elec);
            pstmt.setString(9, work);
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addProfessor(int id, String name, String password, String s1, String s2, String s3) {
        String sql = "INSERT INTO Professors (ID, Name, Password, Subject1, Subject2, Subject3) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setString(4, s1);
            pstmt.setString(5, s2);
            pstmt.setString(6, s3);
            pstmt.executeUpdate();
            System.out.println("Professor added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static String searchStudent(String identifier) {
        String sql;
        boolean isNumeric = identifier.matches("\\d+");
        sql = isNumeric ? "SELECT * FROM Students WHERE ID = ?" : "SELECT * FROM Students WHERE Name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (isNumeric) {
                pstmt.setInt(1, Integer.parseInt(identifier));
            } else {
                pstmt.setString(1, identifier);
            }

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return "ID: " + rs.getInt("ID") + "\n"
                        + "Name: " + rs.getString("Name") + "\n"
                        + "Password: " + rs.getString("Password") + "\n"
                        + "Probability: " + rs.getString("Probability") + "\n"
                        + "Java: " + rs.getString("Java") + "\n"
                        + "English Reports: " + rs.getString("English_Reports") + "\n"
                        + "Logic Design: " + rs.getString("Logic_Design") + "\n"
                        + "Electronics: " + rs.getString("Electronics") + "\n"
                        + "Worshops: " + rs.getString("Worshops");
            } else {
                return "Student not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred.";
        }
    }
}
