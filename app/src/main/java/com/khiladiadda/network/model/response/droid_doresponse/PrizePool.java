package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PrizePool implements Serializable {
    @SerializedName("from")
    private int fromRank;
    @SerializedName("to")
    private int toRank;
    @SerializedName("prizeMoney")
    private int prizeMoney;

    public PrizePool(int fromRank, int toRank, int prizeMoney) {
        this.fromRank = fromRank;
        this.toRank = toRank;
        this.prizeMoney = prizeMoney;
    }

    public int getFromRank() {
        return fromRank;
    }

    public void setFromRank(int fromRank) {
        this.fromRank = fromRank;
    }

    public int getToRank() {
        return toRank;
    }

    public void setToRank(int toRank) {
        this.toRank = toRank;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(int prizeMoney) {
        this.prizeMoney = prizeMoney;
    }
}
