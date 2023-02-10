package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardSubResponse {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("totalAmount")
    @Expose
    private Double totalAmount;
    @SerializedName("fullDetails")
    @Expose
    private LeaderboardFullDetailsResponse fullDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LeaderboardFullDetailsResponse getFullDetails() {
        return fullDetails;
    }

    public void setFullDetails(LeaderboardFullDetailsResponse fullDetails) {
        this.fullDetails = fullDetails;
    }
}
