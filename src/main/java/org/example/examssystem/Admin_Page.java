package org.example.examssystem;

import java.sql.*;

public class Admin_Page {
    static final String DB_URL = "jdbc:mysql://localhost:3306/UniversityDB";
    static final String USER = "MOMO";
    static final String PASS = "SecurePass123!";

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

    // Add student with duplicate check
    public static void addStudent(int id, String name, String password) {
        if (studentExists(id)) {
            System.out.println("Error: Student with ID " + id + " already exists!");
            return;
        }
        String sql = "INSERT INTO Students (ID, Name, Password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
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
    public static void addProfessor(int id, String name, String password, String subject1, String subject2, String subject3) {
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
            pstmt.setString(4, subject1);
            pstmt.setString(5, subject2);
            pstmt.setString(6, subject3);
            pstmt.executeUpdate();
            System.out.println("Professor added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addStudent(3, "Kamolia", "pass1234");
        addProfessor(3, "Dr.Aziz", "pass4556", "Logistic", "Electronic", "CSE");
    }
}