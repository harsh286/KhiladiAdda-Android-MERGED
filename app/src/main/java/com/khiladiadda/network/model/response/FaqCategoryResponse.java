package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class FaqCategoryResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<FaqCategoryDetails> response = null;

    @SerializedName("support_via") @Expose private int supportVia;

    public List<FaqCategoryDetails> getResponse() {
        return response;
    }

    public void setResponse(List<FaqCategoryDetails> response) {
        this.response = response;
    }

    public int getSupportVia() {
        return supportVia;
    }

    public void setSupportVia(int supportVia) {
        this.supportVia = supportVia;
    }

}