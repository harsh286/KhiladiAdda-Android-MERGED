package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass public class FactsList implements Parcelable, RealmModel {
    @PrimaryKey @SerializedName("_id") @Expose private String id;
    @SerializedName("like") @Expose private long like;
    @SerializedName("bookmark") @Expose private long bookmark;
    @SerializedName("views") @Expose private long views;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("updated_at") @Expose private String updatedAt;
    @SerializedName("date") @Expose private String date;
    @SerializedName("details") @Expose private String details;
    @SerializedName("from") @Expose private String from;
    @SerializedName("heading") @Expose private String heading;
    @SerializedName("imgurl") @Expose private String imgurl;
    @SerializedName("subheading") @Expose private String subheading;
    @SerializedName("type") @Expose private String type;
    @SerializedName("points") @Expose private RealmList<PointDetails> points = new RealmList<>();
    @SerializedName("liked") @Expose private boolean liked;
    @SerializedName("bookmarked") @Expose private boolean bookmarked;

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
    }

    public long getBookmark() {
        return bookmark;
    }

    public void setBookmark(long bookmark) {
        this.bookmark = bookmark;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RealmList<PointDetails> getPoints() {
        return points;
    }

    public void setPoints(RealmList<PointDetails> points) {
        this.points = points;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.like);
        dest.writeLong(this.bookmark);
        dest.writeLong(this.views);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.date);
        dest.writeString(this.details);
        dest.writeString(this.from);
        dest.writeString(this.heading);
        dest.writeString(this.imgurl);
        dest.writeString(this.subheading);
        dest.writeString(this.type);
        dest.writeTypedList(this.points);
        dest.writeByte(this.liked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bookmarked ? (byte) 1 : (byte) 0);
    }

    public FactsList() {
    }

    protected FactsList(Parcel in) {
        this.id = in.readString();
        this.like = in.readLong();
        this.bookmark = in.readLong();
        this.views = in.readLong();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.date = in.readString();
        this.details = in.readString();
        this.from = in.readString();
        this.heading = in.readString();
        this.imgurl = in.readString();
        this.subheading = in.readString();
        this.type = in.readString();
        this.points = new RealmList<PointDetails>();
        in.readList(this.points, PointDetails.class.getClassLoader());
        this.points = new RealmList<PointDetails>();
        this.liked = in.readByte() != 0;
        this.bookmarked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<FactsList> CREATOR = new Parcelable.Creator<FactsList>() {
        @Override public FactsList createFromParcel(Parcel source) {
            return new FactsList(source);
        }

        @Override public FactsList[] newArray(int size) {
            return new FactsList[size];
        }
    };
}