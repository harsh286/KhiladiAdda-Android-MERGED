package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaptainTeamHTH implements Parcelable {

    @SerializedName("is_substitute")
    @Expose
    private boolean isSubstitute;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("player_id")
    @Expose
    private String playerId;
    @SerializedName("role")
    @Expose
    private Integer role;

    protected CaptainTeamHTH(Parcel in) {
        isSubstitute = in.readByte() != 0;
        id = in.readString();
        img = in.readString();
        title = in.readString();
        playerId = in.readString();
        if (in.readByte() == 0) {
            role = null;
        } else {
            role = in.readInt();
        }
    }

    public static final Creator<CaptainTeamHTH> CREATOR = new Creator<CaptainTeamHTH>() {
        @Override
        public CaptainTeamHTH createFromParcel(Parcel in) {
            return new CaptainTeamHTH(in);
        }

        @Override
        public CaptainTeamHTH[] newArray(int size) {
            return new CaptainTeamHTH[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSubstitute ? 1 : 0));
        dest.writeString(id);
        dest.writeString(img);
        dest.writeString(title);
        dest.writeString(playerId);
        if (role == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(role);
        }
    }

    public boolean isSubstitute() {
        return isSubstitute;
    }

    public void setSubstitute(boolean substitute) {
        isSubstitute = substitute;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
