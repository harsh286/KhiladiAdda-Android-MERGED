package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Coming {

    @SerializedName("rating") @Expose private long rating;
    @SerializedName("is_active") @Expose private boolean isActive;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("title") @Expose private String title;
    @SerializedName("image") @Expose private String image;
    @SerializedName("categories") @Expose private List<GameCategory> categories = null;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("updated_at") @Expose private String updatedAt;

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<GameCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<GameCategory> categories) {
        this.categories = categories;
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

}
