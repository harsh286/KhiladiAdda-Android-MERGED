package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class KaPlayerHTH implements Parcelable {
    @SerializedName("points")
    @Expose
    private PointsHTH points;
    @SerializedName("score")
    @Expose
    private ScoreHTH score;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("p_id")
    @Expose
    private String pId;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("history")
    @Expose
    private List<Object> history = null;
    @SerializedName("playing")
    @Expose int playing;
    private boolean isSelected;

    public PointsHTH getPoints() {
        return points;
    }

    public void setPoints(PointsHTH points) {
        this.points = points;
    }

    public ScoreHTH getScore() {
        return score;
    }

    public void setScore(ScoreHTH score) {
        this.score = score;
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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Object> getHistory() {
        return history;
    }

    public void setHistory(List<Object> history) {
        this.history = history;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPlaying() {
        return playing;
    }

    public void setPlaying(int playing) {
        this.playing = playing;
    }


    public KaPlayerHTH() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.points, flags);
        dest.writeParcelable(this.score, flags);
        dest.writeString(this.id);
        dest.writeString(this.img);
        dest.writeString(this.pId);
        dest.writeValue(this.role);
        dest.writeString(this.title);
        dest.writeString(this.teamName);
        dest.writeList(this.history);
        dest.writeInt(this.playing);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.points = source.readParcelable(PointsHTH.class.getClassLoader());
        this.score = source.readParcelable(ScoreHTH.class.getClassLoader());
        this.id = source.readString();
        this.img = source.readString();
        this.pId = source.readString();
        this.role = (Integer) source.readValue(Integer.class.getClassLoader());
        this.title = source.readString();
        this.teamName = source.readString();
        this.history = new ArrayList<Object>();
        source.readList(this.history, Object.class.getClassLoader());
        this.playing = source.readInt();
        this.isSelected = source.readByte() != 0;
    }

    protected KaPlayerHTH(Parcel in) {
        this.points = in.readParcelable(PointsHTH.class.getClassLoader());
        this.score = in.readParcelable(ScoreHTH.class.getClassLoader());
        this.id = in.readString();
        this.img = in.readString();
        this.pId = in.readString();
        this.role = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.teamName = in.readString();
        this.history = new ArrayList<Object>();
        in.readList(this.history, Object.class.getClassLoader());
        this.playing = in.readInt();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<KaPlayerHTH> CREATOR = new Parcelable.Creator<KaPlayerHTH>() {
        @Override
        public KaPlayerHTH createFromParcel(Parcel source) {
            return new KaPlayerHTH(source);
        }

        @Override
        public KaPlayerHTH[] newArray(int size) {
            return new KaPlayerHTH[size];
        }
    };
}
