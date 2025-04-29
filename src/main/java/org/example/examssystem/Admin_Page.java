package org.example.examssystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Admin_Page {
    // Replace with your actual MySQL credentials
    static final String DB_URL = "jdbc:mysql://localhost:3306/UniversityDB";
    static final String USER = "MOMO";
    static final String PASS = "SecurePass123!";
    public static void addStudent(int id, String name, String password) {
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
    public static void addProfessor(int id, String name, String password, String subject1, String subject2, String subject3) {
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
        // Example usage:
        addStudent(1, "ZooZ","pass123");
        addProfessor(1, "Dr.Bably", "pass456", "Logic", "Electronics", "CS");
    }
}
