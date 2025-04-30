package org.example.examssystem;
import javafx.scene.control.TextField;
abstract public class Employee {
    protected String Name;
    protected String Email;
    protected String Password;
    protected int UserID;
    // to check username & Password
    public boolean check_username(){
        boolean check = false;
        TextField username = new TextField();
        String check_name= username.getText();
        TextField pass = new TextField();
        String check_pass= pass.getText();

         if(check_name.equals(Name)){

         }
return check;
    }
}
