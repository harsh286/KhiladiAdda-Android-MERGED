package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PanInfo {

    @SerializedName("name") @Expose private String name;
    @SerializedName("url") @Expose private String url;
    @SerializedName("number") @Expose private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PanInfo(String name, String url, String number) {
        this.name = name;
        this.url = url;
        this.number = number;
    }
}