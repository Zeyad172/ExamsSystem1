package org.example.examssystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateNewTable {
/* sql code
* SET SQL_SAFE_UPDATES = 0;
UPDATE Questions SET Right_Answer='MONAMED' WHERE Question='ZYAD';
SET SQL_SAFE_UPDATES = 1;
* */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root"; // e.g., "root"
    private static final String PASS = "Elzooz3050@#";
    public void createQuestionsTable(String tableName) {
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "Question VARCHAR(150) NULL, " +
                        "AnswerA VARCHAR(150) NULL, " +
                        "AnswerB VARCHAR(150) NULL, " +
                        "AnswerC VARCHAR(150) NULL, " +
                        "AnswerD VARCHAR(150) NULL, " +
                        "Right_Answer VARCHAR(150) NULL, " +
                        "Type VARCHAR(150) NULL" +
                        ")", tableName);
        System.out.println("Creating table " + tableName);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Table created successfully: " + tableName);

        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
