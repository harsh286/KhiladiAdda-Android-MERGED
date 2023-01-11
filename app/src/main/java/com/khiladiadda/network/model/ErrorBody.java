package com.khiladiadda.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorBody {

    @SerializedName("Code")
    @Expose
    private double mCode;
    @SerializedName("DATA")
    @Expose
    private Object mData;
    @SerializedName("message")
    @Expose
    private String mMessage;
    @SerializedName("status")
    @Expose
    private boolean mSuccess;

    public double getCode() {
        return mCode;
    }

    public void setCode(double code) {
        this.mCode = code;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
