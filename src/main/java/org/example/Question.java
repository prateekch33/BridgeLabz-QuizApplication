package org.example;

public class Question {
    private String question,answer,explanation,tags;
    public Question(String question,String answer,String explanation,String tags) {
        this.question=question;
        this.answer=answer;
        this.explanation=explanation;
        this.tags=tags;
    }
    public String getQuestion() {
        return this.question;
    }
    public String getAnswer() {
        return this.answer;
    }
    public String getExplanation() {
        return this.explanation;
    }
    public String getTags() {
        return this.tags;
    }
    public void setQuestion(String question) {
        this.question=question;
    }
    public void setAnswer(String answer) {
        this.answer=answer;
    }
    public void setExplanation(String explanation) {
        this.explanation=explanation;
    }
    public void setTags(String tags) {
        this.tags=tags;
    }
}
