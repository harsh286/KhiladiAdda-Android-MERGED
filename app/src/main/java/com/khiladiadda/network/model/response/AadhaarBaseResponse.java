package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AadhaarBaseResponse {

    @SerializedName("status") @Expose private boolean status;
    @SerializedName("message") @Expose private String message;
    @SerializedName("code") @Expose private String code;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

}