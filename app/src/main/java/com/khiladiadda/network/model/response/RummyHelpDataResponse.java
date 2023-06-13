package com.khiladiadda.network.model.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class RummyHelpDataResponse {
    @SerializedName("_id")
    @Expose
    private String mId;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("video")
    @Expose
    private String mVideo;
    @SerializedName("body")
    @Expose
    private List<HelpRulesPointData> HelpRulesPointData;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmVideo() {
        return mVideo;
    }

    public void setmVideo(String mVideo) {
        this.mVideo = mVideo;
    }

    public List<HelpRulesPointData> getmHelpRulesPointData() {
        return HelpRulesPointData;
    }

    public void setmHelpRulesPointData(List<HelpRulesPointData> HelpRulesPointData) {
        this.HelpRulesPointData = HelpRulesPointData;
    }
}
