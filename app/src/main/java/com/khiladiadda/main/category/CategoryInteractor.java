package com.khiladiadda.main.category;

import com.khiladiadda.network.model.response.TrendinQuizResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class CategoryInteractor {

    public Subscription getTrending(IApiListener<TrendinQuizResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getTrendingQuiz())
                .subscribe(new SubscriberCallback<>(listener));
    }
}
