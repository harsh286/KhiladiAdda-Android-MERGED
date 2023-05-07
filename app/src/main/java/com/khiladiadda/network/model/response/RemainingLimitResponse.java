package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class RemainingLimitResponse extends BaseResponse {

    @SerializedName("remaining_add_limit") @Expose private long remaining_add_limit;

    public long getRemaining_add_limit() {
        return remaining_add_limit;
    }

    public void setRemaining_add_limit(long remaining_add_limit) {
        this.remaining_add_limit = remaining_add_limit;
    }
}