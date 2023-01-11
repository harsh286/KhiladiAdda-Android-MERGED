package com.khiladiadda.help;

import com.khiladiadda.network.model.response.FaqCategoryResponse;
import com.khiladiadda.network.model.response.HelpResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class HelpInteractor {

    public Subscription getCategory(IApiListener<FaqCategoryResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getHelpCategory())
                .subscribe(new SubscriberCallback<FaqCategoryResponse>(listener));
    }

    public Subscription getHelp(IApiListener<HelpResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getHelp(id, ""))
                .subscribe(new SubscriberCallback<HelpResponse>(listener));
    }

}