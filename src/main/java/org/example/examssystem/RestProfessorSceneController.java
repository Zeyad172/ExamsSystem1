package org.example.examssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RestProfessorSceneController {

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
}
