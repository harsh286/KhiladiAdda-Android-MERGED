package com.khiladiadda.quiz.result;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.LeaderBoardResponse;
import com.khiladiadda.network.model.request.StartQuizRequest;
import com.khiladiadda.network.model.response.StartQuizResponse;

import rx.Subscription;

public class QuizResultInteractor {

    public Subscription startQuiz(StartQuizRequest request, IApiListener<StartQuizResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.startQuiz(request))
                .subscribe(new SubscriberCallback<StartQuizResponse>(listener));
    }

    public Subscription getLeaderBoard(String id, IApiListener<LeaderBoardResponse> listener, int page, int limit) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getLeaderBoard(id))
                .subscribe(new SubscriberCallback<LeaderBoardResponse>(listener));
    }
}
