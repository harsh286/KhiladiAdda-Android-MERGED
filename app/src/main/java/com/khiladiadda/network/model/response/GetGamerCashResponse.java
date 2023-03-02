package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.gamer_cash.ResponseFetchDetail;

public class GetGamerCashResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private ResponseFetchDetail response = null;

    @SerializedName("is_already_linked")
    @Expose
    private Boolean isAlreadyLinked;
    @SerializedName("is_linked")
    @Expose
    private Boolean isLinked;

    public GetGamerCashResponse(ResponseFetchDetail response, Boolean isAlreadyLinked, Boolean isLinked) {
        this.response = response;
        this.isAlreadyLinked = isAlreadyLinked;
        this.isLinked = isLinked;
    }

    public ResponseFetchDetail getResponse() {
        return response;
    }

    public void setResponse(ResponseFetchDetail response) {
        this.response = response;
    }

    public Boolean getAlreadyLinked() {
        return isAlreadyLinked;
    }

    public void setAlreadyLinked(Boolean alreadyLinked) {
        isAlreadyLinked = alreadyLinked;
    }

    public Boolean getLinked() {
        return isLinked;
    }

    public void setLinked(Boolean linked) {
        isLinked = linked;
    }
}
