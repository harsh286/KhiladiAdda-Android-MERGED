package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoResultRequest {
    @SerializedName("rank") @Expose private int rank;
    @SerializedName("image") @Expose private String image;

    public LudoResultRequest(int rank, String image) {
        this.rank = rank;
        this.image = image;
    }

    public int getRank() {
        return rank;
    }

    public String getImage() {
        return image;
    }
}
