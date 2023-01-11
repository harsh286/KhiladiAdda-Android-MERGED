package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoErrorRequest {
    @SerializedName("reason") @Expose private String reason;
    @SerializedName("image") @Expose private String image;
//    @SerializedName("g_link")@Expose private String glink;

    public LudoErrorRequest(String reason, String image) {
        this.reason = reason;
        this.image = image;
//        this.glink=glink;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public String getGlink() {
//        return glink;
//    }
//
//    public void setGlink(String glink) {
//        this.glink = glink;
//    }
}
