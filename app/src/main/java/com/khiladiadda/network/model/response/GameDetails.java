package com.khiladiadda.network.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameDetails implements Parcelable {
    @SerializedName("_id") @Expose private String id;
    @SerializedName("rating") @Expose private Integer rating;
    @SerializedName("is_active") @Expose private Boolean isActive;
    @SerializedName("title") @Expose private String title;
    @SerializedName("image") @Expose private String image;
    @SerializedName("categories") @Expose private List<CategoriesList> categories = null;
    @SerializedName("created_at") @Expose private String createdAt;

    public GameDetails(Parcel in) {
        id = in.readString();

        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
        byte tmpIsActive = in.readByte();
        isActive = tmpIsActive == 0 ? null : tmpIsActive == 1;
        title = in.readString();
        image = in.readString();
        createdAt = in.readString();
        categories = in.readArrayList(CategoriesList.class.getClassLoader());
    }

    public static final Creator<GameDetails> CREATOR = new Creator<GameDetails>() {
        @Override public GameDetails createFromParcel(Parcel in) {
            return new GameDetails(in);
        }

        @Override public GameDetails[] newArray(int size) {
            return new GameDetails[size];
        }
    };

    public GameDetails() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<CategoriesList> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesList> categories) {
        this.categories = categories;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rating);
        }
        dest.writeByte((byte) (isActive == null ? 0 : isActive ? 1 : 2));
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(createdAt);
        dest.writeList(categories);

    }
}