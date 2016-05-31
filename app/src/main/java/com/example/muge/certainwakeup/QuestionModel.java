package com.example.muge.certainwakeup;

/**
 * Created by muge on 1.6.2016.
 */
public class QuestionModel {
    private String question;
    private String answer;

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionModel(String q,String a)
    {
        setQuestion(q);
        setAnswer(a);
    }
    public QuestionModel()
    {
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
