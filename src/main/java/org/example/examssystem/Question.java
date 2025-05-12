package org.example.examssystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "questionType", // this must exist in the JSON!
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MCQ.class, name = "MCQ"),
        @JsonSubTypes.Type(value = TF.class, name = "TF") // if you have others
})

public class Question {
    @JsonProperty("question")
    public String question;
    @JsonProperty("questionType")
    public String questionType;
    @JsonProperty("Right_Answer")
    public String Right_Answer;
    public Question(){}
    public String getQuestion(){return this.question ;}
    public void setQuestion(String question){
        this.question=question;
    }
    public String getQuestionType(){return this.questionType ;}
    public void setQuestionType(String questionType){
        this.questionType=questionType;
    }
    public String getRight_Answer(){return this.Right_Answer ;}
    public void setRight_Answer(String Right_Answer){
        this.Right_Answer=Right_Answer;
    }
}
