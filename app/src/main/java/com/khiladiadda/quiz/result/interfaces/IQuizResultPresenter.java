package com.khiladiadda.quiz.result.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IQuizResultPresenter extends IBasePresenter {

    void startQuiz(String id);

    void getLeaderBoard(String id, int page, int limit);

}