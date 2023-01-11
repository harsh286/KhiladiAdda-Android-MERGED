package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateChatIdRequest {

    @SerializedName("firebase_chat_id") @Expose private String firebaseChatId;

    public String getFirebaseChatId() {
        return firebaseChatId;
    }

    public void setFirebaseChatId(String firebaseChatId) {
        this.firebaseChatId = firebaseChatId;
    }

    public UpdateChatIdRequest(String firebaseChatId) {
        this.firebaseChatId = firebaseChatId;
    }

}