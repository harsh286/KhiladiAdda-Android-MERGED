package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaySharpData {

    @SerializedName("data")
    @Expose
    private PaySharpDetail response;

    public PaySharpDetail getResponse() {
        return response;
    }

    public void setResponse(PaySharpDetail response) {
        this.response = response;
    }

}
