package org.example.examssystem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MCQ  extends Question {
    @JsonProperty("answerA")
    public String answerA;
    @JsonProperty("answerB")
    public String answerB;
    @JsonProperty("answerC")
    public String answerC;
    @JsonProperty("answerD")
    public String answerD;
    public MCQ(){}
    public String getAnswerA(){return this.answerA;}
    public void setAnswerA(String answerA){
        this.answerA=answerA;
    }
    public String getAnswerB(){return this.answerB;}
    public void setAnswerB(String answerB){
        this.answerB=answerB;
    }
    public String getAnswerC(){return this.answerC;}
    public void setAnswerC(String answerC){
        this.answerC=answerC;
    }
    public String getAnswerD(){return this.answerD;}
    public void setAnswerD(String answerD){
        this.answerD=answerD;
    }
}
