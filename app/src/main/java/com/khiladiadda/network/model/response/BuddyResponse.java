package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class BuddyResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<BuddyDetails> response = null;

    public List<BuddyDetails> getResponse() {
        return response;
    }

    public void setResponse(List<BuddyDetails> response) {
        this.response = response;
    }

}