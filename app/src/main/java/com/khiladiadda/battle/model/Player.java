package com.khiladiadda.battle.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player implements Parcelable {

    @SerializedName("points") @Expose private Points points;
    @SerializedName("score") @Expose private Points score;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("img") @Expose private String img;
    @SerializedName("player_id") @Expose private String playerId;
    @SerializedName("title") @Expose private String title;
    @SerializedName("p_id") @Expose private String pId;
 //   @SerializedName("is_substitute")@Expose private  boolean is_substitute;

    private boolean isSelected;

    protected Player(Parcel in) {
        points = in.readParcelable(Points.class.getClassLoader());
        score = in.readParcelable(Points.class.getClassLoader());
        id = in.readString();
        img = in.readString();
        playerId = in.readString();
        title = in.readString();
        pId = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public Points getPoints() {
        return points;
    }

    public void setPoints(Points points) {
        this.points = points;
    }

    public Points getScore() {
        return score;
    }

    public void setScore(Points score) {
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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(points, flags);
        dest.writeParcelable(score, flags);
        dest.writeString(id);
        dest.writeString(img);
        dest.writeString(playerId);
        dest.writeString(title);
        dest.writeString(pId);
    }

    public Player() {
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.img = source.readString();
        this.playerId = source.readString();
        this.title = source.readString();
    }

}