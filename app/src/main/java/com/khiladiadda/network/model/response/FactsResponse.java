package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FactsResponse {

    @SerializedName("status") @Expose private Boolean status;
    @SerializedName("message") @Expose private String message;
    @SerializedName("response") @Expose private List<FactsList> response;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FactsList> getResponse() {
        return response;
    }

    public void setResponse(List<FactsList> response) {
        this.response = response;
    }
}
