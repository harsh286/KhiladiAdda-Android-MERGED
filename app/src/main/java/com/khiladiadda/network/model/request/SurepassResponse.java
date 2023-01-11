package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.response.SurepassClientDetails;

public class SurepassResponse {

    @SerializedName("success") @Expose private boolean status;
    @SerializedName("message_code") @Expose private String message;
    @SerializedName("status_code") @Expose private int systemInfo;
    @SerializedName("data")
    @Expose
    private SurepassClientDetails data;

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

    public int getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(int systemInfo) {
        this.systemInfo = systemInfo;
    }

    public SurepassClientDetails getData() {
        return data;
    }

    public void setData(SurepassClientDetails data) {
        this.data = data;
    }
}
