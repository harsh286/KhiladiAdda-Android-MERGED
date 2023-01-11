package com.khiladiadda.chat.interfaces;

import com.khiladiadda.network.model.response.NotifyResponse;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.ApiError;

public interface IChatView {

    void onUpdateChatIdSuccess(ProfileResponse responseModel);

    void onUpdateChatIdFailure(ApiError error);

    void onGetOpponentTokenSuccess(NotifyResponse response);

    void onGetOpponentTokenFailure(ApiError error);

}