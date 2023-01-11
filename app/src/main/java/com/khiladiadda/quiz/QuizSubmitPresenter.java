package com.khiladiadda.quiz;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.QuizSubmitRequest;
import com.khiladiadda.network.model.request.QuizSubmitRequestDetails;
import com.khiladiadda.network.model.response.QuestionDetails;
import com.khiladiadda.network.model.response.QuestionOptions;
import com.khiladiadda.network.model.response.QuizSubmitResponse;
import com.khiladiadda.quiz.interfaces.IQuizSubmitPresenter;
import com.khiladiadda.quiz.interfaces.IQuizSubmitView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class QuizSubmitPresenter implements IQuizSubmitPresenter {

    private IQuizSubmitView mView;
    private QuizSubmitInteractor mInteractor;
    private Subscription mQuizSubmitSubscription;

    public QuizSubmitPresenter(IQuizSubmitView mView) {
        this.mView = mView;
        mInteractor = new QuizSubmitInteractor();
    }

    @Override public void doQuizSubmit(long quizTotalTimeTaken, List<QuestionDetails> quizDetails, String categoryId, String quizId) {
        QuizSubmitRequest request = new QuizSubmitRequest();
        request.setQuizId(quizId);
        request.setCategoryId(categoryId);
        int skippedQuestions = 0;
        List<QuizSubmitRequestDetails> answers = new ArrayList<>();
        for (QuestionDetails quizDetail : quizDetails) {
            QuestionOptions selectedOption = quizDetail.getSelectedOption();
            if (selectedOption == null) {
                skippedQuestions++;
            } else {
                answers.add(new QuizSubmitRequestDetails(quizDetail.getId(), selectedOption.getId()));
            }
        }
        request.setAnswer(answers);
        request.setTimeTaken(quizTotalTimeTaken);
        request.setSkippedQuestions(skippedQuestions);
        mQuizSubmitSubscription = mInteractor.doQuizSubmit(request, mQuizSubmitApiListener);
    }

    @Override public void getLeaderboard(String id) {
    }

    private IApiListener<QuizSubmitResponse> mQuizSubmitApiListener = new IApiListener<QuizSubmitResponse>() {
        @Override public void onSuccess(QuizSubmitResponse response) {
            mView.onQuizSubmitComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onQuizSubmitFailure(error);
        }
    };

    @Override public void destroy() {
        if (mQuizSubmitSubscription != null && !mQuizSubmitSubscription.isUnsubscribed()) {
            mQuizSubmitSubscription.unsubscribe();
        }
    }

}