package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoAddaConfig {

    @SerializedName("isEnabled")
    @Expose
    private Boolean isEnabled;
    @SerializedName("classic")
    @Expose
    private Boolean classic;
    @SerializedName("series")
    @Expose
    private Boolean series;
    @SerializedName("timer")
    @Expose
    private Boolean timer;

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getClassic() {
        return classic;
    }

    public void setClassic(Boolean classic) {
        this.classic = classic;
    }

    public Boolean getSeries() {
        return series;
    }

    public void setSeries(Boolean series) {
        this.series = series;
    }

    public Boolean getTimer() {
        return timer;
    }

    public void setTimer(Boolean timer) {
        this.timer = timer;
    }
}
