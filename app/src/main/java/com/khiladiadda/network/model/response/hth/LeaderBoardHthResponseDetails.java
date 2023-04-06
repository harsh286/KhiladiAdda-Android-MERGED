package com.khiladiadda.network.model.response.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderBoardHthResponseDetails {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("leaderboard_type")
    @Expose
    private Integer leaderboardType;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("n_cof")
    @Expose
    private NCof nCof;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLeaderboardType() {
        return leaderboardType;
    }

    public void setLeaderboardType(Integer leaderboardType) {
        this.leaderboardType = leaderboardType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public NCof getnCof() {
        return nCof;
    }

    public void setnCof(NCof nCof) {
        this.nCof = nCof;
    }

}