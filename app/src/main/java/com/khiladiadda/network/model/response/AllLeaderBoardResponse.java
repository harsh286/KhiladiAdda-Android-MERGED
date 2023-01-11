package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class AllLeaderBoardResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<AllLederBoardDetails> response = null;

    public List<AllLederBoardDetails> getResponse() {
        return response;
    }

    public void setResponse(List<AllLederBoardDetails> response) {
        this.response = response;
    }

}