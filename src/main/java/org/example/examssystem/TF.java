package org.example.examssystem;
public class TF extends Question {
    public String answerA;  // "T"
    public String answerB;  // "F"
    public TF(){}
    public String getAnswerA(){return this.answerA;}
    public void setAnswerA(String answerA){
        this.answerA=answerA;
    }
    public String getAnswerB(){return this.answerB;}
    public void setAnswerB(String answerB){
        this.answerB=answerB;
    }
}
