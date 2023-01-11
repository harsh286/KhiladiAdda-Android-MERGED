package com.khiladiadda.clashroyale;

import com.khiladiadda.clashroyale.interfaces.IClashRoyalePresenter;
import com.khiladiadda.clashroyale.interfaces.IClashRoyaleView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ClashRoyaleFilterReponse;
import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;

import rx.Subscription;

public class ClashRoyalePresenter implements IClashRoyalePresenter {

    private IClashRoyaleView mView;
    private ClashRoyaleInteractor mInteractor;
    private Subscription mSubscription;

    public ClashRoyalePresenter(IClashRoyaleView view) {
        mView = view;
        mInteractor = new ClashRoyaleInteractor();
    }

    @Override public void getFilterList() {
        mSubscription = mInteractor.getFilter(AppSharedPreference.getInstance().getString(AppConstant.CLASHROYALE_ID, ""), mFilterApiListener);
    }

    @Override public void getLeagueList(String gameId, int type) {
        mSubscription = mInteractor.getLeague(gameId, type, mLeagueApiListener);
    }

    private IApiListener<ClashRoyaleFilterReponse> mFilterApiListener = new IApiListener<ClashRoyaleFilterReponse>() {
        @Override public void onSuccess(ClashRoyaleFilterReponse response) {
            mView.onFilterComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onFilterFailure(error);
        }
    };

    private IApiListener<LeagueListReponse> mLeagueApiListener = new IApiListener<LeagueListReponse>() {
        @Override public void onSuccess(LeagueListReponse response) {
            mView.onLeagueComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onLeagueFailure(error);
        }
    };

    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}