package com.khiladiadda.network.model.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class LinkBajajWalletRequest{
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("isDelink")
    @Expose
    private boolean isDelink;
    @SerializedName("bajajAccessTocken")
    @Expose
    private String bajajAccessTocken;
    @SerializedName("bajajUserTocken")
    @Expose
    private String bajajUserTocken;

    public LinkBajajWalletRequest(String mobile, boolean isDelink, String bajajAccessTocken, String bajajUserTocken) {
        this.mobile = mobile;
        this.isDelink = isDelink;
        this.bajajAccessTocken = bajajAccessTocken;
        this.bajajUserTocken = bajajUserTocken;
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

    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public boolean isDelink() {
        return isDelink;
    }
    public void setDelink(boolean delink){
        isDelink = delink;
    }
}
