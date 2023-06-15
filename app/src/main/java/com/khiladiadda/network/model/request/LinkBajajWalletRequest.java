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
    public LinkBajajWalletRequest(String mobile,boolean isDelink){
        this.mobile =mobile;
        this.isDelink =isDelink;
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
