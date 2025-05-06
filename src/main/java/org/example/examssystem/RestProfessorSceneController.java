package org.example.examssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

@RestController
public class RestProfessorSceneController {

    @GetMapping("/users/professorAuth/{username}/{password}")
    public boolean professorAuth(@PathVariable String username,@PathVariable String password) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
        Statement statement = connection.createStatement();
        ResultSet professorsData = statement.executeQuery("SELECT * FROM professors");
        while(professorsData.next()){
            if(Objects.equals(professorsData.getString(1), username)&& Objects.equals(professorsData.getString(2), password))
            {
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
    public boolean studentAuth(@PathVariable String username,@PathVariable String password) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
        Statement statement = connection.createStatement();
        ResultSet studentsData = statement.executeQuery("SELECT * FROM students");
        while(studentsData.next()){
            if(Objects.equals(studentsData.getString(1), username)&& Objects.equals(studentsData.getString(2), password))
            {
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
        ArrayList<String>ButtonNames = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
        Statement statement = connection.createStatement();
        statement.execute("USE subjectpastresults");
        ResultSet rs = statement.executeQuery("SHOW TABLES");
        while(rs.next()){
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
        Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
        Statement statement2 = connection2.createStatement();
        statement2.execute("USE subjectpastresults");
        ResultSet rs2 = statement2.executeQuery("SELECT * FROM "+ b);
        ArrayList<Result>resultArrayList = new ArrayList<>();
        while(rs2.next()){
            Result result = new Result();
            result.studentName=rs2.getString(1);
            result.studentID=rs2.getString(2);
            result.studentScore=rs2.getString(3);
            resultArrayList.add(result);
        }
        rs2.close();
        statement2.close();
        return resultArrayList;
    }
}
