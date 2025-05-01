package org.example.examssystem;

import java.sql.*;

public class Admin_Page {
    static final String DB_URL = "jdbc:mysql://localhost:3306/UniversityDB";
    static final String USER = "MOMO";
    static final String PASS = "SecurePass123!";

    enum Course {
        Probability, Java, English_Reports, Logic_Design, Electronics, Worshops
    }

    // Check if student exists
    public static boolean studentExists(int id) {
        String sql = "SELECT COUNT(*) FROM Students WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add student with duplicate check and subject grades
    public static void addStudent(int id, String name, String password,
                                  String probability, String java, String englishReports,
                                  String logicDesign, String electronics, String worshops) {
        if (studentExists(id)) {
            System.out.println("Error: Student with ID " + id + " already exists!");
            return;
        }

        String sql = "INSERT INTO Students (ID, Name, Password, Probability, Java, English_Reports, Logic_Design, Electronics, Worshops) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setString(4, probability);
            pstmt.setString(5, java);
            pstmt.setString(6, englishReports);
            pstmt.setString(7, logicDesign);
            pstmt.setString(8, electronics);
            pstmt.setString(9, worshops);
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if professor exists
    public static boolean professorExists(int id) {
        String sql = "SELECT COUNT(*) FROM Professors WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add professor with duplicate check
    public static void addProfessor(int id, String name, String password, Course subject1, Course subject2, Course subject3) {
        if (professorExists(id)) {
            System.out.println("Error: Professor with ID " + id + " already exists!");
            return;
        }

        String sql = "INSERT INTO Professors (ID, Name, Password, Subject1, Subject2, Subject3) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setString(4, subject1.name());
            pstmt.setString(5, subject2.name());
            pstmt.setString(6, subject3.name());
            pstmt.executeUpdate();
            System.out.println("Professor added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void searchStudent(String identifier) {
        String sql;
        boolean isNumeric = identifier.matches("\\d+");  // Check if input is numeric (ID)

        if (isNumeric) {
            sql = "SELECT * FROM Students WHERE ID = ?";
        } else {
            sql = "SELECT * FROM Students WHERE Name = ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (isNumeric) {
                pstmt.setInt(1, Integer.parseInt(identifier));
            } else {
                pstmt.setString(1, identifier);
            }

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Password: " + rs.getString("Password"));
                System.out.println("Probability: " + rs.getString("Probability"));
                System.out.println("Java: " + rs.getString("Java"));
                System.out.println("English_Reports: " + rs.getString("English_Reports"));
                System.out.println("Logic_Design: " + rs.getString("Logic_Design"));
                System.out.println("Electronics: " + rs.getString("Electronics"));
                System.out.println("Worshops: " + rs.getString("Worshops"));
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Add a student with grades for each course
        addStudent(7, "Kamolia", "pass1234", "A", "B+", "A", "B", "C", "A");

        // Add a professor with 3 subjects
        addProfessor(7, "Dr.Ali", "pass4556", Course.Electronics, Course.Worshops, Course.Probability);
        searchStudent("Kamolia");
    }
}
