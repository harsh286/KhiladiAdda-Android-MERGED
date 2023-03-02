package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceContextResponse {
    @SerializedName("deviceOS")
    @Expose
    private String deviceOS;
}
