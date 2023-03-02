package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RummyGameRedirectionParams {

    @SerializedName("redirectionUrl")
    @Expose
    private Object redirectionUrl;
    public Object getRedirectionUrl() {
        return redirectionUrl;
    }
    public void setRedirectionUrl(Object redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

}
