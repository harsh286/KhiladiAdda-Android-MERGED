package com.khiladiadda.network.model.response;
import com.google.gson.annotations.SerializedName;
public class BajajWallet {
    @SerializedName("isDelink")
    private boolean isDelink;
    @SerializedName("isLinked")
    private boolean isLinked;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("accessToken")
    private String bajajAccessTocken;
    @SerializedName("userToken")
    private String bajajUserTocken;

    public String getBajajAccessTocken() {
        return bajajAccessTocken;
    }

    public void setBajajAccessTocken(String bajajAccessTocken) {
        this.bajajAccessTocken = bajajAccessTocken;
    }

    public String getBajajUserTocken() {
        return bajajUserTocken;
    }

    public void setBajajUserTocken(String bajajUserTocken) {
        this.bajajUserTocken = bajajUserTocken;
    }

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
