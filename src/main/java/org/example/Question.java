package org.example;

public class Question {
    private String question,answer;
    public Question(String question,String answer) {
        this.question=question;
        this.answer=answer;
    }
    public String getQuestion() {
        return this.question;
    }
    public String qetAnswer() {
        return this.answer;
    }
    public void setQuestion(String question) {
        this.question=question;
    }
    public void setAnswer(String answer) {
        this.answer=answer;
    }
}
