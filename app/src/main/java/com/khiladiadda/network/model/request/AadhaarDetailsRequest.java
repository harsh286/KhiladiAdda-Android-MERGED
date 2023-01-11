package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AadhaarDetailsRequest {

//    "api_key": "<your_api_key>",
//            "uuid": "<UUID>",
//            "hash": "Refer Appendix B",

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("hash")
    @Expose
    private String hash;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public AadhaarDetailsRequest(String uuid, String apiKey, String hash) {
        this.uuid = uuid;
        this.apiKey = apiKey;
        this.hash = hash;
    }
}
