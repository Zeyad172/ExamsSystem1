package org.example.examssystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetExam {
    protected String url = "jdbc:mysql://localhost:3306/mydb";
    protected String user = "root";
    protected String Question;
    protected String Answer1;
    protected String Answer2;
    protected String Answer3;
    protected String Answer4;
    protected String Answer5;
    protected Connection con;
    //protected TextField Number_of_Questions;

    public void get_from_gui() {
        try {
            // 1. Load FXML interface
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/examssystem/InQuestions.fxml"));

            // 2. Set up background image (absolute path)
            ImageView background = new ImageView();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void Save_Button() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,"Elzooz3050@");

            System.out.println("Database connection established successfully!");
            Statement stmt = con.createStatement();
            String dbop =( "INSERT INTO questions VALUES('"+ Question + "','" + Answer1 +"','" + Answer2 +"','" + Answer3 +"','" + Answer4 + "','" + Answer5 +"');");
            stmt.execute(dbop);
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");
            while(rs.next()) {
                String has = rs.getString("Question");
                String s = rs.getString("Answera");
                String b = rs.getString("Answerb");
                String c = rs.getString("Answerc");
                String v = rs.getString("Answerd");
                String p = rs.getString("Answere");

                System.out.println(has + " " + s +" " + b + " " + c + " " + v + " " + p);
            }
            con.close();
            stmt.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found", ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
        }
    }

}
