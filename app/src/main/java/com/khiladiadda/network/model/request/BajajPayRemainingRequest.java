package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BajajPayRemainingRequest {
    @SerializedName("bajajUserTocken")
    @Expose
    private String bajajUserTocken;
    @SerializedName("bajajAccessTocken")
    @Expose
    private String bajajAccessTocken;
    @SerializedName("isDelink")
    @Expose
    private Boolean isDelink;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public BajajPayRemainingRequest(String bajajUserTocken, String bajajAccessTocken, Boolean isDelink, String mobile) {
        this.bajajUserTocken = bajajUserTocken;
        this.bajajAccessTocken = bajajAccessTocken;
        this.isDelink = isDelink;
        this.mobile = mobile;
    }

    public String getBajajUserTocken() {
        return bajajUserTocken;
    }

    public void setBajajUserTocken(String bajajUserTocken) {
        this.bajajUserTocken = bajajUserTocken;
    }

    public String getBajajAccessTocken() {
        return bajajAccessTocken;
    }

    public void setBajajAccessTocken(String bajajAccessTocken) {
        this.bajajAccessTocken = bajajAccessTocken;
    }

    public Boolean getDelink() {
        return isDelink;
    }

    public void setDelink(Boolean delink) {
        isDelink = delink;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
