package org.example.examssystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateNewTable {
/* sql code
* SET SQL_SAFE_UPDATES = 0;
UPDATE Questions SET Right_Answer='NONAMED' WHERE Question='ZYAD';
SET SQL_SAFE_UPDATES = 1;
* */
private String tableName;
private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root"; // e.g., "root"
    private static final String PASS = "Elzooz3050@#";

    public static void createQuestionsTable(String tableName) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // SQL to create the table (exact structure from your screenshot)
            String sql = "CREATE TABLE IF NOT EXISTS questions (" +
                    "Question VARCHAR(150) NULL," +
                    "AnswerA VARCHAR(150) NULL," +      // Note: Typo in column name (AnswerA?)
                    "AswerB VARCHAR(150) NULL," +       // Typo (AnswerB?)
                    "AnswerC VARCHAR(150) NULL," +       // Typo (AnswerC?)
                    "AnswereD VARCHAR(150) NULL," +
                    "Rigth_Answer VARCHAR(150) NULL," +
                    "`Delete Access` VARCHAR(150) NULL" + // Backticks for spaces in column name
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

            stmt.executeUpdate(sql);
            System.out.println("Table 'questions' created successfully in 'mydb'!");

        } catch (SQLException e) {
            System.err.println("MySQL error: " + e.getMessage());
        }
    }
}
/*try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // SQL query to create the table (MySQL syntax)
            String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "Question TEXT NOT NULL," +
                "AnswerA TEXT NOT NULL," +
                "AnswerB TEXT NOT NULL," +
                "AnswerC TEXT NOT NULL," +
                "AnswerD TEXT NOT NULL," +
                "Right_Answer TEXT NOT NULL" +
                ");", tableName);

            stmt.executeUpdate(sql);
            System.out.println("MySQL table '" + tableName + "' created successfully!");

        } catch (SQLException e) {
            System.err.println("MySQL error: " + e.getMessage());
        }
    }*/