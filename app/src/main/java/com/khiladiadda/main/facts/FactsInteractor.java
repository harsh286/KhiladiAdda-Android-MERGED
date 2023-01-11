package com.khiladiadda.main.facts;

import com.khiladiadda.network.model.response.FactDetailsResponse;
import com.khiladiadda.network.model.response.FactsList;
import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.network.model.response.LikeFactResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscription;

public class FactsInteractor {

    public Subscription getTrendingFacts(IApiListener<FactsResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getTrendingFacts())
                .subscribe(new SubscriberCallback<FactsResponse>(listener));
    }

    public Subscription getFacts(IApiListener<FactsResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getFacts())
                .subscribe(new SubscriberCallback<FactsResponse>(listener));
    }

    public Subscription getFactDetails(IApiListener<FactDetailsResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getFactDetails(id))
                .subscribe(new SubscriberCallback<FactDetailsResponse>(listener));
    }

    public Subscription likeFacts(IApiListener<LikeFactResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.likeFact(id))
                .subscribe(new SubscriberCallback<LikeFactResponse>(listener));
    }

    public Subscription bookmarkFacts(IApiListener<LikeFactResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.bookmarkFact(id))
                .subscribe(new SubscriberCallback<LikeFactResponse>(listener));
    }

    public void getFactsFromDB(final FactsPresenter.IOnFactsFetchedListener mOnEventsFetchedListener) {
        Realm realm = Realm.getDefaultInstance();
        final List<FactsList> eventList = new ArrayList<>();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
               RealmResults<FactsList> list = realm.where(FactsList.class).findAll();
               eventList.addAll(realm.copyFromRealm(list));
               mOnEventsFetchedListener.onFactsFetched(eventList);
            }
        });
    }

    public void saveFactsInDB(final List<FactsList> events) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(events);
            }
        });
    }

}