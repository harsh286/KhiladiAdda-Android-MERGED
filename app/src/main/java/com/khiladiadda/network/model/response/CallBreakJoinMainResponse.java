package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class CallBreakJoinMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private CallBreakJoinResponse response;
    @SerializedName("isAlreadyJoined")
    @Expose
    private Boolean isAlreadyJoined;

    public CallBreakJoinResponse getResponse() {
        return response;
    }

    public void setResponse(CallBreakJoinResponse response) {
        this.response = response;
    }

    public Boolean getAlreadyJoined() {
        return isAlreadyJoined;
    }

    public void setAlreadyJoined(Boolean alreadyJoined) {
        isAlreadyJoined = alreadyJoined;
    }
}
