package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerDetails implements Parcelable {

    @SerializedName("is_deleted") @Expose private boolean isDeleted;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("title") @Expose private String title;
    @SerializedName("img") @Expose private String img;
    @SerializedName("position") @Expose private long position;
    @SerializedName("desc") @Expose private String desc;
    @SerializedName("link") @Expose private String link;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("__v") @Expose private long v;
    @SerializedName("type") @Expose private String type;

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isDeleted ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.img);
        dest.writeLong(this.position);
        dest.writeString(this.desc);
        dest.writeString(this.link);
        dest.writeString(this.createdAt);
        dest.writeLong(this.v);
        dest.writeString(this.type);
    }

    public BannerDetails() {
    }

    protected BannerDetails(Parcel in) {
        this.isDeleted = in.readByte() != 0;
        this.id = in.readString();
        this.title = in.readString();
        this.img = in.readString();
        this.position = in.readLong();
        this.desc = in.readString();
        this.link = in.readString();
        this.createdAt = in.readString();
        this.v = in.readLong();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<BannerDetails> CREATOR = new Parcelable.Creator<BannerDetails>() {
        @Override public BannerDetails createFromParcel(Parcel source) {
            return new BannerDetails(source);
        }

        @Override public BannerDetails[] newArray(int size) {
            return new BannerDetails[size];
        }
    };

}