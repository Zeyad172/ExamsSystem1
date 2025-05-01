package org.example.examssystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
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

        // Input fields
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

        // Grid layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(12);
        grid.setVgap(10);

        Label[] labels = new Label[]{
                new Label("Student ID:"), new Label("Name:"), new Label("Password:"),
                new Label("Probability:"), new Label("Java:"), new Label("English Reports:"),
                new Label("Logic Design:"), new Label("Electronics:"), new Label("Worshops:"),
                new Label("Professor ID:"), new Label("Name:"), new Label("Password:"),
                new Label("Subject1:"), new Label("Subject2:"), new Label("Subject3:"),
                new Label("Search Student by ID/Name:")
        };

        for (Label label : labels) {
            label.setStyle("-fx-text-fill: #142547; -fx-font-weight: bold;");
        }

        // Student Fields
        grid.add(labels[0], 0, 0); grid.add(studentId, 1, 0);
        grid.add(labels[1], 0, 1); grid.add(studentName, 1, 1);
        grid.add(labels[2], 0, 2); grid.add(studentPass, 1, 2);
        grid.add(labels[3], 0, 3); grid.add(prob, 1, 3);
        grid.add(labels[4], 0, 4); grid.add(java, 1, 4);
        grid.add(labels[5], 0, 5); grid.add(eng, 1, 5);
        grid.add(labels[6], 0, 6); grid.add(logic, 1, 6);
        grid.add(labels[7], 0, 7); grid.add(elec, 1, 7);
        grid.add(labels[8], 0, 8); grid.add(work, 1, 8);
        grid.add(addStudentBtn, 1, 9);

        // Professor Fields
        grid.add(labels[9], 0, 11); grid.add(profId, 1, 11);
        grid.add(labels[10], 0, 12); grid.add(profName, 1, 12);
        grid.add(labels[11], 0, 13); grid.add(profPass, 1, 13);
        grid.add(labels[12], 0, 14); grid.add(sub1, 1, 14);
        grid.add(labels[13], 0, 15); grid.add(sub2, 1, 15);
        grid.add(labels[14], 0, 16); grid.add(sub3, 1, 16);
        grid.add(addProfBtn, 1, 17);

        // Search
        grid.add(labels[15], 0, 19); grid.add(searchField, 1, 19);
        grid.add(searchBtn, 1, 20);
        grid.add(resultArea, 0, 21, 2, 3);

        // Buttons Style
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;";
        addStudentBtn.setStyle(buttonStyle);
        addProfBtn.setStyle(buttonStyle);
        searchBtn.setStyle(buttonStyle);

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

        // ✅ Background Image using ImageView and StackPane
        ImageView bgView = new ImageView();
        try {
            Image bgImage = new Image(new File("C:\\Users\\Omnya\\IdeaProjects\\ExamsSystem1\\src\\main\\java\\org\\example\\examssystem\\WhatsApp Image 2025-05-01 at 12.02.51_3fd4dbd0.jpg").toURI().toString());
            bgView.setImage(bgImage);
            bgView.setPreserveRatio(false);
            bgView.setOpacity(0.8); // optional transparency
        } catch (Exception e) {
            System.err.println("Background image could not be loaded: " + e.getMessage());
        }

        StackPane root = new StackPane(bgView, grid);
        Scene scene = new Scene(root, 1000, 800);

        // Bind background size to window
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.show();

        // Center window
        Platform.runLater(() -> {
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((bounds.getHeight() - stage.getHeight()) / 8);
        });
    }

    // Database Functions
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
            System.out.println("This student already exists.");
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
            System.out.println("This professor already exists.");
        }
    }

    static String searchStudent(String identifier) {
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
