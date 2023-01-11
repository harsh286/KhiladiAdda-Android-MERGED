package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverallLeadBoard {
    @SerializedName("type")
    @Expose
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
