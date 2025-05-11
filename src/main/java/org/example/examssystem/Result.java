package org.example.examssystem;

public class Result {
    protected String studentName;
    protected String studentID;
    protected String studentScore;
    Result(){

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
