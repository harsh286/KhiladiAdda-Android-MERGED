package com.khiladiadda.quiz.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.response.QuestionDetails;

import java.util.List;

public interface IQuizSubmitPresenter extends IBasePresenter {

    void doQuizSubmit(long quizTotalTimeTaken, List<QuestionDetails> quizDetails, String categoryId, String quizId);

    void getLeaderboard(String id);

}