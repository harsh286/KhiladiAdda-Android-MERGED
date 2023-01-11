package com.khiladiadda.quiz.splash;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.QuizQuestionResponse;

import rx.Subscription;

public class QuizSplashInteractor {

//    public Subscription getQuestions(String id, IApiListener<QuizQuestionResponse> listener) {
//        ApiManager manager = ApiManager.getInstance();
//        ApiService service = manager.createService();
//        return manager.createObservable(service.getQuizQuestion(id))
//                .subscribe(new SubscriberCallback<>(listener));
//    }

    public Subscription getQuizTime(String id, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getQuizTime(id))
                .subscribe(new SubscriberCallback<>(listener));
    }

}