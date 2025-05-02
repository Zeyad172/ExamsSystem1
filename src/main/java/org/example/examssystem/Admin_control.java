package org.example.examssystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.application.Platform;

import java.sql.*;

public class Admin_control {
    static final String DB_URL = "jdbc:mysql://localhost:3306/UniversityDB";
    static final String USER = "MOMO";
    static final String PASS = "SecurePass123!";

    @FXML private TextField studentId;
    @FXML private TextField studentName;
    @FXML private PasswordField studentPass;
    @FXML private TextField prob;
    @FXML private TextField java;
    @FXML private TextField eng;
    @FXML private TextField logic;
    @FXML private TextField elec;
    @FXML private TextField work;

    @FXML private TextField profId;
    @FXML private TextField profName;
    @FXML private PasswordField profPass;
    @FXML private TextField sub1;
    @FXML private TextField sub2;
    @FXML private TextField sub3;

    @FXML private TextField searchField;
    @FXML private TextArea resultArea;

    @FXML private Button addStudentBtn;
    @FXML private Button addProfBtn;
    @FXML private Button searchBtn;

    @FXML private ImageView bgView;
    @FXML private GridPane grid;

    @FXML
    private void initialize() {
        // Bind background size (done in main class in original code)

        // Center window
        Platform.runLater(() -> {
            Stage stage = (Stage) grid.getScene().getWindow();
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((bounds.getHeight() - stage.getHeight()) / 8);
        });
    }

    @FXML
    private void handleAddStudent() {
        addStudent(
                Integer.parseInt(studentId.getText()),
                studentName.getText(),
                studentPass.getText(),
                prob.getText(),
                java.getText(),
                eng.getText(),
                logic.getText(),
                elec.getText(),
                work.getText()
        );
    }

    @FXML
    private void handleAddProfessor() {
        addProfessor(
                Integer.parseInt(profId.getText()),
                profName.getText(),
                profPass.getText(),
                sub1.getText(),
                sub2.getText(),
                sub3.getText()
        );
    }

    @FXML
    private void handleSearch() {
        String info = searchStudent(searchField.getText());
        resultArea.setText(info);
    }

    // Database methods (same as original)
    private void addStudent(int id, String name, String password, String prob, String java, String eng, String logic, String elec, String work) {
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
            System.out.println("This student already exists.");
        }
    }

    private void addProfessor(int id, String name, String password, String s1, String s2, String s3) {
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
            System.out.println("This professor already exists.");
        }
    }

    private String searchStudent(String identifier) {
        String sql = identifier.matches("\\d+") ?
                "SELECT * FROM Students WHERE ID = ?" :
                "SELECT * FROM Students WHERE Name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (identifier.matches("\\d+")) {
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