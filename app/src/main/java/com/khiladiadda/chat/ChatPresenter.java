package com.khiladiadda.chat;

import com.khiladiadda.chat.interfaces.IChatPresenter;
import com.khiladiadda.chat.interfaces.IChatView;
import com.khiladiadda.network.model.response.NotifyResponse;
import com.khiladiadda.network.model.request.UpdateChatIdRequest;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class ChatPresenter implements IChatPresenter {

    private IChatView mView;
    private ChatInteractor mInteractor;
    private Subscription mSubscription;

    public ChatPresenter(IChatView mView) {
        this.mView = mView;
        mInteractor = new ChatInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void updateChatId(String id) {
        UpdateChatIdRequest request = new UpdateChatIdRequest(id);
        mSubscription = mInteractor.updateChatId(mUpdateApiListener, request);
    }

    private IApiListener<ProfileResponse> mUpdateApiListener = new IApiListener<ProfileResponse>() {
        @Override
        public void onSuccess(ProfileResponse response) {
            mView.onUpdateChatIdSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdateChatIdFailure(error);
        }
    };

    @Override
    public void getOpponentTokens(String contestId) {
        mSubscription = mInteractor.getOpponentTokens(mOpponentAPIListener, contestId);
    }

    private IApiListener<NotifyResponse> mOpponentAPIListener = new IApiListener<NotifyResponse>() {
        @Override
        public void onSuccess(NotifyResponse response) {
            mView.onGetOpponentTokenSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetOpponentTokenFailure(error);
        }
    };

}