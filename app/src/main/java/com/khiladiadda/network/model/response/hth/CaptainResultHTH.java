package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.response.CxCkPoint;
import com.khiladiadda.network.model.response.CxCkScore;
import com.khiladiadda.network.model.response.CxFbScore;
import com.khiladiadda.network.model.response.CxKbPoints;
import com.khiladiadda.network.model.response.CxKbScore;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CaptainResultHTH implements Parcelable {
    @SerializedName("kb_points")
    @Expose
    private CxKbPoints kbPoints;
    @SerializedName("kb_score")
    @Expose
    private CxKbScore kbScore;
    @SerializedName("fb_score")
    @Expose
    private CxFbScore fbScore;
    @SerializedName("fb_points")
    @Expose
    private CxFbScore fbPoints;
    @SerializedName("score")
    @Expose
    private CxCkScore score;
    @SerializedName("points")
    @Expose
    private CxCkPoint point;
    @SerializedName("playing")
    @Expose
    private Integer playing;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("title")
    @Expose
    private String title;

    public CxKbPoints getKbPoints() {
        return kbPoints;
    }

    public void setKbPoints(CxKbPoints kbPoints) {
        this.kbPoints = kbPoints;
    }

    public CxKbScore getKbScore() {
        return kbScore;
    }

    public void setKbScore(CxKbScore kbScore) {
        this.kbScore = kbScore;
    }

    public CxFbScore getFbScore() {
        return fbScore;
    }

    public void setFbScore(CxFbScore fbScore) {
        this.fbScore = fbScore;
    }

    public CxFbScore getFbPoints() {
        return fbPoints;
    }

    public void setFbPoints(CxFbScore fbPoints) {
        this.fbPoints = fbPoints;
    }

    public CxCkScore getScore() {
        return score;
    }

    public void setScore(CxCkScore score) {
        this.score = score;
    }

    public CxCkPoint getPoint() {
        return point;
    }

    public void setPoint(CxCkPoint point) {
        this.point = point;
    }

    public Integer getPlaying() {
        return playing;
    }

    public void setPlaying(Integer playing) {
        this.playing = playing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.kbPoints, flags);
        dest.writeParcelable(this.kbScore, flags);
        dest.writeParcelable(this.fbScore, flags);
        dest.writeParcelable(this.fbPoints, flags);
        dest.writeParcelable(this.score, flags);
        dest.writeParcelable(this.point, flags);
        dest.writeValue(this.playing);
        dest.writeString(this.id);
        dest.writeString(this.img);
        dest.writeValue(this.role);
        dest.writeString(this.teamName);
        dest.writeString(this.title);
    }

    public void readFromParcel(Parcel source) {
        this.kbPoints = source.readParcelable(CxKbPoints.class.getClassLoader());
        this.kbScore = source.readParcelable(CxKbScore.class.getClassLoader());
        this.fbScore = source.readParcelable(CxFbScore.class.getClassLoader());
        this.fbPoints = source.readParcelable(CxFbScore.class.getClassLoader());
        this.score = source.readParcelable(CxCkScore.class.getClassLoader());
        this.point = source.readParcelable(CxCkPoint.class.getClassLoader());
        this.playing = (Integer) source.readValue(Integer.class.getClassLoader());
        this.id = source.readString();
        this.img = source.readString();
        this.role = (Integer) source.readValue(Integer.class.getClassLoader());
        this.teamName = source.readString();
        this.title = source.readString();
    }

    public CaptainResultHTH() {
    }

    protected CaptainResultHTH(Parcel in) {
        this.kbPoints = in.readParcelable(CxKbPoints.class.getClassLoader());
        this.kbScore = in.readParcelable(CxKbScore.class.getClassLoader());
        this.fbScore = in.readParcelable(CxFbScore.class.getClassLoader());
        this.fbPoints = in.readParcelable(CxFbScore.class.getClassLoader());
        this.score = in.readParcelable(CxCkScore.class.getClassLoader());
        this.point = in.readParcelable(CxCkPoint.class.getClassLoader());
        this.playing = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = in.readString();
        this.img = in.readString();
        this.role = (Integer) in.readValue(Integer.class.getClassLoader());
        this.teamName = in.readString();
        this.title = in.readString();
    }

    public static final Creator<CaptainResultHTH> CREATOR = new Creator<CaptainResultHTH>() {
        @Override
        public CaptainResultHTH createFromParcel(Parcel source) {
            return new CaptainResultHTH(source);
        }

        @Override
        public CaptainResultHTH[] newArray(int size) {
            return new CaptainResultHTH[size];
        }
    };
}
