package org.example.examssystem;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.*;
 import java.util.logging.Level;
 import java.util.logging.Logger;
public class DatabaseConn {
public Connection con;

    public Connection getCon() {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String pass = "Elzooz3050@#";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;

    }
}