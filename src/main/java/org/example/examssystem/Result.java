package org.example.examssystem;

public class Result {
    public String studentName;
    public String studentID;
    public String studentScore;
    public Result(){

    }
    Result(String studentName,String studentID,String studentScore){
        this.studentName=studentName;
        this.studentID=studentID;
        this.studentScore=studentScore;
    }
    public String getStudentName(){
        return this.studentName;
    }
    public String getStudentID(){
        return this.studentID;
    }
    public String getStudentScore(){
        return this.studentScore;
    }

}
