package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.gamer_cash.ResponseGamerCash;

public class GamerCashResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private ResponseGamerCash response;
    @SerializedName("is_already_linked")
    @Expose
    private Boolean isAlreadyLinked;
    @SerializedName("is_linked")
    @Expose
    private Boolean isLinked;

    public GamerCashResponse(ResponseGamerCash response, Boolean isAlreadyLinked, Boolean isLinked) {
        this.response = response;
        this.isAlreadyLinked = isAlreadyLinked;
        this.isLinked = isLinked;
    }

    public ResponseGamerCash getResponse() {
        return response;
    }

    public void setResponse(ResponseGamerCash response) {
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
