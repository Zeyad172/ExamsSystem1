package org.example.examssystem;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.*;
 import java.util.logging.Level;
 import java.util.logging.Logger;

public class DatabaseConn {

protected Connection con;

protected DatabaseConn() {}

protected String url = "jdbc:mysql://localhost:3306/mydb";
protected String user = "root";
//protected String pass = "Elzooz3050@#";

protected void createConnection() {
    try {
        // Load MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
         con = DriverManager.getConnection(url,user,"Elzooz3050@");

        System.out.println("Database connection established successfully!");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");

        while(rs.next()) {
            String name = rs.getString("userscol");
            String password = rs.getString("names");
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
        con = DriverManager.getConnection(url,user,"Elzooz3050@");

        System.out.println("Database connection established successfully!");
        Statement stmt = con.createStatement();
        String dbop = "INSERT INTO USER VALUES(''"+ password + "','" + name +"'')";
        stmt.execute(dbop);
        ResultSet rs = stmt.executeQuery("SELECT * FROM USER");
        while(rs.next()) {
            String has = rs.getString("userscol");
            String pass = rs.getString("names");
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
