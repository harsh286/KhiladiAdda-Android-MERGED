package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaqCategoryDetails implements Parcelable {
    @SerializedName("_id") @Expose private String id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("mobile") @Expose private long mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.mobile);
    }

    public FaqCategoryDetails() {
    }

    protected FaqCategoryDetails(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.mobile = in.readLong();
    }

    public static final Parcelable.Creator<FaqCategoryDetails> CREATOR = new Parcelable.Creator<FaqCategoryDetails>() {
        @Override public FaqCategoryDetails createFromParcel(Parcel source) {
            return new FaqCategoryDetails(source);
        }

        @Override public FaqCategoryDetails[] newArray(int size) {
            return new FaqCategoryDetails[size];
        }
    };
}
