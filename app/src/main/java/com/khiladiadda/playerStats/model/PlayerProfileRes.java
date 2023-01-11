package com.khiladiadda.playerStats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerProfileRes {
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;
    @SerializedName("battingStyle")
    @Expose
    private String battingStyle;
    @SerializedName("bowlingStyle")
    @Expose
    private String bowlingStyle;
    @SerializedName("majorTeams")
    @Expose
    private String majorTeams;
    @SerializedName("currentAge")
    @Expose
    private String currentAge;
    @SerializedName("born")
    @Expose
    private String born;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("playingRole")
    @Expose
    private String playingRole;
    @SerializedName("v")
    @Expose
    private String v;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("ttl")
    @Expose
    private Integer ttl;
    @SerializedName("provider")
    @Expose
    private Provider provider;
    @SerializedName("creditsLeft")
    @Expose
    private Integer creditsLeft;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public void setBattingStyle(String battingStyle) {
        this.battingStyle = battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public void setBowlingStyle(String bowlingStyle) {
        this.bowlingStyle = bowlingStyle;
    }

    public String getMajorTeams() {
        return majorTeams;
    }

    public void setMajorTeams(String majorTeams) {
        this.majorTeams = majorTeams;
    }

    public String getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(String currentAge) {
        this.currentAge = currentAge;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlayingRole() {
        return playingRole;
    }

    public void setPlayingRole(String playingRole) {
        this.playingRole = playingRole;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Integer getCreditsLeft() {
        return creditsLeft;
    }

    public void setCreditsLeft(Integer creditsLeft) {
        this.creditsLeft = creditsLeft;
    }

}
