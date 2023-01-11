package com.khiladiadda.main.category;

import com.khiladiadda.main.category.interfaces.ICategoryPresenter;
import com.khiladiadda.main.category.interfaces.ICategoryView;
import com.khiladiadda.network.model.response.TrendinQuizResponse;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class CategoryPresenter implements ICategoryPresenter {

    private ICategoryView mView;
    private CategoryInteractor mInteractor;
    private Subscription mTrendingSubscription;

    public CategoryPresenter(ICategoryView mView) {
        this.mView = mView;
        mInteractor = new CategoryInteractor();
    }

    @Override
    public void getTrendingQuiz() {
        mTrendingSubscription = mInteractor.getTrending(mTrendingApiListener);
    }

    private IApiListener<TrendinQuizResponse> mTrendingApiListener = new IApiListener<TrendinQuizResponse>() {
        @Override
        public void onSuccess(TrendinQuizResponse response) {
            mView.onTrendingQuizComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onTrendingQuizFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mTrendingSubscription != null && !mTrendingSubscription.isUnsubscribed()) {
            mTrendingSubscription.unsubscribe();
        }
    }

}