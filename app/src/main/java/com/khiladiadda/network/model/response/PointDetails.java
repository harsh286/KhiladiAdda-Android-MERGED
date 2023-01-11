package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass public class PointDetails implements Parcelable, RealmModel {

    @PrimaryKey @SerializedName("_id") @Expose private String id;
    @SerializedName("image") @Expose private String image;
    @SerializedName("text") @Expose private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.image);
        dest.writeString(this.text);
    }

    public PointDetails() {
    }

    protected PointDetails(Parcel in) {
        this.id = in.readString();
        this.image = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<PointDetails> CREATOR = new Parcelable.Creator<PointDetails>() {
        @Override public PointDetails createFromParcel(Parcel source) {
            return new PointDetails(source);
        }

        @Override public PointDetails[] newArray(int size) {
            return new PointDetails[size];
        }
    };
}
