package com.khiladiadda.clashx2.cricket.createbattle;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class KaPlayer implements Parcelable {

    @SerializedName("_id")
    private String id;
    @SerializedName("img")
    private String img;
    @SerializedName("p_id")
    private String p_id;
    @SerializedName("title")
    private String name;
    @SerializedName("role")
    private int role;

    private boolean isSelected;

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

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.img);
        dest.writeString(this.p_id);
        dest.writeString(this.name);
        dest.writeInt(this.role);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.img = source.readString();
        this.p_id = source.readString();
        this.name = source.readString();
        this.role = source.readInt();
    }

    public KaPlayer() {
    }

    protected KaPlayer(Parcel in) {
        this.id = in.readString();
        this.img = in.readString();
        this.p_id = in.readString();
        this.name = in.readString();
        this.role = in.readInt();
    }

    public static final Creator<KaPlayer> CREATOR = new Creator<KaPlayer>() {
        @Override
        public KaPlayer createFromParcel(Parcel source) {
            return new KaPlayer(source);
        }

        @Override
        public KaPlayer[] newArray(int size) {
            return new KaPlayer[size];
        }
    };
}
