package com.khiladiadda.main.facts;

import com.khiladiadda.main.facts.interfaces.IFactsPresenter;
import com.khiladiadda.main.facts.interfaces.IFactsView;
import com.khiladiadda.network.model.response.FactDetailsResponse;
import com.khiladiadda.network.model.response.FactsList;
import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.network.model.response.LikeFactResponse;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import java.util.List;

import rx.Subscription;

public class FactsPresenter implements IFactsPresenter {

    private IFactsView mView;
    private FactsInteractor mInteractor;
    private Subscription mGetTrendingSubscription, mGetSubscription, mGetDetailSubscription, mLikeSubscription, mBookmarkSubscription;

    public FactsPresenter(IFactsView mView) {
        this.mView = mView;
        mInteractor = new FactsInteractor();
    }

    @Override
    public void getTrendingFacts() {
        mGetTrendingSubscription = mInteractor.getTrendingFacts(mGetTrendingApiListener);
    }

    @Override
    public void getFacts() {
        mGetSubscription = mInteractor.getFacts(mGetApiListener);
    }

    @Override
    public void getFactDetails(String id) {
        mGetDetailSubscription = mInteractor.getFactDetails(mGetDetailsApiListener, id);
    }

    @Override
    public void likeFact(String id) {
        mLikeSubscription = mInteractor.likeFacts(mLikeApiListener, id);
    }

    @Override
    public void bookmarkFact(String id) {
        mBookmarkSubscription = mInteractor.bookmarkFacts(mBookmarkApiListener, id);
    }

    @Override
    public void getFactsFromDB() {
        mInteractor.getFactsFromDB(mOnEventsFetchedListener);
    }

    private IApiListener<FactsResponse> mGetTrendingApiListener = new IApiListener<FactsResponse>() {
        @Override
        public void onSuccess(FactsResponse response) {
            mInteractor.saveFactsInDB(response.getResponse());
            mView.onTrendingFactsComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onTrendingFactsFailure(error);
        }
    };

    private IApiListener<FactsResponse> mGetApiListener = new IApiListener<FactsResponse>() {
        @Override
        public void onSuccess(FactsResponse response) {
            mView.onFactsComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFactsFailure(error);
        }
    };

    private IApiListener<FactDetailsResponse> mGetDetailsApiListener = new IApiListener<FactDetailsResponse>() {
        @Override
        public void onSuccess(FactDetailsResponse response) {
            mView.onFactDetailsComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFactDetailsFailure(error);
        }
    };

    private IApiListener<LikeFactResponse> mLikeApiListener = new IApiListener<LikeFactResponse>() {
        @Override
        public void onSuccess(LikeFactResponse response) {
            mView.onLikeFactComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLikeFactFailure(error);
        }
    };

    private IApiListener<LikeFactResponse> mBookmarkApiListener = new IApiListener<LikeFactResponse>() {
        @Override
        public void onSuccess(LikeFactResponse response) {
            mView.onBookmarkFactComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onBookmarkFactFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mGetTrendingSubscription != null && !mGetTrendingSubscription.isUnsubscribed()) {
            mGetTrendingSubscription.unsubscribe();
        }
        if (mGetSubscription != null && !mGetSubscription.isUnsubscribed()) {
            mGetSubscription.unsubscribe();
        }
        if (mGetDetailSubscription != null && !mGetDetailSubscription.isUnsubscribed()) {
            mGetDetailSubscription.unsubscribe();
        }
        if (mLikeSubscription != null && !mLikeSubscription.isUnsubscribed()) {
            mLikeSubscription.unsubscribe();
        }
        if (mBookmarkSubscription != null && !mBookmarkSubscription.isUnsubscribed()) {
            mBookmarkSubscription.unsubscribe();
        }
    }

    private IOnFactsFetchedListener mOnEventsFetchedListener = new IOnFactsFetchedListener() {
        @Override
        public void onFactsFetched(List<FactsList> eventList) {
            mView.onFactsFetchedFromDB(eventList);
        }
    };

    public interface IOnFactsFetchedListener {
        void onFactsFetched(List<FactsList> eventList);
    }

}