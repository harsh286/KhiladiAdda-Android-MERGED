package com.khiladiadda.network.model.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class BajajWallet {
    @SerializedName("isLinked")
    @Expose
    private Boolean isLinked;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("isDelink")
    @Expose
    private Boolean isDelink;
    @SerializedName("accessToken")
    private String bajajAccessTocken;
    @SerializedName("userToken")
    private String bajajUserTocken;

    public Boolean getLinked() {
        return isLinked;
    }

    public void setLinked(Boolean linked) {
        isLinked = linked;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getDelink() {
        return isDelink;
    }

    public void setDelink(Boolean delink) {
        isDelink = delink;
    }

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
}