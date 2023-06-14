package com.khiladiadda.network.model.response;

import com.google.gson.annotations.SerializedName;

public class BajajWallet {
    @SerializedName("isDelink")
    private boolean isDelink;
    @SerializedName("isLinked")
    private boolean isLinked;
    @SerializedName("mobile")
    private String mMobile;

    public boolean isDelink() {
        return isDelink;
    }

    public void setDelink(boolean delink) {
        isDelink = delink;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean linked) {
        isLinked = linked;
    }

    public String getmMobile() {
        return mMobile;
    }

    public void setmMobile(String mMobile) {
        this.mMobile = mMobile;
    }
}
