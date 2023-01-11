package com.khiladiadda.quiz.list.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IQuizListPresenter extends IBasePresenter {

    void startQuiz(String id);

    void getQuizList(String id, boolean upcoming, boolean past, boolean live);

    void getUserPlayedQuiz(String id);

}