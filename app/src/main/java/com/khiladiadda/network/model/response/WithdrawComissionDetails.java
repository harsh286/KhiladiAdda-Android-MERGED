package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawComissionDetails {

    @SerializedName("from") @Expose
    private long from;
    @SerializedName("to") @Expose private long to;
    @SerializedName("commission") @Expose private long commission;

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public long getCommission() {
        return commission;
    }

    public void setCommission(long commission) {
        this.commission = commission;
    }

}