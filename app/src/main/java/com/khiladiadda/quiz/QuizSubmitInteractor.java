package com.khiladiadda.quiz;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.QuizSubmitRequest;
import com.khiladiadda.network.model.response.QuizSubmitResponse;

import rx.Subscription;

public class QuizSubmitInteractor {

    public Subscription doQuizSubmit(QuizSubmitRequest request, IApiListener<QuizSubmitResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.submitQuiz(request))
                .subscribe(new SubscriberCallback<>(listener));
    }
}