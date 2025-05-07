package org.example.examssystem;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.*;
 import java.util.logging.Level;
 import java.util.logging.Logger;

public class DatabaseConn {

protected Connection con;

protected DatabaseConn() {}

static protected String url = "jdbc:mysql://localhost:3306/nourdb";
static protected String user = "root";
static protected String pass = "Elnaggar2@";

protected void createConnection() {
    try {
        // Load MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
         con = DriverManager.getConnection(url,user,"Elnaggar2@");

        System.out.println("Database connection established successfully!");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

        while(rs.next()) {
            String name = rs.getString("question");
            String password = rs.getString("choiceA");
            System.out.println(password + " " + name);
        }
        con.close();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found", ex);
    } catch (SQLException ex) {
        Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, "Database connection failed", ex);
    }
}
protected void createTable() {

}
protected void getTable(String name,String password) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url,user,pass);

        System.out.println("Database connection established successfully!");
        Statement stmt = con.createStatement();
        String dbop = "INSERT INTO USER VALUES(''"+ password + "','" + name +"'')";
        stmt.execute(dbop);
        ResultSet rs = stmt.executeQuery("SELECT * FROM Question");
        while(rs.next()) {
            String has = rs.getString("question");
            String pass = rs.getString("choiceA");
            System.out.println(password + " " + has);
        }
        con.close();
        stmt.close();
    }catch (SQLException ex) {
        Logger.getLogger(DatabaseConn.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }


}
}
