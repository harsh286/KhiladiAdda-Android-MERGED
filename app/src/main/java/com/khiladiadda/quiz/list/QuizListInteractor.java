package com.khiladiadda.quiz.list;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.StartQuizRequest;
import com.khiladiadda.network.model.response.QuizListResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;
import com.khiladiadda.network.model.response.UserQuizPlayedResponse;

import rx.Subscription;

public class QuizListInteractor {

    public Subscription getQuizList(String id, IApiListener<QuizListResponse> listener, boolean upcoming, boolean past, boolean live) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getQuizList(id, upcoming, past, live)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription startQuiz(StartQuizRequest request, IApiListener<StartQuizResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.startQuiz(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getUserPlayedQuiz(IApiListener<UserQuizPlayedResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getUserPlayedQuiz(id, true)).subscribe(new SubscriberCallback<>(listener));
    }

}