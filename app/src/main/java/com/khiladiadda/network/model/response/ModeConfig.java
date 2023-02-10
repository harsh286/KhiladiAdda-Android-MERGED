package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModeConfig {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("ludoAddaConfig")
    @Expose
    private LudoAddaConfig ludoAddaConfig;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LudoAddaConfig getLudoAddaConfig() {
        return ludoAddaConfig;
    }

    public void setLudoAddaConfig(LudoAddaConfig ludoAddaConfig) {
        this.ludoAddaConfig = ludoAddaConfig;
    }

}
