package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateFavouriteRequest {

    @SerializedName("favourites")
    @Expose
    private List<Integer> favourites = null;

    public List<Integer> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Integer> favourites) {
        this.favourites = favourites;
    }

}
