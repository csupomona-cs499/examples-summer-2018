package edu.cpp.cs499.demoapp.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionSet {

    private List<Question> questionList;

    public QuestionSet() {
        this.questionList = new ArrayList<>();
    }

    public void addQuestion(String title, boolean correctAnswer) {
        Question q = new Question();
        q.setTitle(title);
        q.setCorrectAnswer(correctAnswer);
        questionList.add(q);
    }

    public Question getQuestion(int index) {
        return questionList.get(index);
    }

    public int getSize() {
        return questionList.size();
    }
}
