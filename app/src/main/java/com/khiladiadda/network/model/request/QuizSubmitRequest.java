package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuizSubmitRequest {

    @SerializedName("category_id") @Expose private String categoryId;
    @SerializedName("quiz_id") @Expose private String quizId;
    @SerializedName("time_taken") @Expose private long timeTaken;
    @SerializedName("skipped_questions") @Expose private int skippedQuestions;
    @SerializedName("answer") @Expose private List<QuizSubmitRequestDetails> answer = new ArrayList<>();

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getSkippedQuestions() {
        return skippedQuestions;
    }

    public void setSkippedQuestions(int skippedQuestions) {
        this.skippedQuestions = skippedQuestions;
    }

    public List<QuizSubmitRequestDetails> getAnswer() {
        return answer;
    }

    public void setAnswer(List<QuizSubmitRequestDetails> answer) {
        this.answer = answer;
    }

}