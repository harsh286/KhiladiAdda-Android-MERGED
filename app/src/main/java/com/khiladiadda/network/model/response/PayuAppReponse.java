package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayuAppReponse {
    @SerializedName("status") @Expose private int status;
    @SerializedName("message") @Expose private String message;
    @SerializedName("result") @Expose private PayuAppDetails result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PayuAppDetails getResult() {
        return result;
    }

    public void setResult(PayuAppDetails result) {
        this.result = result;
    }

}
