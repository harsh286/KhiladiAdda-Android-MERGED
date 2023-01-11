package com.khiladiadda.chat.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IChatPresenter extends IBasePresenter {

    void updateChatId(String id);

    void getOpponentTokens(String contestId);

}