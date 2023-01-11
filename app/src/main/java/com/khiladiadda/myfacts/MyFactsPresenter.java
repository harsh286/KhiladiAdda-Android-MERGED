package com.khiladiadda.myfacts;

import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.myfacts.interfaces.IMyFactsPresenter;
import com.khiladiadda.myfacts.interfaces.IMyFactsView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import rx.Subscription;

public class MyFactsPresenter implements IMyFactsPresenter {

    private IMyFactsView mView;
    private MyFactsInteractor mInteractor;
    private Subscription mLikedSubscription, mBookmarkedSubscription;

    public MyFactsPresenter(IMyFactsView view) {
        mView = view;
        mInteractor = new MyFactsInteractor();
    }

    @Override
    public void getLikedFacts() {
            mLikedSubscription = mInteractor.getLikedFacts(mLikedAPIListener);
    }

    @Override
    public void getBookmarkedFacts() {
        mBookmarkedSubscription = mInteractor.getBookmarkedFacts(mBookmarkedAPIListener);
    }

    private IApiListener<FactsResponse> mLikedAPIListener = new IApiListener<FactsResponse>() {
        @Override
        public void onSuccess(FactsResponse response) {
            mView.onLikedComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLikedFailure(error);
        }
    };

    private IApiListener<FactsResponse> mBookmarkedAPIListener = new IApiListener<FactsResponse>() {
        @Override
        public void onSuccess(FactsResponse response) {
            mView.onBookmarkedComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onBookmarkedFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mLikedSubscription != null && !mLikedSubscription.isUnsubscribed()) {
            mLikedSubscription.unsubscribe();
        }
        if (mBookmarkedSubscription != null && !mBookmarkedSubscription.isUnsubscribed()) {
            mBookmarkedSubscription.unsubscribe();
        }
    }
}