package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileRequest {

    @SerializedName("dp") @Expose private String image;
    @SerializedName("name") @Expose private String name;
    @SerializedName("username") @Expose private String username;
    @SerializedName("email") @Expose private String email;
    @SerializedName("city") @Expose private String city;
    @SerializedName("country") @Expose private String country;
    @SerializedName("state") @Expose private String state;
    @SerializedName("gender") @Expose private String gender;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ProfileRequest(String image, String name, String username, String email, String city, String country, String state, String gender) {
        this.image = image;
        this.name = name;
        this.username = username;
        this.email = email;
        this.city = city;
        this.country = country;
        this.state = state;
        this.gender = gender;
    }

}
