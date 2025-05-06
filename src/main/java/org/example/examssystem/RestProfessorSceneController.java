package org.example.examssystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;

@RestController
public class RestProfessorSceneController {

    @GetMapping("/users/professorAuth")
    public ArrayList<ProfessorData> professorAuth() throws ClassNotFoundException, SQLException {
        ArrayList<ProfessorData>professorsDataArraylist = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nourdb","root","Elnaggar2@");
        Statement statement = connection.createStatement();
        ResultSet professorsData = statement.executeQuery("SELECT * FROM professors");
        while(professorsData.next()){
            ProfessorData temp = new ProfessorData();
            temp.professorID=professorsData.getString(1);
            temp.professorPassword=professorsData.getString(2);
            temp.subject1=professorsData.getString(3);
            temp.subject2=professorsData.getString(4);
            temp.subject3=professorsData.getString(5);
            professorsDataArraylist.add(temp);
        }
        professorsData.close();
        statement.close();
        return professorsDataArraylist;
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
