package com.khiladiadda.main;

import com.khiladiadda.main.category.CategoryInteractor;
import com.khiladiadda.main.category.interfaces.ICategoryPresenter;
import com.khiladiadda.main.category.interfaces.ICategoryView;
import com.khiladiadda.main.interfaces.IMainPresenter;
import com.khiladiadda.main.interfaces.IMainView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.UpdateFavouriteRequest;
import com.khiladiadda.network.model.response.DashboardResponse;
import com.khiladiadda.network.model.response.TrendinQuizResponse;

import rx.Subscription;

public class MainPresenter implements IMainPresenter {

    private IMainView mView;
    private MainInteractor mInteractor;
    private Subscription mTrendingSubscription;

    public MainPresenter(IMainView mView) {
        this.mView = mView;
        mInteractor = new MainInteractor();
    }

    @Override
    public void getData() {
        mTrendingSubscription = mInteractor.getTrending(mTrendingApiListener);
    }

    private IApiListener<DashboardResponse> mTrendingApiListener = new IApiListener<DashboardResponse>() {
        @Override
        public void onSuccess(DashboardResponse response) {
            mView.onTrendingQuizComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onTrendingQuizFailure(error);
        }
    };


    @Override
    public void updateFavourite(UpdateFavouriteRequest request) {
        mTrendingSubscription = mInteractor.updateFavourite(mFavouriteApiListener, request);
    }

    private IApiListener<BaseResponse> mFavouriteApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onUpdateFavouriteComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdateFavouriteFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mTrendingSubscription != null && !mTrendingSubscription.isUnsubscribed()) {
            mTrendingSubscription.unsubscribe();
        }
    }

}