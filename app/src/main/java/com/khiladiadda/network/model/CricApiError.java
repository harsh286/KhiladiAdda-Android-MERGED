package com.khiladiadda.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CricApiError {
    @SerializedName("error")
    @Expose String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
