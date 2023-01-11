package com.khiladiadda.network.model;

public class ApiError {
    private double mCode;
    private String mMessage;

    public ApiError(double mCode, String mMessage) {
        this.mCode = mCode;
        this.mMessage = mMessage;
    }

    public double getCode() {
        return mCode;
    }

    public void setCode(double mCode) {
        this.mCode = mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}