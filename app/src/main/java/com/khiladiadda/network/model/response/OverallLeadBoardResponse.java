package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class OverallLeadBoardResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<OverallLeadBoardList> overallLeadBoardLists = null;

    public List<OverallLeadBoardList> getOverallLeadBoardLists() {
        return overallLeadBoardLists;
    }

    public void setOverallLeadBoardLists(List<OverallLeadBoardList> overallLeadBoardLists) {
        this.overallLeadBoardLists = overallLeadBoardLists;
    }
}
