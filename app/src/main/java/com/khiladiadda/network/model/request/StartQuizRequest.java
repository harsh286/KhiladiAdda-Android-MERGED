package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartQuizRequest {

    @SerializedName("quiz_id") @Expose private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public StartQuizRequest(String categoryId) {
        this.categoryId = categoryId;
    }

}