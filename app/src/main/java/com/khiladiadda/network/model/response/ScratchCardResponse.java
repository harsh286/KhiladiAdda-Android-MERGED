package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class ScratchCardResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<ScratchCardResponseDettails> response = null;
    @SerializedName("totalAmountEarned")
    @Expose
    private Double totalAmountEarned;
    @SerializedName("n_game_remaining")
    @Expose
    private int nGameRemaining;

    public List<ScratchCardResponseDettails> getResponse() {
        return response;
    }

    public void setResponse(List<ScratchCardResponseDettails> response) {
        this.response = response;
    }

    public Double getTotalAmountEarned() {
        return totalAmountEarned;
    }

    public void setTotalAmountEarned(Double totalAmountEarned) {
        this.totalAmountEarned = totalAmountEarned;
    }

    public int getnGameRemaining() {
        return nGameRemaining;
    }

    public void setnGameRemaining(int nGameRemaining) {
        this.nGameRemaining = nGameRemaining;
    }
}
