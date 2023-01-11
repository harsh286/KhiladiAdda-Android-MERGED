package com.khiladiadda.base;

public class ApiResponseEvent {

    private int mSystemInfo;
    private String mMessage;

    public ApiResponseEvent(String message, int systemInfo) {
        this.mMessage = message;
        this.mSystemInfo = systemInfo;
    }

    public int getSystemInfo() {
        return mSystemInfo;
    }

    public void setSystemInfo(int mSystemInfo) {
        this.mSystemInfo = mSystemInfo;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

}