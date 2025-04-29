package org.example.examssystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Profressor {
    protected String Username="root";
    protected String Password="root";
    protected int UserID;
    protected String url = "jdbc:mysql://localhost:3306/mydb";
    protected String user = "root";
    protected Connection con;

    public void set_Username(String Username) {
        this.Username=Username;
    }
    public void set_Password(String Password) {
        this.Password=Password;
    }
    public String get_Username() {
        return Username;
    }
    public String get_Password() {
        return Password;
    }


    public void setExam(){


    }
}
